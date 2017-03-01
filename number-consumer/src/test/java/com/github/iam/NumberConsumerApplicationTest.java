package com.github.iam;

import com.github.iam.number.consumer.sink.ConsumerService;
import com.github.iam.number.consumer.NumberConsumerApplication;
import com.github.iam.number.consumer.model.TimedNumber;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NumberConsumerApplication.class)
@DirtiesContext
public class NumberConsumerApplicationTest {

	@Autowired
	private Sink channels;

	@MockBean
	private ConsumerService consumerService;

	@Test
    @Ignore
	public void consumesEveryMessageProvided() {

		List<TimedNumber> timedNumbers = Arrays.asList(
				new TimedNumber(1, 1L),
				new TimedNumber(1, 1L),
				new TimedNumber(1, 1L),
				new TimedNumber(1, 1L));


		ObjectToJsonTransformer transformer = new ObjectToJsonTransformer();

		for (TimedNumber timedNumber: timedNumbers) {
			Message<?> message = transformer.transform(new GenericMessage<>(timedNumber));
			channels.input().send(message);
		}

		verify(consumerService, times(4)).consume(any(TimedNumber.class));

	}

}
