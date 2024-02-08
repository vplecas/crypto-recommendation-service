# Crypto Recommendation API

## About

Crypto Recommendation API is spring boot application which helps developers who want to invest their salaries on cryptos. 
For data loading Spring Batch is used because of chunk-based processing, parallel processing and much more.
For data persistence MySQL is used.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 17
- Spring Boot
- Docker (Also with Kubernetes enabled)

### Build Docker Image of Crypto API

 - Build jar file: 
```
mvn clean package
```

- Create Docker Image:
```
docker build -f Dockerfile -t crypto-recommendation-api:1.1.1 .
```

### Running with Docker Compose

- To create container for MySQL and Crypto API navigate to /docker folder and paste:
```
docker-compose up
```

### Running on Kubernetes

- Run:
```
kubectl apply -f k8s
```
- After application is up, paste next command:
```
kubectl port-forward svc/crypto-service 8080:8080
```

### API documentation
- To open swagger api documentation, got to web browser and paste url:
```
http://localhost:8080/swagger-ui/#/
```

After running the application, import postman collection and test endpoints.

### Future steps
- Add integration tests.
- Increase test coverage. Currently, test coverage only about is 20%.
- Use secrets for Kubernetes and Docker Compose.
- Manage logging and introduce Elastic Stack.
- Add security.
