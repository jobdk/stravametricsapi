package com.jobdk.stravametricsapi.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class StravaConfigPropertiesTest {
  @MockBean
  CommandLineRunner commandLineRunnerMock;
  @Autowired private StravaConfigProperties stravaConfigProperties;

  @Test
  void testStravaConfigProperties() {
    // Arrange
    // Act
    // Assert
    assertEquals("https", stravaConfigProperties.getProtocol());
    assertEquals("www.strava.com", stravaConfigProperties.getHost());
    assertEquals("/api/v3/", stravaConfigProperties.getApiPath());
    assertEquals("/oauth/token", stravaConfigProperties.getOauthPath());
        assertEquals("test-access-token", stravaConfigProperties.getAccessToken());
    assertEquals("test-client-id", stravaConfigProperties.getClientId());
    assertEquals("test-client-secret", stravaConfigProperties.getClientSecret());
    assertEquals("test-refresh-token", stravaConfigProperties.getRefreshToken());
  }
}
