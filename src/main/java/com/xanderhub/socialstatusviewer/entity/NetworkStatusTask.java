package com.xanderhub.socialstatusviewer.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class NetworkStatusTask implements Runnable {

    private final String networkName;
    private final String networkUrl;
    private final Map<String, JsonNode> results;
    private final RestTemplate restTemplate;
    private final CountDownLatch latch;
    private int retriesCount;

    public NetworkStatusTask(
            String networkName
            , String networkUrl
            , Map<String, JsonNode> results
            , RestTemplate restTemplate
            , int retriesCount
            , CountDownLatch latch) {

        this.networkName = networkName;
        this.networkUrl = networkUrl;
        this.results = results;
        this.restTemplate = restTemplate;
        this.latch = latch;
        this.retriesCount = retriesCount;
    }

    @Override
    public void run() {
        JsonNode response = null;

        while(retriesCount > 0 && response == null) {
            try {
                response = restTemplate.getForObject(networkUrl, JsonNode.class);
            } catch (RestClientException e) {
                retriesCount--;
            }
        }

        if(response != null)
            results.put(networkName, response);
        else
            results.put(networkName, new ObjectMapper().createObjectNode().put("error", "social network status is unavailable"));

        latch.countDown();
    }
}
