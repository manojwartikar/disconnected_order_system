# Use an official Java runtime as a parent image 
FROM openjdk:8-jdk-alpine 
 
# Set the working directory in the container 
WORKDIR /app 
 
# Add the application's jar to the container 
COPY target/order-management-system-1.0-SNAPSHOT.jar app.jar 
 
# Make port 8080 available to the world outside this container 
EXPOSE 8080 
 
# Run the jar file 
ENTRYPOINT ["java","-jar","/app/app.jar"] 