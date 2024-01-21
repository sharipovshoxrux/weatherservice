FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/weatherservice.jar /app

EXPOSE 8080

CMD ["java", "-jar", "weatherservice.jar"]