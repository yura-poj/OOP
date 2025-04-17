FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle-8.13 /opt/
COPY gradle /app/gradle
COPY src /app/src

ENV PATH="/opt/gradle/gradle-8.13/bin:$PATH"
RUN gradle build -x test -x checkstyleMain -x checkstyleTest --no-daemon

COPY --from=builder /app/build/classes/java/main ./build/classes/java/main
CMD ["java", "-cp", "build/classes/java/main", "ru.nsu.pozhidaev.worker.Worker"]