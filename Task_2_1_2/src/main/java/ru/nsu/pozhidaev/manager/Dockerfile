# Этап сборки проекта
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
# Копируем все файлы в контейнер
COPY . .
# Сборка проекта (рекомендуется добавить clean для гарантированного билда)
RUN gradle clean build --no-daemon

# Финальный этап: запуск приложения
FROM openjdk:21-slim
WORKDIR /app
# Копируем jar-файл, сгенерированный на предыдущем этапе
COPY --from=builder /app/build/libs/Task_2_1_2-1.0-SNAPSHOT.jar .
# При необходимости можно указать EXPOSE, если приложение слушает определённый порт (например, 5005)
EXPOSE 5005
CMD ["java", "-jar", "Task_2_1_2-1.0-SNAPSHOT.jar"]