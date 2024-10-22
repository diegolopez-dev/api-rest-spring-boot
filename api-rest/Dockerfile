FROM maven:3-eclipse-temurin-17
COPY . .
RUN mvn clean package -DskipTest


FROM amazoncorretto:17-alpine-jdk
COPY api-rest/target/api-rest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]