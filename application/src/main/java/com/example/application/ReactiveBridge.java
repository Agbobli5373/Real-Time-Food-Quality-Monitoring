package com.example.application;

import com.example.sensorsimulator.domain.SensorEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Configuration class that provides reactive bridges for handling {@link SensorEvent} streams.
 * <p>
 * Defines beans for a multicast {@link Sinks.Many} to emit sensor events and a {@link Flux}
 * to consume those events reactively throughout the application.
 * </p>
 *
 * <ul>
 *   <li>{@code sensorEventSink}: A multicast sink for publishing {@link SensorEvent} instances,
 *       supporting backpressure buffering.</li>
 *   <li>{@code sensorEventFlux}: A {@link Flux} view of the sink for reactive consumption of events.</li>
 * </ul>
 */
@Configuration
public class ReactiveBridge {

    @Bean
    Sinks.Many<SensorEvent> sensorEventSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    Flux<SensorEvent> sensorEventFlux(Sinks.Many<SensorEvent> sink) {
        return sink.asFlux();
    }
}
