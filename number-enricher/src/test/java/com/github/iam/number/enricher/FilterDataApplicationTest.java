package com.github.iam.number.enricher;

import com.github.iam.number.enricher.config.filter.FilterDataApplication;
import com.github.iam.number.enricher.model.Number;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FilterDataApplication.class)
@TestPropertySource(properties = {
        "spring.cloud.stream.bindings.input.contentType = application/x-java-object",
        "spring.cloud.stream.bindings.output.contentType = application/x-java-object"
})
@DirtiesContext
public class FilterDataApplicationTest {

	@Autowired
	private Processor processor;

	@Autowired
	private MessageCollector messageCollector;

	@Test
	public void onlyEvenNumbersAreProcessed() {

		List<Number> numbers = Arrays.asList(
				new Number(1),
				new Number(2),
				new Number(3),
				new Number(4),
				new Number(5)
				);

		for (Number number : numbers) {
			processor.input().send(new GenericMessage<>(number));
		}

		Message<Number> firstMessage = consumeMessage();
		Message<Number> secondMessage = consumeMessage();
		Message<Number> thirdMessage = consumeMessage();

		assertThat(firstMessage.getPayload(), is(new Number(2)));
		assertThat(secondMessage.getPayload(), is(new Number(4)));
		assertNull(thirdMessage);
	}

	@Test
	public void oddNumbersAreDiscarded() {

		List<Number> dataPayloads = Arrays.asList(
				new Number(3),
				new Number(5)
		);

		for (Number dataPayload: dataPayloads) {
			processor.input().send(new GenericMessage<>(dataPayload));
		}

		Message<Number> firstMessage = consumeMessage();
		Message<Number> secondMessage = consumeMessage();

		assertNull(firstMessage);
		assertNull(secondMessage);
	}

	private Message<Number> consumeMessage() {
	    return (Message<Number>) messageCollector.forChannel(processor.output()).poll();
	}


}