package com.github.iam.filter;

import com.github.iam.model.Number;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Filter;


@SpringBootApplication
@EnableBinding(FilteringChannels.class)
public class EvenFilteringApplication {

	@Filter(inputChannel = FilteringChannels.INPUT, outputChannel = FilteringChannels.OUTPUT)
	public boolean onlyOddNumbersAreProcessed(Number data) {
		return data.isOdd();
	}
}
