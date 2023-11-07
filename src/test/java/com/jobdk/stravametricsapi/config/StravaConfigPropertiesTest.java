package com.jobdk.stravametricsapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class StravaConfigPropertiesTest {
  @Autowired private StravaConfigProperties stravaConfigProperties;

  @Test
  void testStravaConfigProperties() {
    // Arrange
    // Act
    // Assert
    assertEquals("https://www.strava.com/api/v3/", stravaConfigProperties.apiUrl());
    assertEquals("test-access-token", stravaConfigProperties.accessToken());
    assertEquals("test-client-id", stravaConfigProperties.clientId());
    assertEquals("test-client-secret", stravaConfigProperties.clientSecret());
    assertEquals("test-refresh-token", stravaConfigProperties.refreshToken());
  }
}
