package com.github.iam.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SinkChannels {

    String INPUT = "outputEnricher";


    @Input(INPUT)
    SubscribableChannel input();

}
