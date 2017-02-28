package com.github.iam.number.enricher.config.transform;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;


@SpringBootApplication
public class EnrichDataApplication {

    @Bean
    public Clock clock(){
        return Clock.systemUTC();
    }

    @Bean
    @Profile("fixedClock")
    @Primary
    public Clock fixedClock(){
        return Clock.fixed(Instant.parse("2010-01-10T10:00:00Z"), ZoneId.of("UTC"));
    }

}
