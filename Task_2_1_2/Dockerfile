FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/build/classes/java/main /app/build/classes/java/main
COPY --from=builder /app/build/resources/main /app/build/resources/main

ENTRYPOINT ["java"]
