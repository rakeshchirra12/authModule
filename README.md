# Auth Module

## Overview
This project is a Spring Boot application that provides authentication and authorization services. It includes functionalities for user login, token generation, and role-based access control.

## Project Structure
- `src/main/java/com/rakesh/authmodule/AuthModuleApplication.java`: Main class to bootstrap the Auth Module application.
- `src/main/java/com/rakesh/authmodule/security`: Contains security-related classes for authentication and authorization.
- `src/main/resources/application.properties`: Configuration file for the Auth Module.

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Maven

## Configuration
The `application.properties` file contains the configuration for the Auth Module, including security settings and token properties.

### Example Configuration
```ini
spring.application.name=AuthModule
server.port=8081
logging.level.org.springframework=TRACE

# Security configurations
security.jwt.secret=your_secret_key
security.jwt.expiration=3600
## Running the Application
1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project.
4. Run `mvn spring-boot:run` to start the application.
