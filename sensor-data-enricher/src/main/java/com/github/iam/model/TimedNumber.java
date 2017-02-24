package com.github.iam.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Clock;

public class TimedNumber {

    @JsonProperty
    private Number number;

    @JsonProperty
    private long currentTimeMillis;

    public TimedNumber(Number number, Clock clock) {
        this.number = number;
        currentTimeMillis = clock.millis();
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Number getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TimedNumber that = (TimedNumber) o;

        return new EqualsBuilder()
                .append(currentTimeMillis, that.currentTimeMillis)
                .append(number, that.number)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(number)
                .append(currentTimeMillis)
                .toHashCode();
    }

    public Integer getValue() {
        return number.getValue();
    }
}
