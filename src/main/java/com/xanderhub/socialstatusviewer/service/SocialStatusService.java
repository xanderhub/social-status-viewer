package com.xanderhub.socialstatusviewer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.xanderhub.socialstatusviewer.entity.NetworkStatusTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class SocialStatusService {

    private final RestTemplate restTemplate;
    private final ThreadPoolExecutor executor;
    private CountDownLatch latch;
    private final Map<String, String> networks;

    @Value("${app.num-of-retries}") private int retriesCount;

    public SocialStatusService(@Value("#{${app.social-networks}}") Map<String, String> networks) {
        this.networks = networks;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(networks.size());
        this.restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(
                new ArrayList<>(Collections.singletonList(new MappingJackson2HttpMessageConverter())));
    }


    public Map<String, JsonNode> getSocialNetworkStatus() {
        Map<String, JsonNode> results = new ConcurrentHashMap<>();

        setLatch();

        for (String networkName : networks.keySet()) {
            executor.submit(new NetworkStatusTask(
                             networkName
                            ,networks.get(networkName)
                            ,results
                            ,restTemplate
                            ,retriesCount
                            ,latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }

    private void setLatch() {
        this.latch = new CountDownLatch(networks.size());
    }
}
