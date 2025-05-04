package com.example.application;

import com.example.sensorsimulator.domain.SensorEvent;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * REST controller that provides a Server-Sent Events (SSE) endpoint for streaming sensor events to clients.
 * <p>
 * This controller exposes an endpoint at <code>/events/stream</code> which streams real-time {@link SensorEvent}
 * data using the <code>text/event-stream</code> media type. Clients can subscribe to this endpoint to receive
 * continuous updates as new sensor events are emitted.
 * </p>
 *
 * <p>
 * Dependencies:
 * <ul>
 *   <li>{@link Flux} of {@link SensorEvent} - the reactive stream of sensor events to be sent to clients.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * curl http://localhost:8080/events/stream
 * </pre>
 * </p>
 */
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
