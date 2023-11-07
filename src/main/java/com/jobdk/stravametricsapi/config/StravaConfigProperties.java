package com.jobdk.stravametricsapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("strava")
public
class StravaConfigProperties { // Cannot use record as accessToken has to be changed at runtime
  private String protocol;
  private String host;
  private String apiPath;
  private String oauthPath;
  private String accessToken;
  private String clientId;
  private String clientSecret;
  private String refreshToken;
}
