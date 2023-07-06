# Base image
FROM adoptopenjdk:17-jdk

# Set the working directory
WORKDIR /usr/src/data-warehouse

# Copy the application JAR file to the container
COPY --from=build /usr/src/data-warehouse/api/target/api-*.jar /opt/app.jar

# Expose the port your application listens on
EXPOSE 8080

# Define the entry point to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
