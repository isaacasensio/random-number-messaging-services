package com.github.iam.number.enricher.config.transform;

import com.github.iam.number.enricher.CustomProcessor;
import com.github.iam.number.enricher.model.Number;
import com.github.iam.number.enricher.model.TimedNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
@EnableBinding(CustomProcessor.class)
public class EnrichTransformer {

    private static final Logger logger = LoggerFactory.getLogger(EnrichTransformer.class);

    private final Clock clock;

    @Autowired
    public EnrichTransformer(Clock clock) {
        this.clock = clock;
    }

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public TimedNumber addCurrentMillisToNumber(Number number) {
        TimedNumber timedNumber = new TimedNumber(number.getValue(), clock);
        logger.info("Enriching data: {}", timedNumber);
        return timedNumber;
    }
}
