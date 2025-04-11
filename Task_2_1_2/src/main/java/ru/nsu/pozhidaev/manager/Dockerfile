FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
# Копируем файлы конфигурации Gradle (будут доступны в корне проекта)
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY src /app/src
COPY gradle-8.13 /opt/

ENV PATH="/opt/gradle/gradle-8.13/bin:$PATH"
RUN gradle build -x test --no-daemon

COPY --from=builder /app/build/classes/java/main ./build/classes/java/main
CMD ["java", "-cp", "build/classes/java/main", "ru.nsu.pozhidaev.manager.Main"]