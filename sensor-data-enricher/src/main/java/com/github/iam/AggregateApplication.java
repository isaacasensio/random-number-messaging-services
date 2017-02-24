package com.github.iam;

import com.github.iam.enrich.EnrichAndMultiplyApplication;
import com.github.iam.filter.EvenFilteringApplication;
import com.github.iam.sink.NumberSinkApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;

@SpringBootApplication
public class AggregateApplication {

	public static void main(String[] args) {
		new AggregateApplicationBuilder()
				.from(EvenFilteringApplication.class).namespace("filtering")
				.via(EnrichAndMultiplyApplication.class).namespace("enricher")
				.to(NumberSinkApplication.class).namespace("sink");
	}
}
