FROM openjdk:17-jdk-alpine
LABEL authors="Aziz BOUCHTAOUI"
VOLUME /tmp


COPY target/Sunlingua-Backend-0.0.1.jar app.jar



EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
