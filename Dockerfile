FROM openjdk:17-jdk-alpine
LABEL authors="Aziz BOUCHTAOUI"

WORKDIR /app

# Copy the application JAR
COPY target/Sunlingua-Backend-0.0.1.jar app.jar

# Copy the template files (if they are not packaged into the JAR)
COPY src/main/resources/templates /app/templates

ENTRYPOINT ["java", "-jar", "/app/app.jar"]


EXPOSE 8080
