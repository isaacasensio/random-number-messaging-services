package com.github.iam.number.emitter;

import com.github.iam.number.emitter.model.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

import java.util.Random;


@SpringBootApplication
@EnableBinding(Source.class)
public class NumberEmitterApplication {

	private static final Logger logger = LoggerFactory.getLogger(NumberEmitterApplication.class);

	private final Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(NumberEmitterApplication.class, args);
	}

	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedRate = "2000"))
	public Number emit(){
		Number number = new Number(random.nextInt(10) + 1);
		logger.info("Emitting: {}", number);
		return number;
	}
}
