FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/IWDB0-0.0.1-SNAPSHOT.jar /app/IWDB0.jar

# Expose the port
EXPOSE 8083

LABEL name="iwdb-img"

# Run the application
CMD ["java", "-jar", "IWDB0.jar"]

