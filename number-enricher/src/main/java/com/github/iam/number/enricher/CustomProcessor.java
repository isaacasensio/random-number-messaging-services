package com.github.iam.number.enricher;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;

public interface CustomProcessor extends Processor {
    String EXIT = "exit";

    @Output(EXIT)
    MessageChannel exit();
}
