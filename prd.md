# PRD: Real-Time Food Quality Monitoring Demo (Reactor & Modularity Focus)

**Version:** 1.1
**Date:** 2023-10-27
**Author:** [Your Name]

## I. Introduction

### Purpose

This document outlines the requirements for a **demonstration project** designed to showcase proficiency in **Project Reactor**, reactive programming principles, and **logical application modularity** using Java and Spring Boot. The project simulates a core component of a real-time food quality monitoring system by processing a stream of mock sensor data reactively within a single, modular Spring Boot application.

This project is built for learning purposes and specifically to demonstrate relevant technical skills for an application to Picnic Technologies. It intentionally simplifies aspects like external dependencies and deployment to focus on the reactive processing core and clean internal structure. It aligns conceptually with the principles promoted by frameworks like **Spring Modulith**, emphasizing well-defined boundaries between logical modules within a single deployment unit, even though it may not utilize the full runtime enforcement capabilities of Spring Modulith for this focused demo.

### Target Audience

*   Interviewer / Technical Reviewer at Picnic Technologies
*   Developer (Self-learning and demonstration)

## II. Project Overview

### Vision

To clearly demonstrate the effective use of reactive streams (Project Reactor) within a well-structured, logically modular Spring Boot application to handle simulated real-time data processing tasks, specifically anomaly detection based on configurable rules. The structure aims to mirror the benefits of clear domain boundaries often sought in microservices but within a single application context.

### Core Functionality

*   **Mock Data Generation:** Internal, scheduled generation of simulated sensor data (temperature, humidity) representing food conditions, encapsulated within its own module.
*   **Reactive Bridging:** Seamlessly bridge the scheduled, imperative data generation into a reactive stream (`Flux`) for processing, managed by the application's core module.
*   **Reactive Processing:** Implement a processing pipeline using Project Reactor operators (`Flux`) to analyze the stream of sensor data, encapsulated within its own module.
*   **Anomaly Detection:** Filter events within the reactive pipeline based on configurable thresholds (e.g., temperature exceeding a safe limit).
*   **Action on Anomaly:** Log detected anomalies clearly to the console for observation.

## III. Technology Stack (Demonstration Scope)

*   **Language:** Java (JDK 17+)
*   **Framework:** Spring Boot 3+
*   **Reactive Library:** Project Reactor (`reactor-core`)
*   **Scheduling:** Spring Scheduling (`@Scheduled`)
*   **Build Tool:** Maven / Gradle (with multi-module support)
*   **Logging:** SLF4j + Logback (default in Spring Boot)
*   **(Conceptual Alignment):** Spring Modulith principles (logical modularity, clear boundaries).

## IV. User Stories (Conceptual & Reviewer Focused)

*   **As a reviewer,** I want to see how Project Reactor can process a continuous stream of simulated sensor events originating from a scheduled task.
*   **As a reviewer,** I want to observe how reactive operators (e.g., `filter`, `doOnNext`, `map`) are correctly applied to implement anomaly detection logic based on defined rules.
*   **As a reviewer,** I want to understand how the application is structured into clearly decoupled logical modules (`application`, `sensor-simulator`, `quality-monitor`) using standard Java build tools, reflecting principles similar to those encouraged by Spring Modulith.
*   **As an operations manager (conceptually),** I want anomalies (like temperature thresholds being breached in simulated data) to be immediately identified and logged by the system for potential action visibility.

## V. Functional Requirements (Demo Scope)

*   **FR1: Mock Data Generation:** The `sensor-simulator` module MUST contain a component that uses Spring's `@Scheduled` annotation to periodically generate mock `SensorEvent` objects (e.g., containing `deviceId`, `timestamp`, `temperature`, `humidity`).
*   **FR2: Reactive Bridge:** The `application` module MUST manage a reactive bridge (e.g., using `Sinks.Many` and its associated `Flux`) to connect the data generation component (imperative) to the processing component (reactive).
*   **FR3: Decoupled Publishing:** The data generation component MUST publish generated `SensorEvent` objects into the reactive bridge's `Sink`. The `sensor-simulator` MUST NOT have a direct dependency on the `quality-monitor` module.
*   **FR4: Decoupled Consumption:** The `quality-monitor` module MUST contain a component that subscribes to the `Flux` provided by the reactive bridge. The `quality-monitor` MUST NOT have a direct dependency on the `sensor-simulator` module.
*   **FR5: Reactive Pipeline:** The `quality-monitor` module MUST implement a processing pipeline using Project Reactor `Flux` operators on the consumed stream.
*   **FR6: Anomaly Filtering:** The reactive pipeline MUST include a `filter` operation to identify `SensorEvent` objects that violate configurable thresholds (e.g., `temperature > 8.0` OR `humidity < 50.0`).
*   **FR7: Configurable Thresholds:** Anomaly detection thresholds MUST be configurable, preferably via Spring Boot's `application.properties` or `application.yml`.
*   **FR8: Anomaly Logging:** Upon detecting an anomalous event passing the filter, the pipeline MUST log a clear, informative message to the console indicating the anomaly (e.g., "ALERT: High temperature detected for device [deviceId]: [value]°C"). Use SLF4j/Logback for logging.
*   **FR9: Modular Structure:** The application MUST be structured into three distinct Maven/Gradle modules (`application`, `sensor-simulator`, `quality-monitor`) demonstrating separation of concerns and clear logical boundaries, conceptually aligning with Spring Modulith's goals for maintainable monoliths.

## VI. Non-Functional Requirements (Demo Scope)

*   **NFR1: Clarity:** The implementation MUST clearly demonstrate the use of Project Reactor operators and reactive programming principles. Code should be well-structured and include comments explaining the reactive flow and key decisions.
*   **NFR2: Correctness:** The reactive pipeline MUST correctly identify and log anomalies based on the configured thresholds and generated mock data.
*   **NFR3: Simplicity:** The demo MUST focus exclusively on the core reactive processing logic within the defined modules, avoiding unnecessary complexity from external systems or dependencies not listed in the Tech Stack.
*   **NFR4: Modularity & Decoupling:** The codebase MUST strictly adhere to the defined modular structure, ensuring the `sensor-simulator` and `quality-monitor` modules are decoupled via the bridge managed by the `application` module. Dependencies between modules should be minimized and explicit.

## VII. Out of Scope (Explicitly)

The following items are explicitly **out of scope** for this demonstration project:

*   External REST API endpoints for data ingestion or querying.
*   Persistence of sensor data or alerts (No Database interaction).
*   Integration with message brokers (No Kafka, RabbitMQ, etc.).
*   Web Dashboard, User Interface, or any frontend components.
*   External notification channels (Email, SMS, Push Notifications).
*   User authentication or authorization.
*   Docker containerization.
*   Kubernetes deployment manifests or orchestration.
*   Cloud infrastructure setup or deployment (AWS, GCP, Azure).
*   High-throughput performance testing or benchmarking.
*   Complex error handling strategies beyond basic logging within the stream.
*   End-to-end or integration tests involving external systems (as none are used).
*   Runtime module boundary enforcement using Spring Modulith tooling (though conceptual alignment is desired).

## VIII. Implementation Phases

1.  **Phase 1: Project Setup & Module Structure**
    *   Create a multi-module Maven/Gradle project with the defined structure: `application`, `sensor-simulator`, `quality-monitor`.
    *   Configure parent POM/build script for managing dependencies (Spring Boot, Reactor).
    *   Set up the basic Spring Boot application class in the `application` module.
2.  **Phase 2: `sensor-simulator` Module Implementation**
    *   Define the `SensorEvent` data class (potentially in a shared `common` or `events` module, or within the simulator if strictly separated).
    *   Implement the `MockDataGenerator` component.
    *   Implement the `@Scheduled` task component that uses the generator.
    *   Define an interface or mechanism (like requiring a `Consumer<SensorEvent>` or `FluxSink<SensorEvent>`) for publishing events, to be provided by the `application` module.
3.  **Phase 3: `quality-monitor` Module Implementation**
    *   Implement the `QualityMonitor` component responsible for processing.
    *   Define a mechanism (like requiring a `Flux<SensorEvent>`) for receiving the event stream, to be provided by the `application` module.
    *   Implement the core Project Reactor pipeline (`filter`, `doOnNext`, etc.) within this component.
    *   Implement the anomaly logging logic.
4.  **Phase 4: `application` Module Wiring & Bridge**
    *   Implement the `ReactiveBridge` component using `Sinks.Many` to create the `Sink` and `Flux`.
    *   Configure Spring Beans to instantiate components from `sensor-simulator` and `quality-monitor`.
    *   Inject the `Sink` into the scheduled task component (`sensor-simulator`).
    *   Inject the `Flux` into the quality monitor component (`quality-monitor`).
    *   Ensure `@EnableScheduling` is active.
    *   Load configuration for thresholds (`application.yml`/`.properties`).
5.  **Phase 5: Testing & Refinement**
    *   Run the application and observe console logs to verify:
        *   Scheduled task runs periodically.
        *   Events flow through the bridge.
        *   Anomalies are correctly filtered and logged based on configuration.
    *   Add basic unit tests for the generator logic and potentially using `StepVerifier` for the reactive pipeline in the `quality-monitor` module.
    *   Review code for clarity, comments, and adherence to best practices.

## IX. Best Practices Demonstrated

This project aims to demonstrate the following best practices:

*   **Logical Modularity:** Structuring the application into distinct modules with clear responsibilities, promoting maintainability and separation of concerns (aligning with Spring Modulith principles).
*   **Explicit Dependencies:** Managing dependencies clearly between modules using the build tool, avoiding tight coupling.
*   **Reactive Programming Principles:** Correctly applying Project Reactor for non-blocking processing of an event stream. Using declarative operators (`filter`, `map`, `doOnNext`).
*   **Decoupling via Bridge:** Using a reactive bridge (`Sinks`/`Flux`) as an effective in-memory mechanism to decouple the data producer (simulator) from the consumer (monitor) within the same application.
*   **Configuration Management:** Externalizing configurable values (like anomaly thresholds) using Spring Boot's standard configuration mechanisms.
*   **Immutability:** Designing event objects (`SensorEvent`) to be immutable is preferred.
*   **Clear Logging:** Providing meaningful log output for observing application behavior and detected anomalies.
*   **Clean Code:** Adhering to standard Java coding conventions, using meaningful names, and keeping methods concise.