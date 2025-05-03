package com.example.application;

import com.example.sensorsimulator.domain.SensorEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class ReactiveBridge {

    @Bean
    public Sinks.Many<SensorEvent> sensorEventSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<SensorEvent> sensorEventFlux(Sinks.Many<SensorEvent> sink) {
        return sink.asFlux();
    }
}
