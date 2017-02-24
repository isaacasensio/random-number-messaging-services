package com.github.iam.enrich;

import com.github.iam.filter.EvenFilteringApplication;
import com.github.iam.sink.NumberSinkApplication;
import com.github.iam.model.Number;
import com.github.iam.model.TimedNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@SpringBootApplication
@EnableBinding(EnricherChannels.class)
public class EnrichAndMultiplyApplication {

	@Autowired
	private Clock clock;

	@Bean
	public Clock clock(){
		return Clock.systemUTC();
	}


	@Transformer(inputChannel = EnricherChannels.INPUT, outputChannel = EnricherChannels.INPUT_TRIPLICATE)
	public TimedNumber addCurrentMillisToNumber(Number data) {
		return new TimedNumber(data, clock);
	}

	@Splitter(inputChannel = EnricherChannels.INPUT_TRIPLICATE, outputChannel = EnricherChannels.OUTPUT_TRIPLICATE)
	public List<TimedNumber> createAsManyMessageAsInternalValue(TimedNumber event) {

		List<TimedNumber> clonedNumbers = IntStream.range(0, event.getValue())
				.mapToObj(i -> event)
				.collect(Collectors.toList());

		return clonedNumbers;
	}

}
