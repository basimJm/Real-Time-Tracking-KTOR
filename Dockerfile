FROM ubuntu:latest
LABEL authors="BasimAbusnaimeh"

ENTRYPOINT ["top", "-b"]

# Use the official JDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the build output from your local machine to the container
COPY ./build/libs/*.jar app.jar

# Expose the port that your application listens on
EXPOSE 8022

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]