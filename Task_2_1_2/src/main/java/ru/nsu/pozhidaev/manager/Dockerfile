FROM openjdk:21
WORKDIR /app
COPY . .
RUN javac Manager.java
RUN javac Main.java
CMD ["java", "Main"]