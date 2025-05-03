package com.example.application;

import com.example.sensorsimulator.domain.SensorEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SseControllerTest {

    @Mock
    private Flux<SensorEvent> eventFlux;

    @InjectMocks
    private SseController controller;

    @Test
    void testStreamEvents() {
        // Arrange
        SensorEvent event1 = new SensorEvent("test-device-1", Instant.now(), 10.0, 60.0);
        SensorEvent event2 = new SensorEvent("test-device-2", Instant.now(), 15.0, 70.0);
        
        when(eventFlux.map(org.mockito.ArgumentMatchers.<java.util.function.Function<SensorEvent, ServerSentEvent<SensorEvent>>>any()))
            .thenReturn(Flux.just(
                ServerSentEvent.<SensorEvent>builder().event("sensorEvent").data(event1).build(),
                ServerSentEvent.<SensorEvent>builder().event("sensorEvent").data(event2).build()
            ));
        
        // Act & Assert
        StepVerifier.create(controller.streamEvents())
            .assertNext(event -> {
                assert "sensorEvent".equals(event.event());
                assert event1.equals(event.data());
            })
            .assertNext(event -> {
                assert "sensorEvent".equals(event.event());
                assert event2.equals(event.data());
            })
            .verifyComplete();
    }
}