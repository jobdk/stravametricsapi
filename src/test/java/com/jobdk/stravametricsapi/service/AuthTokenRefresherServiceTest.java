package com.jobdk.stravametricsapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.model.authentication.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class AuthTokenRefresherServiceTest {
  @MockBean CommandLineRunner commandLineRunnerMock;
  @MockBean RestTemplate restTemplateMock;
  @Autowired StravaConfigProperties stravaConfigProperties;
  @Mock ResponseEntity<AuthenticationResponse> responseEntity;

  private AuthTokenRefresherService unitUnderTest;
  String response =
      """
{
    "token_type": "type",
    "access_token": "34234234234",
    "expires_at": 324234234,
    "expires_in": 324234,
    "refresh_token": "32423423"
}
""";

  @BeforeEach
  void beforeEach() {
    unitUnderTest = new AuthTokenRefresherService(restTemplateMock, stravaConfigProperties);
  }

  @Test
  void testRefreshToken() throws JsonProcessingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    AuthenticationResponse authenticationResponse =
        objectMapper.readValue(response, AuthenticationResponse.class);
    when(restTemplateMock.exchange(
            eq(
                "https://www.strava.com/oauth/token?client_id=test-client-id&client_secret=test-client-secret&refresh_token=test-refresh-token&grant_type=refresh_token"),
            Mockito.eq(HttpMethod.POST),
            any(),
            Mockito.<Class<AuthenticationResponse>>any()))
        .thenReturn(responseEntity);
    when(responseEntity.getBody()).thenReturn(authenticationResponse);

    // Act
    unitUnderTest.refreshToken();

    // Assert
    verify(restTemplateMock, times(1))
        .exchange(
            eq(
                "https://www.strava.com/oauth/token?client_id=test-client-id&client_secret=test-client-secret&refresh_token=test-refresh-token&grant_type=refresh_token"),
            Mockito.eq(HttpMethod.POST),
            any(),
            Mockito.<Class<AuthenticationResponse>>any());
    assertThat(stravaConfigProperties.getAccessToken()).isEqualTo("34234234234");
  }
}
