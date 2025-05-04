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
/**
 * MockDataGenerator is a Spring component responsible for generating and publishing mock sensor data events
 * at a fixed interval. It uses a {@link Sinks.Many} to emit {@link SensorEvent} objects containing randomly
 * generated temperature and humidity values.
 *
 * <p>
 * The temperature is randomly generated between 5 and 20 degrees, and the humidity is randomly generated
 * between 30 and 100 percent. Each event is assigned a unique identifier and the current timestamp.
 * </p>
 *
 * <p>
 * The {@link #publishEvent()} method is scheduled to run every 5 seconds, emitting a new event to the sink.
 * </p>
 *
 * @author Isaac Agbobli
 * @version 1.0
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
