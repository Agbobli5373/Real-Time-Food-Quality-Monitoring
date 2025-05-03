package com.example.sensorsimulator;

import com.example.sensorsimulator.domain.SensorEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/**
 * Generates mock SensorEvent data at a fixed interval and publishes to the reactive sink.
 */
@Component
public class MockDataGenerator {

    private final Sinks.Many<SensorEvent> sink;
    private final Random random = new Random();

    public MockDataGenerator(Sinks.Many<SensorEvent> sink) {
        this.sink = sink;
    }

    @Scheduled(fixedRate = 5000)
    public void publishEvent() {
        SensorEvent event = new SensorEvent(
                UUID.randomUUID().toString(),
                Instant.now(),
                5 + random.nextDouble() * 15,       // temperature between 5 and 20
                30 + random.nextDouble() * 70       // humidity between 30 and 100
        );
        sink.tryEmitNext(event);
    }
}
