package com.github.iam.number.consumer.sink;

import com.github.iam.number.consumer.model.TimedNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;


@EnableBinding(Sink.class)
public class NumberSource {

    private ConsumerService consumerService;

    @Autowired
    public NumberSource(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @StreamListener(Sink.INPUT)
    public void handle(TimedNumber number) {
        consumerService.consume(number);
    }

}
