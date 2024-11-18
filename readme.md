# Microservices Architecture

This project consists of multiple microservices designed using Spring Boot, Docker, and Maven. The architecture includes the following services:

- **MS-Client-UI**: Frontend service for user interaction (port 8080)
- **MS-Patient**: Manages patient-related data (port 8081)
- **MS-Note**: Handles notes related to patients (port 8083)
- **MS-Diabete**: Evaluates diabetes risk levels (port 8084)
- **MS-Gateway-server**: Acts as a gateway for routing requests to appropriate services (port 8082)
- **MS-Eureka-server**: Service registry for managing microservices (port 8761).

## Prerequisites

Ensure you have the following installed:

- Java 17+
- Maven 3.9.9
- Docker & Docker Compose
- Git

## Start

To start the Medilabo application, you have to start according this order :
1. MS-Eureka-server
2. MS-Patient
3. MS-Note
4. MS-Diabete
5. MS-Gateway-server