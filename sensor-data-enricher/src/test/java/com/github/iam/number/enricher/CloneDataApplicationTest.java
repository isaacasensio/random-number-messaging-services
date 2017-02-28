package com.github.iam.number.enricher;

import com.github.iam.number.enricher.config.multiply.CloneDataApplication;
import com.github.iam.number.enricher.model.Number;
import com.github.iam.number.enricher.model.TimedNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CloneDataApplication.class)
@TestPropertySource(properties = {
        "spring.cloud.stream.bindings.input.contentType = application/x-java-object",
        "spring.cloud.stream.bindings.exit.contentType = application/x-java-object"
})
@DirtiesContext
public class CloneDataApplicationTest {

    @Autowired
    private CustomProcessor processor;

    @Autowired
    private MessageCollector messageCollector;

    private Clock clock;

    @Before
    public void setUp() throws Exception {
        clock = Clock.fixed(Instant.parse("2010-01-10T10:00:00Z"), ZoneId.of("UTC"));
    }

    @Test
    public void enrichWithCurrentMillisAndQuadruplicatesTheMessage() {

        Number number = new Number(4);
        TimedNumber expectedTimedNumber = new TimedNumber(number, clock);

        processor.input().send(new GenericMessage<>(expectedTimedNumber));

        Message<TimedNumber> firstMessage = consumeMessage();
        Message<TimedNumber> secondMessage = consumeMessage();
        Message<TimedNumber> thirdMessage = consumeMessage();
        Message<TimedNumber> fourthMessage = consumeMessage();
        Message<TimedNumber> fifthMessage = consumeMessage();


        assertThat(firstMessage.getPayload(), is(expectedTimedNumber));
        assertThat(secondMessage.getPayload(), is(expectedTimedNumber));
        assertThat(thirdMessage.getPayload(), is(expectedTimedNumber));
        assertThat(fourthMessage.getPayload(), is(expectedTimedNumber));
        assertNull(fifthMessage);
    }

    @Test
    public void enrichWithCurrentMillisAndDuplicatesTheMessage() {

        Number number = new Number(2);
        TimedNumber expectedTimedNumber = new TimedNumber(number, clock);

        processor.input().send(new GenericMessage<>(expectedTimedNumber));

        Message<TimedNumber> firstMessage = consumeMessage();
        Message<TimedNumber> secondMessage = consumeMessage();
        Message<TimedNumber> thirdMessage = consumeMessage();


        assertThat(firstMessage.getPayload(), is(expectedTimedNumber));
        assertThat(secondMessage.getPayload(), is(expectedTimedNumber));
        assertNull(thirdMessage);
    }

    private Message<TimedNumber> consumeMessage() {
        return (Message<TimedNumber>) messageCollector.forChannel(processor.exit()).poll();
    }


}