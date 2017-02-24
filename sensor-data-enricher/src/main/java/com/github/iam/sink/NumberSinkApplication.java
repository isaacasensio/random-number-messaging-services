package com.github.iam.sink;

import com.github.iam.filter.EvenFilteringApplication;
import com.github.iam.enrich.EnrichAndMultiplyApplication;
import com.github.iam.model.TimedNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;


@SpringBootApplication
@EnableBinding(SinkChannels.class)
public class NumberSinkApplication {

	@Autowired
	private LoggingService loggingService;

	@StreamListener(SinkChannels.INPUT)
	public void consume(TimedNumber timedNumber) {
		loggingService.log(timedNumber);
	}

	@Service
	public class LoggingService {

		private final Logger logger = LoggerFactory.getLogger(LoggingService.class);

		public void log(TimedNumber number){
			logger.debug("Consumed : {}", number);
		}
	}
}
