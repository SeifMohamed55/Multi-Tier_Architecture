# Use a Java runtime as base image
FROM eclipse-temurin:21




# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY spring-0.0.2-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# Specify the command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]