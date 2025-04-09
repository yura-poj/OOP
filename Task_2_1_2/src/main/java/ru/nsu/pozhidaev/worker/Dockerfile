FROM openjdk:21
WORKDIR /app
COPY . .
RUN javac Worker.java
RUN javac Main.java
CMD ["java", "Main"]