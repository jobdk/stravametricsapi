package com.jobdk.stravametricsapi.service;

import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.model.authentication.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AuthTokenRefresherService {
  private static final Logger LOG = LoggerFactory.getLogger(AuthTokenRefresherService.class);

  private final RestTemplate restTemplate;
  private final StravaConfigProperties stravaConfigProperties;

  public AuthTokenRefresherService(
      RestTemplate restTemplate, StravaConfigProperties stravaConfigProperties) {
    this.restTemplate = restTemplate;
    this.stravaConfigProperties = stravaConfigProperties;
  }

  public void refreshToken() {
    String url = buildUrl();
    LOG.info("Refreshing access token.");
    AuthenticationResponse authenticationResponse =
        restTemplate.exchange(url, HttpMethod.POST, null, AuthenticationResponse.class).getBody();
    stravaConfigProperties.setAccessToken(authenticationResponse.getAccessToken());
    LOG.info("Access token refreshed.");
  }

  private String buildUrl() {
    return UriComponentsBuilder.newInstance()
        .scheme(stravaConfigProperties.getProtocol())
        .host(stravaConfigProperties.getHost())
        .path(stravaConfigProperties.getOauthPath())
        .queryParam("client_id", stravaConfigProperties.getClientId())
        .queryParam("client_secret", stravaConfigProperties.getClientSecret())
        .queryParam("refresh_token", stravaConfigProperties.getRefreshToken())
        .queryParam("grant_type", "refresh_token")
        .toUriString();
  }
}
