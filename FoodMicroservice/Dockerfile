# Use a Java runtime as base image
FROM eclipse-temurin:21


# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY FoodMicroservice-0.0.1-SNAPSHOT.jar /app/microservice.jar

EXPOSE 8081

# Specify the command to run the Spring Boot application
CMD ["java", "-jar", "microservice.jar"]