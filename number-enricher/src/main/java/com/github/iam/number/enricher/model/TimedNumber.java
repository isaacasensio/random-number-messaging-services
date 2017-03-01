package com.github.iam.number.enricher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Clock;

import static org.apache.commons.lang3.builder.ToStringStyle.NO_CLASS_NAME_STYLE;

public class TimedNumber {

    @JsonProperty
    private Integer number;

    @JsonProperty
    private Long createdAt;

    public TimedNumber() {
    }

    public TimedNumber(Integer number, Clock clock) {
        this.number = number;
        this.createdAt = clock.millis();
    }

    public TimedNumber(Number number, Clock clock) {
        this(number.getValue(), clock);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TimedNumber that = (TimedNumber) o;

        return new EqualsBuilder()
                .append(createdAt, that.createdAt)
                .append(number, that.number)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(number)
                .append(createdAt)
                .toHashCode();
    }

    public Integer getNumber() {
        return number;
    }
}
