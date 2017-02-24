package com.github.iam;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.Transformer;

import java.util.Random;


@Configuration
@EnableBinding(Processor.class)
public class SensorDataEnricherProcessorApplication {

	private final Random random = new Random();

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public SensorDataEnriched transform(SensorData data) {
		return new SensorDataEnriched(data, random.nextInt());
	}

	@Router
	public String route(SensorDataEnriched message) {

		if(message.getAdditionalValue() < 0.5){
			return "lessThan05";
		}else{
			return "moreThan05";
		}
	}
}
