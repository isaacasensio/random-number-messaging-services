package com.github.iam;

import com.github.iam.filter.FilteringChannels;
import com.github.iam.model.Number;
import com.github.iam.model.TimedNumber;
import com.github.iam.sink.NumberSinkApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AggregateApplication.class)
@DirtiesContext
public class AggregateApplicationTest {

    @Autowired
    private FilteringChannels filteringChannels;

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

        Number oddNumber = new Number(4);
        Number evenNumber = new Number(5);

        filteringChannels.input().send(new GenericMessage<>(oddNumber));
        filteringChannels.input().send(new GenericMessage<>(evenNumber));

        TimedNumber timedNumber = new TimedNumber(oddNumber, clock);

        verify(loggingService, times(4)).log(timedNumber);
        verify(loggingService, times(4)).log(any(TimedNumber.class));
    }



}