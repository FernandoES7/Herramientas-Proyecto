# Multi-stage Dockerfile for the ConfigServer Spring Boot app (Java 25)

# Build stage: compile with Maven + Temurin JDK 21
FROM eclipse-temurin:21 AS build
WORKDIR /workspace
COPY . .
RUN ./mvnw clean package -DskipTests

# Runtime stage: lightweight Temurin JRE 21
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
# Copy the built Spring Boot fat jar from the build stage
COPY --from=build /workspace/target/*.jar /app/app.jar
# Expose configured server port
EXPOSE 8080
# Allow passing JVM options via JAVA_OPTS at runtime
ENV JAVA_OPTS="-Xms256m -Xmx512m"
# Start the Spring Boot application
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
