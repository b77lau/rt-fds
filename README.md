# Real-Time Fraud Detection Service

This project implements a real-time fraud detection service for processing transactions. The system is developed using **Spring Boot**, with options for deployment to a **Kubernetes (K8s) cluster** on AWS, GCP, or Alibaba Cloud.

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Technology Stack](#technology-stack)
- [Running Locally](#running-locally)
- [Testing](#testing)
- [Building Docker Image](#building-docker-image)
- [Deploying to Kubernetes](#deploying-to-kubernetes)
    - [K8s Deployment](#kubernetes-deployment)
    - [Helm Charts (Optional)](#helm-charts-optional)
- [Endpoints](#endpoints)
- [Additional Notes](#additional-notes)

---

## Prerequisites

Ensure the following tools and resources are installed and configured:

- **Java 17** or later (e.g., GraalVM, Zulu JDK)
- **Maven** (for building the project)
- **Docker** (to containerize the application)
- **Kubernetes** (with `kubectl` configured)
- Cloud CLI:
    - AWS CLI / GCP CLI / Alibaba CLI (optional, based on deployment platform)
- **Helm** (optional, for managing K8s deployments)
- **Postman** or **curl** (for API testing)

---

## Technology Stack

- **Backend**: Java 17, Spring Boot
- **Message Queues**: AWS SQS / GCP PubSub / Alibaba Cloud Message Service
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **Testing**: JUnit 5, MockMvc, Mockito
- **CI/CD**: Optional (GitHub Actions or Jenkins)

---

## Running Locally

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo/real-time-fraud-detection.git
   cd real-time-fraud-detection
   ```

2. **Build the Project**:
   Use Maven to package the application:
   ```bash
   mvn clean package
   ```

3. **Run the Application**:
   Start the service using Spring Boot:
   ```bash
   java -jar target/real-time-fraud-detection.jar
   ```

4. **Access the Service**:
    - The application will run on **`http://localhost:8080`**.
    - Test API endpoints using Postman or curl (refer to [Endpoints](#endpoints)).

---

## Testing

Run unit and integration tests using Maven:

```bash
mvn test
```

### Test Coverage Report
- Ensure test coverage is generated using JaCoCo.
- Reports can be found in: `target/site/jacoco/index.html`.

---

## Building Docker Image

To containerize the application, follow these steps:

1. **Build Docker Image**:
   ```bash
   docker build -t fraud-detection-service:latest .
   ```

2. **Run the Docker Container**:
   ```bash
   docker run -p 8080:8080 fraud-detection-service:latest
   ```

3. **Verify**:
    - Access the service at **`http://localhost:8080`**.

---

## Deploying to Kubernetes

### 1. Kubernetes Deployment

1. **Ensure Kubernetes Cluster is Ready**:
    - Use `kubectl` to verify access to the cluster.

2. **Apply Deployment Manifests**:
   Apply provided Kubernetes YAML files:

   ```bash
   kubectl apply -f k8s/deployment.yaml
   kubectl apply -f k8s/service.yaml
   ```

3. **Verify Deployment**:
   Check that pods and services are running:

   ```bash
   kubectl get pods
   kubectl get services
   ```

4. **Access the Service**:
    - Use the Kubernetes service URL or LoadBalancer to access the API.

### 2. Helm Charts (Optional)

To deploy using Helm:

1. **Install Helm Chart**:
   ```bash
   helm install fraud-detection ./helm
   ```

2. **Verify Deployment**:
   ```bash
   kubectl get pods
   kubectl get services
   ```

---

## Endpoints

Here are the available endpoints for the fraud detection service:

| Method | Endpoint            | Description                     |
|--------|---------------------|---------------------------------|
| POST   | `/analyze`          | Analyze a transaction for fraud |
| GET    | `/health`           | Health check endpoint           |

### Example Request: `/analyze`
```json
POST /analyze
Content-Type: application/json

{
  "transactionId": "txn123",
  "accountNumber": "ACC987654321",
  "amount": 15000.0,
  "timestamp": "2024-06-17T15:30:00"
}
```

### Example Response
- **200 OK** (Legitimate transaction):
  ```json
  { "status": "Legitimate Transaction" }
  ```

- **400 Bad Request** (Fraudulent transaction):
  ```json
  { "status": "Fraud Detected" }
  ```

---

## Additional Notes

- **Resilience Testing**: Ensure fault tolerance for message queues and external systems.
- **Scaling**: Kubernetes HPA (Horizontal Pod Autoscaler) can be configured.
- **Monitoring**: Integrate tools like Prometheus and Grafana for metrics.

---

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

For issues or inquiries, contact **Bryan Lau** at **your.email@example.com**.
