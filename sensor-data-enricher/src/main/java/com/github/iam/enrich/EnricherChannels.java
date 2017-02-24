package com.github.iam.enrich;

import com.github.iam.sink.SinkChannels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EnricherChannels {

    String INPUT = "inputEnricher";
    String OUTPUT = "outputEnricher";
    String INPUT_TRIPLICATE = "inputTriplicate";
    String OUTPUT_TRIPLICATE = SinkChannels.INPUT;


    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();


    @Input(INPUT_TRIPLICATE)
    SubscribableChannel inputTriplicate();

    @Output(OUTPUT_TRIPLICATE)
    MessageChannel outputTriplicate();

}
