package com.example.application;

import com.example.sensorsimulator.domain.SensorEvent;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SseController {

    private final Flux<SensorEvent> eventFlux;

    public SseController(Flux<SensorEvent> eventFlux) {
        this.eventFlux = eventFlux;
    }

    @GetMapping(value = "/events/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<SensorEvent>> streamEvents() {
        return eventFlux.map(event ->
            ServerSentEvent.<SensorEvent>builder()
                .event("sensorEvent")
                .data(event)
                .build()
        );
    }
}
