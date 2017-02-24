package com.github.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SensorDataAggregateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorDataAggregateApplication.class, args);
	}

	@Bean
	public AggregateApplication aggregateApplication() {
		return new AggregateApplicationBuilder()
				.from(SensorDataFilteringProcessor.class).namespace("filtering")
				.to(SensorDataEnricherProcessorApplication.class).namespace("enricher")
				.build();
	}
}
