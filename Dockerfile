# Dockerfile
# Stage 1: build the multi-module project
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy root pom and module poms
COPY pom.xml mvnw mvnw.cmd ./
COPY sensor-simulator/pom.xml sensor-simulator/
COPY quality-monitor/pom.xml quality-monitor/
COPY application/pom.xml application/

# copy source trees
COPY sensor-simulator sensor-simulator/
COPY quality-monitor quality-monitor/
COPY application application/

# build without running tests
RUN mvn -B clean package -DskipTests

# Stage 2: runtime image
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# copy the assembled JAR from builder stage
COPY --from=build /workspace/application/target/application-1.0.0-SNAPSHOT.jar app.jar

# expose Spring Boot default port
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]