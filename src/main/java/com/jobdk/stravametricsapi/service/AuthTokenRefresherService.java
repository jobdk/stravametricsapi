package com.jobdk.stravametricsapi.service;

import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.model.Activity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthTokenRefresherService {


    private final RestTemplate restTemplate;
    private final StravaConfigProperties stravaConfigProperties;

    public AuthTokenRefresherService(RestTemplate restTemplate, StravaConfigProperties stravaConfigProperties) {
        this.restTemplate = restTemplate;
        this.stravaConfigProperties = stravaConfigProperties;
    }


    public void refreshToken() {
        String url = buildUrl();
        List<Activity> body = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<List<Activity>>() {
        }).getBody();
        // TODO: get new access token and save it in current variable config

    }

    private String buildUrl() {
        return new StringBuilder()
                .append(stravaConfigProperties.apiUrl())
                .append("oauth/token?")
                .append("client_id=").append(stravaConfigProperties.clientId())
                .append("client_secret=").append(stravaConfigProperties.clientSecret())
                .append("refresh_token=").append(stravaConfigProperties.refreshToken())
                .toString();
//        https://www.strava.com/oauth/token?client_id=116127&client_secret=f3f3f28aacbaadc2a361dc9ef790fdba1c182a52&refresh_token=727ce7d22c896de46150cb487f533bd72cd79d0b&grant_type=refresh_token

    }
}
