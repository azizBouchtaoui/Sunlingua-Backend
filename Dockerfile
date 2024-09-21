FROM openjdk:17-jdk-alpine
LABEL authors="Aziz BOUCHTAOUI"
ARG JAR_FILE=target/*.jar

WORKDIR /app
COPY ./target/Sunlingua-Backend-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
