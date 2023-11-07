package com.jobdk.stravametricsapi.controller;

import com.jobdk.stravametricsapi.service.AuthTokenRefresherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthTokenRefresherService authTokenRefresherService;

    public AuthController(AuthTokenRefresherService authTokenRefresherService) {
        this.authTokenRefresherService = authTokenRefresherService;
    }


    @PostMapping ("refreshToken")
    public void refreshToken(){
        this.authTokenRefresherService.refreshToken();
    }
}
