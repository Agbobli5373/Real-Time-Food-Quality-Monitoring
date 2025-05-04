package com.example.sensorsimulator.domain;

import java.time.Instant;


/**
 * Represents a sensor event containing environmental data from a device.
 *
 * @param deviceId    the unique identifier of the sensor device
 * @param timestamp   the timestamp when the event was recorded
 * @param temperature the measured temperature value
 * @param humidity    the measured humidity value
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
