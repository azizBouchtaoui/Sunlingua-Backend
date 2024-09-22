FROM openjdk:17-jdk-alpine
LABEL authors="Aziz BOUCHTAOUI"

# Install Maven
RUN apk add --no-cache maven

# Set the working directory inside the container
WORKDIR /app
COPY --chown=node:node ./package*.json ./

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Copy the JAR file to the app directory (assuming your JAR is generated in target/)
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
