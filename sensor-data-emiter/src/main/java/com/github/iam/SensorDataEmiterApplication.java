package com.github.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;

import java.util.Random;
import java.util.UUID;


@SpringBootApplication
@EnableBinding(Source.class)
public class SensorDataEmiterApplication {

	private final Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(SensorDataEmiterApplication.class, args);
	}

	@InboundChannelAdapter(Source.OUTPUT)
	public SensorData emit(){
		String name	= UUID.randomUUID().toString();
		return new SensorData(name, random.nextFloat());
	}
}
