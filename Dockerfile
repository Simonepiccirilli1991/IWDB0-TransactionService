FROM openjdk:17-slim as builder

LABEL name="IWDB0"

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Build all the dependencies in preparation to go offline. 
# This is a separate step so the dependencies will be cached unless the pom.xml file has changed.
RUN mvn dependency:go-offline -B

# Copy the project source code to the container
COPY . .

# Build the application
RUN mvn package

FROM openjdk:17-slim

# Set the working directory to /app
WORKDIR /app

# Copy the built jar file from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Set the environment variable
ENV JAVA_OPTS="-Xmx256m -Xms256m"

# Expose port 8083
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "app.jar"]
