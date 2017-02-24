package com.github.iam.filter;

import com.github.iam.enrich.EnricherChannels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface FilteringChannels {

    String INPUT = "inputFiltering";
    String OUTPUT = EnricherChannels.INPUT;


    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();

}
