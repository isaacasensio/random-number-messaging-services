package com.github.iam.number.consumer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TimedNumber {

    @JsonProperty
    private Integer number;

    @JsonProperty
    private Long createdAt;

    @JsonCreator
    public TimedNumber(@JsonProperty("number") Integer number, @JsonProperty("createdAt") Long createdAt) {
        this.number = number;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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

}
