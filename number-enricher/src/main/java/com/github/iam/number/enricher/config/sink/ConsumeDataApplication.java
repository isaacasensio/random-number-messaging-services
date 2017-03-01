package com.github.iam.number.enricher.config.sink;

import com.github.iam.number.enricher.CustomProcessor;
import com.github.iam.number.enricher.model.Number;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;


@SpringBootApplication
@EnableBinding(CustomProcessor.class)
public class ConsumeDataApplication {

    @StreamListener(CustomProcessor.INPUT)
    @SendTo(CustomProcessor.OUTPUT)
    public Number consume(Number data) {
        return data;
    }

}
