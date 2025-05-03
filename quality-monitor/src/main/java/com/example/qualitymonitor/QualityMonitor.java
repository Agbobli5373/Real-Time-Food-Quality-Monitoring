package com.example.qualitymonitor;

import com.example.sensorsimulator.domain.SensorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import jakarta.annotation.PostConstruct;

/**
 * Subscribes to the sensor event flux and logs anomalies based on thresholds.
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
            .filter(event -> event.getTemperature() > temperatureThreshold
                          || event.getHumidity() < humidityThreshold)
            .doOnNext(event -> logger.warn(
                "ALERT: Anomaly detected for device {} – temp={}°C, humidity={}%",
                event.getDeviceId(),
                event.getTemperature(),
                event.getHumidity()))
            .subscribe();
    }
}
