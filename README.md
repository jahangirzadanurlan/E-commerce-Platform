# E-commerce Platform Microservices Project

![E-commerce Banner](https://some-image-link.com)

## Overview

This is an e-commerce platform built using a microservices architecture. The platform enables users to purchase wholesale products and resell them in a personalized marketplace.

## Features :star:

- **External API Integration**: Fetches products from an external API.
- **Spring Security and JWT**: Handles user registration, authentication, and logout.
- **Wholesale Products**: Users can browse wholesale products at `localhost:8080/wholesale-products`.
- **Resale Capabilities**: Users can resell purchased products, appearing at `localhost:8080/market-products`.
- **Automated Sale Simulation**: Scheduled tasks to simulate product sales.

## Built With :hammer:

- **Microservices Architecture**: Designed with a microservices approach.
- **JDK 11**: The project is built using JDK 11.
- **Gradle**: Gradle is used for dependency management.
- **Spring Boot**: Used for the backend framework.
- **PostgreSQL**: Utilized for the database.
- **Liquibase**: Handles database migrations.
- **Eureka Server**: For service discovery.
- **Application Gateway**: Serves as the API gateway.
- **Docker**: The project includes Dockerfile and docker-compose.yml for containerization.

## Getting Started :rocket:

### Prerequisites

- JDK 11
- Gradle
- Docker
- PostgreSQL

### Installation

1. **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/your-repo-name.git
    ```

2. **Navigate to Project Directory**
    ```bash
    cd your-repo-name
    ```

3. **Build the Project**
    ```bash
    gradle build
    ```

4. **Run Docker Compose**
    ```bash
    docker-compose up
    ```

## How To Use :book:

1. Register a new user at `localhost:8080/register`.
2. Browse available wholesale products at `localhost:8080/wholesale-products`.
3. Purchase products to resell on `localhost:8080/market-products`.
4. Automated tasks will simulate sales at predefined intervals.

## Contributing :handshake:

1. Fork the project.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

Give this project a :star: if you found it useful!




<img width="946" alt="Screenshot 2023-08-19 221531" src="https://github.com/jahangirzadanurlan/E-commerce-Platform/assets/103985861/98aa5370-c1ba-4afa-8171-1d4e67c5e464">
