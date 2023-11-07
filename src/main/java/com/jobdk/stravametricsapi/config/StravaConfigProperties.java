package com.jobdk.stravametricsapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("strava")
public record StravaConfigProperties(String apiUrl, String accessToken,
                                     String clientId, String clientSecret,
                                     String refreshToken) {

}