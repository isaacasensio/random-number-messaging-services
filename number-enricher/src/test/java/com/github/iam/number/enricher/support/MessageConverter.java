package com.github.iam.number.enricher.support;

import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

public class MessageConverter {

    public Message<?> toJsonMessage(Object object) {
        ObjectToJsonTransformer transformer = new ObjectToJsonTransformer();
        return transformer.transform(new GenericMessage<>(object));
    }

    public  <T> Message<T> fromJsonMessage(Message<?> message, Class<T> clazz) {
        if(message == null) return null;
        JsonToObjectTransformer transformer = new JsonToObjectTransformer(clazz);
        return (Message<T>) transformer.transform(message);
    }
}
