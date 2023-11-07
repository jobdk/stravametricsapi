package com.jobdk.stravametricsapi.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;
import static org.springframework.util.ResourceUtils.getFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.model.activity.Activity;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ActivityRetrievalServiceTest {
  @Mock RestTemplate restTemplateMock;
  @Mock AuthTokenRefresherService authTokenRefresherService;
  @Mock ResponseEntity<List<Activity>> responseEntityMockFifty;
  @Mock ResponseEntity<List<Activity>> responseEntityMockForty;
  @Mock ResponseEntity<List<Activity>> getResponseEntityMockLastRetrieval;

  @Autowired StravaConfigProperties stravaConfigPropertiesMock;
  ObjectMapper objectMapper = new ObjectMapper();
  TypeReference<List<Activity>> listTypeReference = new TypeReference<>() {};

  private ActivityRetrievalService unitUnderTest;

  @BeforeEach
  void beforeEach() {
    unitUnderTest =
        new ActivityRetrievalService(
            restTemplateMock, stravaConfigPropertiesMock, authTokenRefresherService);
  }

  @Test
  void testGetAllActivitiesReturnsCombinedList() throws IOException {
    // Arrange
    List<Activity> fiftyList =
        objectMapper.readValue(
            getFile(CLASSPATH_URL_PREFIX.concat("fiftyActivities.json")), listTypeReference);
    List<Activity> fourtyNineList =
        objectMapper.readValue(
            getFile(CLASSPATH_URL_PREFIX.concat("fortyNineActivities.json")), listTypeReference);

    when(restTemplateMock.exchange(
            Mockito.endsWith("1"),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any()))
        .thenReturn(responseEntityMockFifty);

    when(restTemplateMock.exchange(
            Mockito.endsWith("2"),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any()))
        .thenReturn(responseEntityMockForty);

    when(responseEntityMockFifty.getBody()).thenReturn(fiftyList);
    when(responseEntityMockForty.getBody()).thenReturn(fourtyNineList);

    // Act
    List<Activity> result = unitUnderTest.getAllActivities();

    // Assert
    verify(restTemplateMock, times(2))
        .exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any());
    verify(responseEntityMockFifty, times(1)).getBody();
    verify(responseEntityMockForty, times(1)).getBody();
    assertThat(result).hasSize(98);
    assertThat(result.get(0).getId()).isEqualTo(200000000L);
  }

  @Test
  void testGetActivitiesAfterLastRetrievalReturnsCombinedList() throws IOException {
    // Arrange
    List<Activity> afterLastRetrievalList =
        objectMapper.readValue(
            getFile(CLASSPATH_URL_PREFIX.concat("afterLastRetrieval.json")), listTypeReference);

    String date = "2023-11-05T10:30:31Z";
    long elapsedTime = 4432L;
    when(restTemplateMock.exchange(
            Mockito.contains("after"),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any()))
        .thenReturn(getResponseEntityMockLastRetrieval);
    when(getResponseEntityMockLastRetrieval.getBody()).thenReturn(afterLastRetrievalList);

    // Act
    List<Activity> result = unitUnderTest.getActivitiesAfterLastRetrieval(date, elapsedTime);

    // Assert
    verify(restTemplateMock, times(1))
        .exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any());
    verify(getResponseEntityMockLastRetrieval, times(1)).getBody();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(200000000L);
  }
}
