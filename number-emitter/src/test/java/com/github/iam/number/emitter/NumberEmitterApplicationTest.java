package com.github.iam.number.emitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Callable;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NumberEmitterApplication.class)
@DirtiesContext
public class NumberEmitterApplicationTest {

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private Source source;


    private Callable<Boolean> randomNumberConsumed() {
        return () -> messageCollector.forChannel(source.output()).poll() != null;
    }


    @Test
    public void everySecondAMessageIsSent() {

        await().atMost(3, SECONDS).until(randomNumberConsumed(), is(true));
        await().atMost(3, SECONDS).until(randomNumberConsumed(), is(true));
        await().atMost(3, SECONDS).until(randomNumberConsumed(), is(true));

    }




}