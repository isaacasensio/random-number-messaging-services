package com.github.iam.number.enricher.config.multiply;

import com.github.iam.number.enricher.CustomProcessor;
import com.github.iam.number.enricher.model.TimedNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Splitter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@SpringBootApplication
@EnableBinding(CustomProcessor.class)
public class CloneDataApplication {

    private static final Logger logger = LoggerFactory.getLogger(CloneDataApplication.class);

    @Splitter(inputChannel = CustomProcessor.INPUT, outputChannel = CustomProcessor.EXIT)
    public List<TimedNumber> createAsManyMessageAsInternalValue(TimedNumber timedNumber) {

        List<TimedNumber> clonedNumbers = IntStream.range(0, timedNumber.getNumber())
                .mapToObj(i -> timedNumber)
                .collect(Collectors.toList());

        logger.info("Multiplying: {} with {} more messages", timedNumber, clonedNumbers.size());

        return clonedNumbers;
    }

}
