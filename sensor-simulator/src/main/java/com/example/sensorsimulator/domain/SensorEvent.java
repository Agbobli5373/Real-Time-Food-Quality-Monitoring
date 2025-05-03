package com.example.sensorsimulator.domain;

import java.time.Instant;

/**
 * Immutable representation of a sensor event.
 */
public final class SensorEvent {
    private final String deviceId;
    private final Instant timestamp;
    private final double temperature;
    private final double humidity;

    public SensorEvent(String deviceId, Instant timestamp, double temperature, double humidity) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
               "deviceId='" + deviceId + '\'' +
               ", timestamp=" + timestamp +
               ", temperature=" + temperature +
               ", humidity=" + humidity +
               '}';
    }
}
