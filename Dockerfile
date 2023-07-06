FROM openjdk:17
WORKDIR /usr/src/data-warehouse/api/target/api-*.jar /opt/app.jar

COPY . .
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
