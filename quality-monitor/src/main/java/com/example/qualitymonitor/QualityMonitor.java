package com.example.qualitymonitor;

import com.example.sensorsimulator.domain.SensorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import jakarta.annotation.PostConstruct;


/**
 * Component responsible for monitoring real-time sensor events and detecting anomalies
 * based on configurable temperature and humidity thresholds.
 * <p>
 * Subscribes to a stream of {@link SensorEvent} objects and logs a warning whenever
 * an event exceeds the specified temperature threshold or falls below the humidity threshold.
 * </p>
 *
 * <p>
 * Configuration properties:
 * <ul>
 * <li><b>thresholds.temperature</b> - The temperature threshold for anomaly detection.</li>
 * <li><b>thresholds.humidity</b> - The humidity threshold for anomaly detection.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Usage: This component is automatically started after construction and does not require
 * manual invocation.
 * </p>
 *
 * @author Isaac Agbobli
 */
@Component
public class QualityMonitor {

    private static final Logger logger = LoggerFactory.getLogger(QualityMonitor.class);

    private final Flux<SensorEvent> eventFlux;
    private final double temperatureThreshold;
    private final double humidityThreshold;

    public QualityMonitor(
            Flux<SensorEvent> eventFlux,
            @Value("${thresholds.temperature}") double temperatureThreshold,
            @Value("${thresholds.humidity}") double humidityThreshold) {
        this.eventFlux = eventFlux;
        this.temperatureThreshold = temperatureThreshold;
        this.humidityThreshold = humidityThreshold;
    }

    @PostConstruct
    public void startMonitoring() {
        eventFlux
            .filter(event -> event.temperature() > temperatureThreshold
                          || event.humidity() < humidityThreshold)
            .doOnNext(event -> logger.warn(
                "ALERT: Anomaly detected for device {} – temp={}°C, humidity={}%",
                event.deviceId(),
                event.temperature(),
                event.humidity()))
            .subscribe();
    }
}
