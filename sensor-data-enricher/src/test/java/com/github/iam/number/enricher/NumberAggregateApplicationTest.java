package com.github.iam.number.enricher;

import com.github.iam.number.enricher.model.Number;
import com.github.iam.number.enricher.model.TimedNumber;
import com.github.iam.number.enricher.support.MessageConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.aggregate.AggregateApplication;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NumberAggregateApplication.class})
@DirtiesContext
@ActiveProfiles("fixedClock")
public class NumberAggregateApplicationTest {

    private CustomProcessor processor;
    private CustomProcessor clonerProcessor;

    @Autowired
    private Clock clock;

    @Autowired
    private MessageCollector messageCollector;

    private MessageConverter messageConverter = new MessageConverter();

    @Autowired
    private AggregateApplication aggregateApplication;

    @Before
    public void setUp() throws Exception {
        processor = aggregateApplication.getBinding(CustomProcessor.class, "filtering");
        clonerProcessor = aggregateApplication.getBinding(CustomProcessor.class, "cloner");
    }

    @Test
    public void doesNotFilterEvenNumbersAndReturnsAsManyCopiesAsItsInternalNumber() {

        Number evenNumber = new Number(4);

        processor.input().send(new GenericMessage<>(evenNumber));

        Message<TimedNumber> firstMessage = consumeMessage();
        Message<TimedNumber> secondMessage = consumeMessage();
        Message<TimedNumber> thirdMessage = consumeMessage();
        Message<TimedNumber> fourthMessage = consumeMessage();
        Message<TimedNumber> fifthMessage = consumeMessage();


        TimedNumber expectedTimedNumber = new TimedNumber(evenNumber, clock);
        Message<?> jsonMessage = messageConverter.toJsonMessage(expectedTimedNumber);

        assertThat(firstMessage.getPayload(), is(jsonMessage.getPayload()));
        assertThat(secondMessage.getPayload(), is(jsonMessage.getPayload()));
        assertThat(thirdMessage.getPayload(), is(jsonMessage.getPayload()));
        assertThat(fourthMessage.getPayload(), is(jsonMessage.getPayload()));
        assertNull(fifthMessage);

    }

    @Test
    public void filtersOddNumbers() {

        Number oddNumber = new Number(5);

        processor.input().send(new GenericMessage<>(oddNumber));

        Message<TimedNumber> firstMessage = consumeMessage();
        Message<TimedNumber> secondMessage = consumeMessage();
        Message<TimedNumber> thirdMessage = consumeMessage();
        Message<TimedNumber> fourthMessage = consumeMessage();
        Message<TimedNumber> fifthMessage = consumeMessage();

        assertNull(firstMessage);
        assertNull(secondMessage);
        assertNull(thirdMessage);
        assertNull(fourthMessage);
        assertNull(fifthMessage);

    }


    private Message<TimedNumber> consumeMessage() {
        return (Message<TimedNumber>) messageCollector.forChannel(clonerProcessor.exit()).poll();
    }

}