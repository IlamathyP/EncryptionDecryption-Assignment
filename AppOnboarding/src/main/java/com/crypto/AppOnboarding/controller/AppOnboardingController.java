package com.crypto.AppOnboarding.controller;

import com.crypto.AppOnboarding.model.AppResponse;
import com.crypto.AppOnboarding.service.AppOnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class AppOnboardingController {

    @Autowired
    private AppOnboardingService appOnboardingService;
    @PostMapping("/onboardApp")
    public String appOnboarding(@RequestParam(name = "appName") String appName, @RequestParam(name = "userId")String userId) throws UnsupportedEncodingException {
        ResponseEntity<String> response = appOnboardingService.onboardApp(userId,appName);
        return response.toString();
    }
    @GetMapping("/getApp")
    public List<AppResponse> getAppDetails() throws UnsupportedEncodingException {

        List<AppResponse> list= appOnboardingService.getAppDetails();
        return list;
    }

}
