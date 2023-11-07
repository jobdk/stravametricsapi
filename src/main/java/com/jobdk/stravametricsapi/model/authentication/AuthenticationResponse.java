package com.jobdk.stravametricsapi.model.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"token_type", "access_token", "expires_at", "expires_in", "refresh_token"})
public class AuthenticationResponse {

  @JsonProperty("token_type")
  public String tokenType;

  @JsonProperty("access_token")
  public String accessToken;

  @JsonProperty("expires_at")
  public Integer expiresAt;

  @JsonProperty("expires_in")
  public Integer expiresIn;

  @JsonProperty("refresh_token")
  public String refreshToken;
}
