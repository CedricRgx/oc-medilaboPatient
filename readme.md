# Microservices Architecture

This project consists of multiple microservices designed using Spring Boot, Docker, and Maven. The architecture includes the following services:

- **MS-Client-UI**: Frontend service for user interaction.
- **MS-Patient**: Manages patient-related data.
- **MS-Note**: Handles notes related to patients.
- **MS-Diabete**: Evaluates diabetes risk levels.
- **MS-Gateway-server**: Acts as a gateway for routing requests to appropriate services.
- **MS-Eureka-server**: Service registry for managing microservices.

This guide will help you build, test, and deploy the microservices.

## Prerequisites

Ensure you have the following installed:

- Java 17+
- Maven 3.9.9
- Docker & Docker Compose
- Git