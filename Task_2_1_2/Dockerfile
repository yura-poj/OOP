FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
COPY gradle-8.13 /opt/
ENV PATH="/opt/gradle/gradle-8.13/bin:$PATH"

RUN gradle build -x test -x checkstyleMain -x checkstyleTest --no-daemon