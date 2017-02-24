package com.github.iam;

import com.sun.tools.javac.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.aggregate.AggregateApplication;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SensorDataAggregateApplication.class)
@DirtiesContext
public class SensorDataEnricherApplicationTests {

	@Autowired
	public AggregateApplication aggregateApplication;

	@Autowired
	private MessageCollector messageCollector;

	@Test
	public void allMessagesNotStartingWithDigitAreDiscarded() {

		List<SensorData> dataPayloads = List.of(
				new SensorData("aa", 1f),
				new SensorData("1a", 2f),
				new SensorData("ga", 3f),
				new SensorData("2a", 4f)
				);

		for (SensorData dataPayload: dataPayloads) {
			getDataFilteringProcessor().input().send(new GenericMessage<>(dataPayload));
		}

		Message<SensorData> firstMessage = (Message<SensorData>) messageCollector.forChannel(getDataFilteringProcessor().output()).poll();
		Message<SensorData> secondMessage = (Message<SensorData>) messageCollector.forChannel(getDataFilteringProcessor().output()).poll();
		Message<SensorData> thirdMessage = (Message<SensorData>) messageCollector.forChannel(getDataFilteringProcessor().output()).poll();

		assertThat(firstMessage.getPayload(), is(new SensorData("1a", 2f)));
		assertThat(secondMessage.getPayload(), is(new SensorData("2a", 4f)));
		assertNull(thirdMessage);
	}

	private Processor getDataFilteringProcessor() {
		return aggregateApplication.getBinding(Processor.class, "filtering");
	}


}