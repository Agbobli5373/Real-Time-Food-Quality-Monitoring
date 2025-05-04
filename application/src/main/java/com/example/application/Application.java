package com.example.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main entry point for the Real-Time Food Quality Monitoring application.
 * <p>
 * This class bootstraps the Spring Boot application, enabling component scanning
 * for the base package {@code com.example} and scheduling support via {@link EnableScheduling}.
 * </p>
 *
 * <p>
 * Usage: Run the {@code main} method to start the application.
 * </p>
 */
@SpringBootApplication(scanBasePackages = "com.example")
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
