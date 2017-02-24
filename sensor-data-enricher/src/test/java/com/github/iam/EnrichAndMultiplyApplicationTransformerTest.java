package com.github.iam;

import com.github.iam.enrich.EnrichAndMultiplyApplication;
import com.github.iam.enrich.EnricherChannels;
import com.github.iam.model.Number;
import com.github.iam.model.TimedNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnrichAndMultiplyApplication.class)
@DirtiesContext
public class EnrichAndMultiplyApplicationTransformerTest {

    @Autowired
    private EnricherChannels processor;

    @Autowired
    private MessageCollector messageCollector;

    @MockBean
    private Clock clock;

    @Before
    public void setUp() throws Exception {
        when(clock.millis()).thenReturn(1000L);
    }

    @Test
    public void enrichWithCurrentMillisAndQuadruplicatesTheMessage() {

        Number number = new Number(4);

        processor.input().send(new GenericMessage<>(number));

        Message<TimedNumber> firstMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();
        Message<TimedNumber> secondMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();
        Message<TimedNumber> thirdMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();
        Message<TimedNumber> fourthMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();
        Message<TimedNumber> fifthMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();

        TimedNumber expectedTimedNumber = new TimedNumber(number, clock);

        assertThat(firstMessage.getPayload(), is(expectedTimedNumber));
        assertThat(secondMessage.getPayload(), is(expectedTimedNumber));
        assertThat(thirdMessage.getPayload(), is(expectedTimedNumber));
        assertThat(fourthMessage.getPayload(), is(expectedTimedNumber));
        assertNull(fifthMessage);
    }


    @Test
    public void enrichWithCurrentMillisAndDuplicatesTheMessage() {

        Number number = new Number(2);

        processor.input().send(new GenericMessage<>(number));

        Message<TimedNumber> firstMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();
        Message<TimedNumber> secondMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();
        Message<TimedNumber> thirdMessage = (Message<TimedNumber>) messageCollector.forChannel(processor.outputTriplicate()).poll();

        TimedNumber expectedTimedNumber = new TimedNumber(number, clock);

        assertThat(firstMessage.getPayload(), is(expectedTimedNumber));
        assertThat(secondMessage.getPayload(), is(expectedTimedNumber));
        assertNull(thirdMessage);
    }


}