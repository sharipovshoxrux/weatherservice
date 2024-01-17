# Weather Service

This is a Spring Boot project for managing weather data.

## Getting Started

### Prerequisites

- JDK 11
- Docker (optional)

### Running the Application

1. Build the project: `mvn clean install`
2. Run the application: `java -jar target/weatherservice.jar`

### Docker

You can also run the application using Docker:

```bash
docker build -t weatherservice .
docker run -p 8080:8080 weatherservice
