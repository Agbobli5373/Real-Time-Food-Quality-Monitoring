package com.example.sensorsimulator;

import com.example.sensorsimulator.domain.SensorEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Sinks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MockDataGeneratorTest {

    @Mock
    private Sinks.Many<SensorEvent> sink;

    private MockDataGenerator generator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        generator = new MockDataGenerator(sink);
    }

    @Test
    void testPublishEvent() {
        // Act
        generator.publishEvent();

        // Assert
        ArgumentCaptor<SensorEvent> eventCaptor = ArgumentCaptor.forClass(SensorEvent.class);
        verify(sink).tryEmitNext(eventCaptor.capture());

        SensorEvent capturedEvent = eventCaptor.getValue();
        assertNotNull(capturedEvent, "Event should not be null");
        assertNotNull(capturedEvent.deviceId(), "Device ID should not be null");
        assertNotNull(capturedEvent.timestamp(), "Timestamp should not be null");
        
        // Verify temperature is within expected range (5-20)
        assertTrue(capturedEvent.temperature() >= 5.0, "Temperature should be >= 5.0");
        assertTrue(capturedEvent.temperature() <= 20.0, "Temperature should be <= 20.0");
        
        // Verify humidity is within expected range (30-100)
        assertTrue(capturedEvent.humidity() >= 30.0, "Humidity should be >= 30.0");
        assertTrue(capturedEvent.humidity() <= 100.0, "Humidity should be <= 100.0");
    }
}