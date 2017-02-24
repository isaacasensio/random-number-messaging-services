package com.github.iam;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;


@Configuration
@EnableBinding(Processor.class)
public class SensorDataFilteringProcessor {

	@Filter(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public boolean onlyDataWithNameStartingWithNumber(SensorData data) {
		return Character.isDigit(data.getName().charAt(0));
	}
}
