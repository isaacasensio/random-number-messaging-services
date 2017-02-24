package com.github.iam;

public class SensorData {

    private String name;
    private Float value;

    public SensorData() {
    }

    public SensorData(String name, Float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Float getValue() {
        return value;
    }

}
