package com.jobdk.stravametricsapi;

import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.service.ActivityRetrievalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StravaConfigProperties.class)
public class StravaMetricsApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(StravaMetricsApiApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(ActivityRetrievalService activityRetrievalService) {
    return args -> {
      // activityRetrievalService.dropActivitiesTable(); // Should just insert new ones and skip
      // duplicates
      activityRetrievalService.getAllActivitiesFromStrava();
    };
  }
}
