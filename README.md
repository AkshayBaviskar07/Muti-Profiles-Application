# Multiple-Profile Application
> This project implements a Spring REST API that manages companies, categories, and products. It utilizes multiple profile configurations for development (dev), testing (test), and production (prod) environments, offering health checks through Spring Actuator and containerization with Docker.

## Features:

RESTful API for CRUD operations on companies, categories, and products.
Profile-based configuration for different environments (dev, test, prod).
Spring Actuator endpoints for application health monitoring.
Dockerized for easy deployment and scalability.
<br>

**Prerequisites**:

- Java Development Kit (JDK) 11 or later
- Maven (or Gradle) build tool
- Docker installed
- Getting Started:

**Clone the repository**:

```Bash
git clone https://your-repository-url.git
```
### Install dependencies:

**Using Maven**:

```Bash
mvn clean install
```


- Edit `src/main/resources/application.properties` for default settings.
- Create environment-specific property files (e.g., `dev.properties`, `test.properties`, `prod.properties`) to override defaults for specific environments.
- Activate a profile during startup with `-Dspring.profiles.active=dev` (replace dev with your desired profile).


### Run the application:

**Using Maven (embedded server)**:

```Bash
mvn spring-boot:run
```

**Using a compiled JAR (production)**:

```Bash
java -jar target/your-application-name.jar
```

**API Endpoints**:

### Companies:

- `GET /company`: Get all companies
- `POST /company`: Create a new company
- `GET /company/{compnayId}}`: Get a company by ID
- `PUT /company/{companyId}`: Update a company
- `DELETE /company/{companyId}`: Delete a company

### Categories:

- `GET /company/{companyId}/category`: Get all categories
- `POST /company/{companyId}/category`: Create a new category
- `GET /company/{companyId}/category/{categoryId}`: Get a category by ID
- `PUT /company/{companyId}/category/{categoryId}`: Update a category
- `DELETE /company/{companyId}/category/{categoryId}`: Delete a category

### Products:

- `GET /company/{companyId}/category/{categoryId}/product`: Get all products
- `POST /company/{companyId}/category/{categoryId}/product`: Create a new product
- `GET /company/{companyId}/category/{categoryId}/product/{productId}`: Get a product by ID
- `PUT /company/{companyId}/category/{categoryId}/product/{productId}`: Update a product
- `DELETE /company/{companyId}/category/{categoryId}/product/{productId}`: Delete a product

### Health Checks:

Spring Actuator exposes health endpoints at the following paths:

- `/health`: Overall application health
- `/health/liveness`: Liveness probe
- `/health/readiness`: Readiness probe

### Docker:

**Build the Docker image**:

```Bash
docker build -t your-image-name .
```

**Run the container**:

```Bash
docker run -p 8080:8080 your-image-name
```
(Replace 8080 with your desired port mapping)

### Additional Notes:

- Consider using a database MySQL  for persistence in production.
- Implement unit and integration tests for robust application verification.