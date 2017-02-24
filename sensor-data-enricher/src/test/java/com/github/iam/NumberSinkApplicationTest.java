package com.github.iam;

import com.github.iam.model.Number;
import com.github.iam.model.TimedNumber;
import com.github.iam.sink.NumberSinkApplication;
import com.github.iam.sink.SinkChannels;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NumberSinkApplication.class)
@DirtiesContext
public class NumberSinkApplicationTest {

    @Autowired
    private SinkChannels sinkChannels;

    @Autowired
    private MessageCollector messageCollector;

    @MockBean
    private Clock clock;

    @MockBean
    private NumberSinkApplication.LoggingService loggingService;

    @Before
    public void setUp() throws Exception {
        when(clock.millis()).thenReturn(1000L);
    }


    @Test
    public void warningAndErrorMessagesAreConsumed() {

        TimedNumber timedNumber = new TimedNumber(new Number(2), clock);

        sinkChannels.input().send(new GenericMessage<>(timedNumber));

        verify(loggingService).log(timedNumber);
    }

}