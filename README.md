# Project Manager App

Backend application for managing **projects, tasks and users**, built with Java and Spring Boot.

The project started as a university exercise and has been extended with a service layer, REST API documentation, unit and integration tests, Docker, Spring Security and Apache Kafka.

## Tech stack

- Java 17
- Spring Boot
- Spring Web / REST
- Spring Data JPA
- Hibernate
- Spring Security
- Spring Kafka
- PostgreSQL
- Maven
- Swagger / OpenAPI
- JUnit 5
- Mockito
- MockMvc
- Testcontainers
- JaCoCo
- Docker
- Docker Compose
- Git / GitHub

## Architecture

The application follows a layered architecture:

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
PostgreSQL
```

- **Controller** handles HTTP requests and REST endpoints.
- **Service** contains application/business logic.
- **Repository** communicates with the database through Spring Data JPA.
- **Entity** represents data stored in the database.

Kafka adds an event-driven flow for tasks:

```text
TaskService
  ↓
TaskEventProducer
  ↓
Kafka topic: task-events
  ↓
TaskEventConsumer
```

## Domain model

### Project

Represents a project containing tasks and users.

```text
Project 1 -------- * Task
```

### Task

Represents a task assigned to a project. It contains fields such as:

- title
- description
- task type / priority
- project

Priority is stored as an enum:

```text
LOW_PRIORITY
MEDIUM_PRIORITY
HIGH_PRIORITY
```

### User

Represents an application user.

Projects and users have a many-to-many relationship:

```text
Project * -------- * User
```

The relationship is stored in the `project_users` table.

## JPA / Hibernate

The project uses common JPA mappings:

```java
@OneToMany
@ManyToOne
@ManyToMany
@JoinColumn
@JoinTable
@Enumerated(EnumType.STRING)
```

Example:

```text
tasks.project_id -> projects.id
```

The foreign key keeps the relationship between tasks and projects consistent.

## REST API

### Projects

```text
GET    /api/projects
GET    /api/projects/{id}
POST   /api/projects
PUT    /api/projects/{id}
DELETE /api/projects/{id}
```

### Users

```text
GET    /api/users
GET    /api/users/{id}
POST   /api/users
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Tasks

```text
GET    /api/tasks
GET    /api/tasks/{id}
POST   /api/tasks
PUT    /api/tasks/{id}
DELETE /api/tasks/{id}
POST   /api/tasks/project/{projectId}
```

The last endpoint creates a task assigned to an existing project.

## Swagger / OpenAPI

The REST API is documented with Springdoc OpenAPI.

Local application:

```text
http://localhost:8080/swagger-ui.html
```

Docker Compose setup:

```text
http://localhost:8081/swagger-ui.html
```

Swagger can be used to inspect and test the API directly from the browser.

## Spring Security

The application contains a basic Spring Security configuration using `SecurityFilterChain`.

Implemented concepts:

- authentication
- authorization
- form login
- roles
- BCrypt password hashing
- role-protected endpoints

Development users are currently stored in memory:

```text
user  -> ROLE_USER
admin -> ROLE_ADMIN
```

Example authorization rule:

```text
DELETE /api/projects/** -> ADMIN only
```

A logged-in normal user receives `403 Forbidden` when trying to perform an admin-only operation.

> The in-memory users and simple form login are intended for development and learning purposes.

## Apache Kafka

The project contains a simple event-driven flow using Kafka.

When a task is created:

```text
TaskService
  ↓
TaskEventProducer
  ↓
topic: task-events
  ↓
TaskEventConsumer
```

Example event:

```text
TASK_CREATED id = 7 title = Example task
```

The consumer listens to:

```text
topic: task-events
group: task-group
```

This demonstrates:

- producer
- consumer
- broker
- topic
- consumer group
- asynchronous event communication

Local Kafka configuration:

```properties
spring.kafka.bootstrap-servers=localhost:9092
```

## Unit tests

The service layer is tested using JUnit 5 and Mockito.

Tested services:

```text
UserService
ProjectService
TaskService
```

The tests include:

- repository mocks
- `when(...).thenReturn(...)`
- `verify(...)`
- assertions
- CRUD service logic
- `Optional`
- exception testing with `assertThrows`
- logic using multiple repositories

Typical unit-test flow:

```text
Service under test
      ↓
Mock Repository
```

No real database is required.

## Integration tests

The project also contains integration tests using:

- `@SpringBootTest`
- MockMvc
- Testcontainers
- PostgreSQL container

Integration tests verify the full flow:

```text
MockMvc
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
PostgreSQL Testcontainer
```

Covered CRUD scenarios include:

- GET all projects
- GET project by ID
- CREATE project
- UPDATE project
- DELETE project

Testcontainers starts a temporary PostgreSQL database, so integration tests do not modify the normal local development database.

## Test coverage

JaCoCo is configured for code coverage.

Run tests:

```bash
mvn clean test
```

Coverage report:

```text
target/site/jacoco/index.html
```

Coverage helps identify untested areas, but a high percentage alone does not guarantee high-quality tests.

## PostgreSQL

The application uses PostgreSQL through Spring Data JPA and Hibernate.

Example local configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_managerr
spring.datasource.username=myuser
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Do not commit database passwords or secrets to the repository.

## Docker

Build the Spring Boot JAR:

```bash
mvn clean package
```

Build the application image:

```bash
docker build -t project-manager-app .
```

Simplified flow:

```text
Maven
  ↓
Spring Boot JAR
  ↓
Docker image
  ↓
Docker container
```

## Docker Compose

`docker-compose.yml` starts:

- Spring Boot application
- PostgreSQL

Architecture:

```text
Host
  |
  | localhost:8081
  ↓
Spring Boot container
  |
  | db:5432
  ↓
PostgreSQL container
```

Inside Docker Compose the application connects to PostgreSQL using the service name `db`, not `localhost`.

Start:

```bash
docker compose up
```

Start in background:

```bash
docker compose up -d
```

Stop:

```bash
docker compose down
```

## Environment variables

Sensitive values are stored outside the repository.

Create a local `.env` file:

```env
DB_PASSWORD=your_password
```

The `.env` file should be ignored by Git.

Example Docker Compose usage:

```yaml
POSTGRES_PASSWORD: ${DB_PASSWORD}
```

## Running locally

### Requirements

- Java 17
- Maven
- PostgreSQL
- Docker Desktop
- Git

### Clone

```bash
git clone https://github.com/Matthias3721/project-manager.git
```

Navigate to the directory containing `pom.xml`.

### Start Kafka

Example using Docker:

```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:4.3.1
```

### Run tests

```bash
mvn clean test
```

### Start application

```bash
mvn spring-boot:run
```

Application:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## Running with Docker Compose

Build the application first:

```bash
mvn clean package
docker build -t project-manager-app .
```

Create the local `.env` file and run:

```bash
docker compose up
```

Application:

```text
http://localhost:8081
```

## Project structure

```text
src
├── main
│   ├── java
│   │   └── org.example.projectmanagerapp
│   │       ├── controller
│   │       ├── entity
│   │       ├── kafka
│   │       ├── priority
│   │       ├── repository
│   │       ├── security
│   │       └── service
│   └── resources
│       └── application.properties
└── test
    └── java
        └── org.example.projectmanagerapp
            ├── ProjectIntegrationTest
            └── service
                ├── UserServiceTest
                ├── ProjectServiceTest
                └── TaskServiceTest
```

## What this project demonstrates

- layered Spring Boot architecture
- REST API design
- dependency injection
- Spring Data repositories
- Hibernate / JPA relationships
- PostgreSQL and SQL
- CRUD operations
- API documentation
- unit testing with Mockito
- integration testing with MockMvc
- temporary test databases with Testcontainers
- code coverage with JaCoCo
- Docker and Docker Compose
- authentication and authorization
- role-based endpoint security
- BCrypt password hashing
- Kafka producer / consumer communication
- Git-based version control

## Possible next steps

- store security users in PostgreSQL
- implement JWT authentication
- introduce DTOs
- add Bean Validation
- add global exception handling with `@ControllerAdvice`
- replace string Kafka messages with structured JSON events
- add Kafka integration tests
- improve DELETE handling for foreign-key relationships
- add CI/CD
- add monitoring and metrics
- deploy to a cloud environment

## Author

**Mateusz**

Project developed as a practical Java / Spring Boot learning project and extended with additional backend technologies.
