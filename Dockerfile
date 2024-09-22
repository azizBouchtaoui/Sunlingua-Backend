FROM openjdk:17-jdk-alpine
LABEL authors="Aziz BOUCHTAOUI"

WORKDIR /app

COPY target/Sunlingua-Backend-0.0.1.jar app.jar


EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
