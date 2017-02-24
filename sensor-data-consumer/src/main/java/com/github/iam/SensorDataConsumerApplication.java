package com.github.iam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class SensorDataConsumerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SensorDataConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SensorDataConsumerApplication.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void handle(SensorData sensorData) {
		LOGGER.info("Data: {}", sensorData);
	}
}
