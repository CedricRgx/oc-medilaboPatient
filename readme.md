# Medilabo Microservices Architecture
This project consists of multiple microservices designed using Spring Boot, Docker, and Maven. The architecture includes the following services:

* **MS-Client-UI:** Frontend service for user interaction (port 8080)
* **MS-Patient:** Manages patient-related data (port 8081)
* **MS-Note:** Handles patient medical notes (port 8083)
* **MS-Diabete:** Evaluates diabetes risk levels (port 8084)
* **MS-Gateway-Server:** Acts as a gateway to route requests to appropriate services (port 8082)
* **MS-Eureka-Server:** Service registry for managing microservices (port 8761)
* **mysql-db:** MySQL database service (port 3306)
* **mongo-db:** MongoDB database service (port 27017)

## Prerequisites
Ensure you have the following installed:
* Java 17
* Maven 3.9.9
* Docker & Docker Compose
* Git

## Project Structure
* `ms-client-ui:` Contains the frontend application.
* `ms-patient:` Manages patient data.
* `ms-note:` Manages patient medical notes.
* `ms-diabete:` Evaluates diabetes risks.
* `ms-gateway-server:` API gateway to route requests.
* `ms-eureka-server:` Service registry using Eureka.
* `docker-compose.yml:` Docker Compose file to orchestrate the services.
* `ms-patient/src/main/resources/schemaMedilaboPatient.sql:` SQL script to initialize the MySQL database.
* `ms-note/src/main/resources/medicalNotes.csv:` CSV file to initialize the MongoDB database.

## Compiling the Application
1. Clone the repository:
`git clone <repository_url>`
2. Navigate to the project directory:
`cd medilabo`
3. Compile the microservices with Maven:
`mvn clean install`

This command will compile the code, run the tests, and package the applications into executable JAR files located in the target directories of each service.

## Building Docker Images
The docker-compose.yml file is configured to build Docker images for each microservice using the provided Dockerfiles. Ensure that each microservice directory contains a Dockerfile.

Alternatively, you can build the Docker images manually using the following commands:

* `docker build -t ms-eureka-server:latest ./ms-eureka-server`
* `docker build -t ms-patient:latest ./ms-patient`
* `docker build -t ms-note:latest ./ms-note`
* `docker build -t ms-diabete:latest ./ms-diabete`
* `docker build -t ms-gateway-server:latest ./ms-gateway-server`
* `docker build -t ms-client-ui:latest ./ms-client-ui`

## Starting the Application
You can start the entire application using Docker Compose. This will build (if necessary) and run all services in the correct order as specified in the docker-compose.yml file.

### To start the application:

`docker-compose up`

This command will:
* Build the Docker images for each service if they are not already built.
* Start the containers for each service.
* Set up the medilabo-network network for inter-service communication.
* Mount the necessary volumes and set environment variables.

**Note:** The first time you run this command, it may take some time to download all the required Docker images and build the services.

## Accessing the Services
* **MS-Client-UI:** http://localhost:8080
* **MS-Patient:** http://localhost:8081
* **MS-Gateway-Server:** http://localhost:8082
* **MS-Note:** http://localhost:8083
* **MS-Diabete:** http://localhost:8084
* **MS-Eureka-Server:** http://localhost:8761

## Stopping the Application
To stop the application and remove the containers, run:

`docker-compose down`

This command will stop and remove all containers, networks, and volumes created by docker-compose up.

## Important Notes
* **Service Startup Order:** The `depends_on` directive in the `docker-compose.yml` file specifies the order in which services are started. However, it does not wait for a service to be "ready" before starting the next one.
* **Database Initialization:**
  * **MySQL (mysql-db):** The MySQL database is initialized using the SQL script located at ./ms-patient/src/main/resources/schemaMedilaboPatient.sql. This script is mounted into the container and executed at startup to set up the database schema.
  * **MongoDB (mongo-db):** The MongoDB database is intended to be initialized using the CSV file located at ./ms-note/src/main/resources/medicalNotes.csv. However, additional configuration is required to import this CSV file into MongoDB at startup. You may need to create an initialization script or adjust the configuration to ensure the data is imported correctly.
* **Health Checks:** Health checks (healthcheck) are defined for each service to monitor their status. This can help orchestrate the startup sequence and monitor the application's health.
* **Network Configuration:** All services are connected via the Docker network medilabo-network, allowing them to communicate with each other using their service names.