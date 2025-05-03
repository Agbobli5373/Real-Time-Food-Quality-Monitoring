package com.example.application;

import com.example.sensorsimulator.domain.SensorEvent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.time.Instant;

class ReactiveBridgeTest {

    @Test
    void testSinkToFluxBridge() {
        // Arrange
        ReactiveBridge bridge = new ReactiveBridge();
        Sinks.Many<SensorEvent> sink = bridge.sensorEventSink();
        
        // Create test events
        SensorEvent event1 = new SensorEvent("test-device-1", Instant.now(), 10.0, 60.0);
        SensorEvent event2 = new SensorEvent("test-device-2", Instant.now(), 15.0, 70.0);
        
        // Act & Assert
        StepVerifier.create(bridge.sensorEventFlux(sink).take(2))
            .then(() -> {
                sink.tryEmitNext(event1);
                sink.tryEmitNext(event2);
            })
            .expectNext(event1)
            .expectNext(event2)
            .verifyComplete();
    }
}