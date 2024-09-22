FROM openjdk:17-jdk-alpine
LABEL authors="Aziz BOUCHTAOUI"
VOLUME /tmp

ARG JAR_FILE=target/*.jar
WORKDIR /app

COPY ${JAR_FILE} app.jar
COPY src/main/resources/templates /app



EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
