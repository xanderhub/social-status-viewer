package com.xanderhub.socialstatusviewer.controller;

import com.xanderhub.socialstatusviewer.service.SocialStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialStatusController {

    @Autowired
    private SocialStatusService socialStatusService;

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity getStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(socialStatusService.getSocialNetworkStatus());
    }
}
