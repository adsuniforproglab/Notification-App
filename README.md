# Notification App

A Spring Boot microservice for sending SMS notifications via Amazon SNS based on RabbitMQ message queue events.

## Overview

The Notification App is a Java Spring Boot application that consumes proposal-related events from RabbitMQ queues and sends SMS notifications to users via AWS SNS. It is designed to handle two primary notification types:
- Pending proposal notifications
- Completed proposal notifications

## Features

- Message consumption from RabbitMQ queues
- SMS notification delivery via Amazon SNS
- Error handling and fallback mechanisms
- Customizable notification messages
- Comprehensive logging
- Containerized deployment
- Health monitoring via Spring Actuator

## Technology Stack

- Java 21
- Spring Boot 3.4.5
- Spring AMQP (RabbitMQ)
- AWS SDK for SNS
- Docker & Docker Compose
- Lombok
- JUnit 5 & Mockito for testing

## Prerequisites

- Java 21 or higher
- Maven
- RabbitMQ server (or Docker)
- AWS account with SNS permissions
- Docker & Docker Compose (for containerized deployment)

## Configuration

The application can be configured using environment variables or a `.env` file. Key configuration parameters include:

### RabbitMQ Configuration
```properties
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
```

### AWS Configuration
```properties
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key
AWS_REGION=us-east-1
```

### Notification Configuration
```properties
DEFAULT_PHONE=your_default_fallback_phone_number
```

## Getting Started

### Running Locally

1. Clone the repository
2. Create a `.env` file with your configuration (see template in Configuration section)
3. Run the application using Maven:
```bash
./mvnw spring-boot:run
```

### Using Docker

1. Build the Docker image:
```bash
docker build -t notification-app .
```

2. Run using Docker Compose:
```bash
docker-compose up -d
```

## Message Formats

The application expects proposal messages with the following structure:

```json
{
  "id": 123,
  "proposalValue": 5000.0,
  "approved": true,
  "integrated": true,
  "observation": "Optional custom message",
  "user": {
    "id": 456,
    "name": "John",
    "lastName": "Doe",
    "tellPhone": "+1234567890"
  }
}
```

## Testing

Run the tests using Maven:

```bash
./mvnw test
```

Or use the VS Code test task:

```bash
# Run all tests
mvn -B test
```

## Health Checks

The application exposes health endpoints through Spring Actuator:

- Health: `/actuator/health`
- Information: `/actuator/info`
- Metrics: `/actuator/metrics`

## Monitoring

Spring Actuator provides detailed metrics for monitoring. The Docker setup includes health checks to ensure the application is running correctly.


## Contributors

- Leonardo Meireles

---