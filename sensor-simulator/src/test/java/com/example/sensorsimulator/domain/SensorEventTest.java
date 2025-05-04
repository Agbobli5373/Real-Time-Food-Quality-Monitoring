package com.example.sensorsimulator.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

class SensorEventTest {

    @Test
    void testSensorEventCreation() {
        // Arrange
        String deviceId = "test-device-123";
        Instant timestamp = Instant.now();
        double temperature = 15.5;
        double humidity = 75.0;

        // Act
        SensorEvent event = new SensorEvent(deviceId, timestamp, temperature, humidity);

        // Assert
        assertEquals(deviceId, event.deviceId(), "Device ID should match");
        assertEquals(timestamp, event.timestamp(), "Timestamp should match");
        assertEquals(temperature, event.temperature(), "Temperature should match");
        assertEquals(humidity, event.humidity(), "Humidity should match");
    }

    @Test
    void testToString() {
        // Arrange
        String deviceId = "test-device-123";
        Instant timestamp = Instant.now();
        double temperature = 15.5;
        double humidity = 75.0;
        SensorEvent event = new SensorEvent(deviceId, timestamp, temperature, humidity);

        // Act
        String result = event.toString();

        // Assert
        assertTrue(result.contains(deviceId), "toString should contain deviceId");
        assertTrue(result.contains(timestamp.toString()), "toString should contain timestamp");
        assertTrue(result.contains(String.valueOf(temperature)), "toString should contain temperature");
        assertTrue(result.contains(String.valueOf(humidity)), "toString should contain humidity");
    }
}