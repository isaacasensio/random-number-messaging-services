package com.github.iam.number.enricher;

import com.github.iam.number.enricher.config.transform.EnrichDataApplication;
import com.github.iam.number.enricher.model.Number;
import com.github.iam.number.enricher.model.TimedNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnrichDataApplication.class)
@DirtiesContext
@TestPropertySource(properties = {
        "spring.cloud.stream.bindings.input.contentType = application/x-java-object",
        "spring.cloud.stream.bindings.output.contentType = application/x-java-object"
})
@ActiveProfiles("fixedClock")
public class EnrichDataApplicationTest {

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private Clock clock;

    @Test
    public void addATimeStampToTheNumber() {

        Number number = new Number(4);

        processor.input().send(new GenericMessage<>(number));

        Message<TimedNumber> firstMessage =
                (Message<TimedNumber>) messageCollector.forChannel(processor.output()).poll();

        TimedNumber expectedTimedNumber = new TimedNumber(number, clock);

        assertThat(firstMessage.getPayload(), is(expectedTimedNumber));
    }


}