FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app
COPY target/weatherservice.jar weatherservice.jar

EXPOSE 8080

CMD ["java", "-jar", "weatherservice.jar"]