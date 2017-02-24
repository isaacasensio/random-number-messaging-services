package com.github.iam;

public class SensorDataEnriched {

    private String name;
    private Float value;
    private int additionalValue;

    public SensorDataEnriched() {
    }

    public SensorDataEnriched(SensorData sensorData, int additionalValue) {
        this.name = sensorData.getName();
        this.value = sensorData.getValue();
        this.additionalValue = additionalValue;
    }

    public String getName() {
        return name;
    }

    public Float getValue() {
        return value;
    }

    public int getAdditionalValue() {
        return additionalValue;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
