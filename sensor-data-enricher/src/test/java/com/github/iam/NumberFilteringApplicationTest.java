package com.github.iam;

import com.github.iam.filter.EvenFilteringApplication;
import com.github.iam.filter.FilteringChannels;
import com.github.iam.model.Number;
import com.sun.tools.javac.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EvenFilteringApplication.class)
@DirtiesContext
public class NumberFilteringApplicationTest {

	@Autowired
	private FilteringChannels filtering;

	@Autowired
	private MessageCollector messageCollector;

	@Test
	public void warningAndErrorMessagesAreConsumed() {

		List<Number> numbers = List.of(
				new Number(1),
				new Number(2),
				new Number(3),
				new Number(4),
				new Number(5)
				);

		for (Number number : numbers) {
			filtering.input().send(new GenericMessage<>(number));
		}

		Message<Number> firstMessage = (Message<Number>) messageCollector.forChannel(filtering.output()).poll();
		Message<Number> secondMessage = (Message<Number>) messageCollector.forChannel(filtering.output()).poll();
		Message<Number> thirdMessage = (Message<Number>) messageCollector.forChannel(filtering.output()).poll();

		assertThat(firstMessage.getPayload(), is(new Number(2)));
		assertThat(secondMessage.getPayload(), is(new Number(4)));
		assertNull(thirdMessage);
	}

	@Test
	public void infoMessageEventsAreDiscarded() {

		List<Number> dataPayloads = List.of(
				new Number(3),
				new Number(5)
		);

		for (Number dataPayload: dataPayloads) {
			filtering.input().send(new GenericMessage<>(dataPayload));
		}

		Message<Number> firstMessage = (Message<Number>) messageCollector.forChannel(filtering.output()).poll();
		Message<Number> secondMessage = (Message<Number>) messageCollector.forChannel(filtering.output()).poll();

		assertNull(firstMessage);
		assertNull(secondMessage);
	}


}