package com.jobdk.stravametricsapi;

import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StravaConfigProperties.class)
public class StravaMetricsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StravaMetricsApiApplication.class, args);
    }

}
