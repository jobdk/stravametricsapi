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
import com.jobdk.stravametricsapi.exception.NoActivitiesFoundException;
import com.jobdk.stravametricsapi.model.activity.Activity;
import com.jobdk.stravametricsapi.repository.ActivityRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ActivityRetrievalServiceTest {
  @MockBean CommandLineRunner commandLineRunnerMock;
  @MockBean RestTemplate restTemplateMock;
  @MockBean AuthTokenRefresherService authTokenRefresherServiceMock;
  @MockBean ActivityRepository activityRepositoryMock;
  @Mock ResponseEntity<List<Activity>> responseEntityMockFifty;
  @Mock ResponseEntity<List<Activity>> responseEntityMockForty;
  @Mock ResponseEntity<List<Activity>> getResponseEntityMockLastRetrieval;

  @Autowired StravaConfigProperties stravaConfigPropertiesMock;
  ObjectMapper objectMapper = new ObjectMapper();
  TypeReference<List<Activity>> listTypeReference = new TypeReference<>() {};

  private ActivityRetrievalService unitUnderTest;

  List<Activity> fiftyList;
  List<Activity> fortyEightList;
  List<Activity> allActivities;

  @BeforeEach
  void beforeEach() {
    unitUnderTest =
        new ActivityRetrievalService(
            restTemplateMock,
            stravaConfigPropertiesMock,
            authTokenRefresherServiceMock,
            activityRepositoryMock);
  }

  @BeforeAll
  void beforeAll() throws IOException {
    fiftyList =
        objectMapper.readValue(
            getFile(CLASSPATH_URL_PREFIX.concat("fiftyActivities.json")), listTypeReference);
    fortyEightList =
        objectMapper.readValue(
            getFile(CLASSPATH_URL_PREFIX.concat("fortyNineActivities.json")), listTypeReference);
    allActivities = new ArrayList<>(List.copyOf(fiftyList));
    allActivities.addAll(fortyEightList);
  }

  // TODO: fix test to use mongo db
  @Test
  void testGetAllActivitiesFromStrava() {
    // Arrange
    when(activityRepositoryMock.findActivitiesByOrderByStartDateDesc())
        .thenReturn(Optional.of(allActivities));
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
    when(responseEntityMockForty.getBody()).thenReturn(fortyEightList);

    // Act
    List<Activity> result = unitUnderTest.getAllActivitiesFromStrava();

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
  void testUpdateActivities() throws IOException {
    // Arrange
    List<Activity> afterLastRetrievalList =
        objectMapper.readValue(
            getFile(CLASSPATH_URL_PREFIX.concat("afterLastRetrieval.json")), listTypeReference);

    when(activityRepositoryMock.findActivitiesByOrderByStartDateDesc())
        .thenReturn(Optional.of(allActivities));
    when(restTemplateMock.exchange(
            Mockito.contains("after"),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any()))
        .thenReturn(getResponseEntityMockLastRetrieval);
    when(getResponseEntityMockLastRetrieval.getBody()).thenReturn(afterLastRetrievalList);

    // Act
    unitUnderTest.updateActivities();

    // Assert
    verify(restTemplateMock, times(1))
        .exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any());
    verify(getResponseEntityMockLastRetrieval, times(1)).getBody();
    verify(activityRepositoryMock, times(1)).saveAll(afterLastRetrievalList);
  }

  @Test
  void testGetAllActivitiesFromDatabase() {
    // Arrange
    when(activityRepositoryMock.findActivitiesByOrderByStartDateDesc())
        .thenReturn(Optional.of(fiftyList));
    when(restTemplateMock.exchange(
            Mockito.endsWith("1"),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any()))
        .thenReturn(responseEntityMockFifty);
    when(responseEntityMockFifty.getBody()).thenReturn(fiftyList);

    // Act
    List<Activity> result = unitUnderTest.getAllActivitiesFromDatabase();

    // Assert
    verify(activityRepositoryMock, times(1)).findActivitiesByOrderByStartDateDesc();
    assertThat(result).hasSize(50);
  }

  @Test
  void testGetAllActivitiesFromDatabaseEmpty() {
    // Arrange
    when(activityRepositoryMock.findActivitiesByOrderByStartDateDesc())
        .thenReturn(Optional.empty());

    // Act
    // Assert
    Assertions.assertThrows(
        NoActivitiesFoundException.class, () -> unitUnderTest.getAllActivitiesFromDatabase());
  }

  @Test
  void testAccessCodeIncorrect() {
    // Arrange
    when(activityRepositoryMock.findActivitiesByOrderByStartDateDesc())
        .thenReturn(Optional.empty());
    when(restTemplateMock.exchange(
            Mockito.endsWith("1"),
            eq(HttpMethod.GET),
            any(),
            Mockito.<ParameterizedTypeReference<List<Activity>>>any()))
        .thenThrow(HttpClientErrorException.Unauthorized.class);

    // Act
    // Assert
    Assertions.assertThrows(
        HttpClientErrorException.Unauthorized.class,
        () -> unitUnderTest.getAllActivitiesFromStrava());
  }
}
