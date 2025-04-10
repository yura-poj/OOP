FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY ../../ .
RUN gradle build --no-daemon
CMD ["java", "-cp", "build/classes/java/main", "ru.nsu.pozhidaev.worker.Main"]