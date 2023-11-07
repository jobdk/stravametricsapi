package com.jobdk.stravametricsapi.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class StravaConfigPropertiesTest {
  @Autowired private StravaConfigProperties stravaConfigProperties;

  @Test
  void testStravaConfigProperties() {
    // Arrange
    // Act
    // Assert
    assertEquals("test-protocol", stravaConfigProperties.getProtocol());
    assertEquals("test-host", stravaConfigProperties.getHost());
    assertEquals("test-api-path", stravaConfigProperties.getApiPath());
    assertEquals("test-oauth-path", stravaConfigProperties.getOauthPath());
        assertEquals("test-access-token", stravaConfigProperties.getAccessToken());
    assertEquals("test-client-id", stravaConfigProperties.getClientId());
    assertEquals("test-client-secret", stravaConfigProperties.getClientSecret());
    assertEquals("test-refresh-token", stravaConfigProperties.getRefreshToken());
  }
}
