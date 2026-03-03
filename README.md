# 🛒 E-Commerce Microservices Architecture

A complete backend E-Commerce Ordering System built using Spring Boot, Kafka, gRPC, PostgreSQL, and Docker.

This project demonstrates a real-world Event-Driven Microservices Architecture.

---

# 🏗️ Architecture Overview

This system follows distributed microservices communication using:

- gRPC (synchronous communication)
- Apache Kafka (asynchronous event-driven communication)

### 🔄 Flow of the System

1. Order Service creates an order.
2. Order Service calls Product Service using gRPC to reserve stock.
3. If stock is available:
   - Order is saved in database.
   - `order-created` event is published to Kafka.
4. Payment Service consumes `order-created`.
5. Payment Service processes payment (mock logic).
6. Payment Service publishes:
   - `payment-success` OR
   - `payment-failed`
7. Notification Service consumes payment result and prints confirmation.

---

# 🚀 Tech Stack

- Java 17
- Spring Boot
- Apache Kafka
- gRPC
- PostgreSQL
- Maven (Multi-module)
- Docker & Docker Compose

---

# 📦 Microservices

## 1️⃣ Product Service (Port: 8081)
- Manage products
- Maintain stock
- gRPC server for stock reservation
- PostgreSQL integration

---

## 2️⃣ Order Service (Port: 8082)
- Create new orders
- Calls Product Service using gRPC
- Publishes `order-created` event to Kafka
- Saves order to database

---

## 3️⃣ Payment Service (Port: 8084)
- Consumes `order-created`
- Processes payment (mock logic)
- Publishes:
  - `payment-success`
  - `payment-failed`

---

## 4️⃣ Notification Service
- Consumes payment events
- Sends order confirmation (console log)

---

# 📡 Event Communication

| Event Name        | Published By     | Consumed By        |
|------------------|------------------|--------------------|
| order-created     | Order Service     | Payment Service     |
| payment-success   | Payment Service   | Notification Service |
| payment-failed    | Payment Service   | Notification Service |

---

# 🐳 Running Kafka & Zookeeper

Start Docker:

```bash
docker-compose up -d
```

Stop Docker:

```bash
docker-compose down
```

---

# ▶️ Running Services

Run each service individually:

```bash
mvn spring-boot:run
```

Or run from IntelliJ.

---

# 🧪 Test Order API

Create Order:

```http
POST http://localhost:8082/api/orders?productId=1&quantity=2
```

If stock is available:
- Order will be saved
- Kafka event will be published
- Payment will be processed
- Notification will be triggered

---

# 📁 Project Structure

```
ecommerce-ordering/
│
├── common-proto/
├── product-service/
├── order-service/
├── payment-service/
├── notification-service/
├── docker-compose.yml
└── pom.xml
```

---

# 🧠 Key Concepts Implemented

- Microservices Architecture
- Event-Driven Design
- Apache Kafka Producer & Consumer
- gRPC Inter-service Communication
- Distributed System Flow
- PostgreSQL Integration
- Maven Multi-module Project
- Dockerized Kafka Environment

---

# 📌 Future Improvements

- API Gateway Integration
- Service Discovery (Eureka)
- Centralized Config Server
- JWT Authentication
- Distributed Tracing
- Containerize each service
- Deploy to AWS / Kubernetes

---

# 👨‍💻 Author

Pavan Muramreddy  
Integrated M.Tech – Software Engineering  
Microservices & Cloud Enthusiast  

---

⭐ If you like this project, give it a star!
