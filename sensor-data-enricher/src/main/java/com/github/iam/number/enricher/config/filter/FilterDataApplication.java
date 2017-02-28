package com.github.iam.number.enricher.config.filter;

import com.github.iam.number.enricher.CustomProcessor;
import com.github.iam.number.enricher.model.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Filter;

@SpringBootApplication
@EnableBinding(CustomProcessor.class)
public class FilterDataApplication {

    private static final Logger logger = LoggerFactory.getLogger(FilterDataApplication.class);

    @Filter(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public boolean onlyEvenNumbersAreProcessed(Number number) {
        if(!number.isEven()){
            logger.info("Number: {} is skipped", number);
        }
        return number.isEven();
    }
}
