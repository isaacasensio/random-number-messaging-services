package com.github.iam.number.enricher.support;

import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class MessageConsumer {

    private MessageCollector messageCollector;
    private MessageConverter messageConverter;

    public <T> MessageConsumer(MessageCollector messageCollector) {
        this.messageCollector = messageCollector;
        this.messageConverter = new MessageConverter();
    }

    public <T> Message<T> consumeJsonMessage(MessageChannel channel, Class<T> clazz) {
        return messageConverter.fromJsonMessage(messageCollector.forChannel(channel).poll(), clazz);
    }

}
