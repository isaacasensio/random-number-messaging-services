package com.github.iam.number.consumer.sink;

import com.github.iam.number.consumer.model.TimedNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    public void consume(TimedNumber number) {
        LOGGER.info("Data: {}", number);
    }
}
