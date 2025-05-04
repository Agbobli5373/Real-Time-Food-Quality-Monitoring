package com.example.sensorsimulator.domain;

import java.time.Instant;

/**
 * Immutable representation of a sensor event.
 */
public record SensorEvent(String deviceId, Instant timestamp, double temperature, double humidity) {

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
