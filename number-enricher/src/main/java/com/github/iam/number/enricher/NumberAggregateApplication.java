package com.github.iam.number.enricher;

import com.github.iam.number.enricher.config.filter.FilterDataApplication;
import com.github.iam.number.enricher.config.multiply.CloneDataApplication;
import com.github.iam.number.enricher.config.sink.ConsumeDataApplication;
import com.github.iam.number.enricher.config.transform.EnrichDataApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NumberAggregateApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberAggregateApplication.class, args);
	}

	@Bean
	public AggregateApplication aggregateApplication() {
		return new AggregateApplicationBuilder(NumberAggregateApplication.class)
				.from(ConsumeDataApplication.class).namespace("consuming")
				.via(FilterDataApplication.class).namespace("filtering")
				.via(EnrichDataApplication.class).namespace("enricher")
				.via(CloneDataApplication.class).namespace("cloner")
				.build();
	}
}
