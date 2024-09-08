# Spring Boot Microservices with Kafka, gRPC, and Swagger

## Table of Contents
- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Create the images of the Application using Docker](#Create-the-images-of-the-Application-using-Docker)
- [The images of the project on my docker hub](#The-images-of-the-project-on-my-docker-hub)

## Overview
This project demonstrates a **Spring Boot** microservices architecture using **Apache Kafka** for messaging, **gRPC** for efficient inter-service communication, and **Swagger** for API documentation. It includes the following key features:
- **Microservices** architecture for modular development
- **Kafka** as the message broker for asynchronous communication
- **gRPC** for high-performance RPC communication between services
- **Swagger** for API documentation and testing

## Technologies Used
- **Spring Boot** - Framework for building microservices
- **Kafka** - Distributed messaging system
- **gRPC** - High-performance RPC framework
- **Swagger** - API documentation and UI
- **PostgreSQL** - Database for persistence
- **Docker** - Containerization for running services

## Prerequisites
- **Java 17** 
- **Apache Kafka** installed and running
- **PostgreSQL** installed and running
- **Maven** for building the project
- **Docker** for running containers

## Installation
### 1. Clone the repository:
```bash
git clone https://github.com/your-username/your-repo.git
cd spring-boot-microservices
```

### 2. Install Ubuntu WSL2:
Follow the [Ubuntu WSL2 Guide](https://github.com/ubuntu/WSL/blob/main/docs/guides/install-ubuntu-wsl2.md) for setting up Ubuntu on Windows Subsystem for Linux 2.

### 3. Run Kafka on Windows:
Follow these steps to get Kafka running on Windows:

1. **Download and Extract Kafka**:
   - Download Kafka from the official site: [Kafka Downloads](https://kafka.apache.org/quickstart)
   - Extract the files to a folder, e.g., `C:\kafka`.

2. **Start Zookeeper**:
   Kafka relies on Zookeeper. Open a command prompt, navigate to the Kafka directory, and run:
   ```bash
   cd C:\kafka
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   ```
3. **Start Kafka Server**:
   ```bash
   bin/kafka-server-start.sh config/server.properties
   ```
4. **Set Up Port Forwarding**:
   Kafka on Windows needs port forwarding to communicate with WSL2. Run the following command in an elevated command prompt (Run as Administrator):
   ```bash
   netsh interface portproxy add v4tov4 listenport=9092 listenaddress=0.0.0.0 connectport=9092 connectaddress=<IP OF YOUR WSL2>
   ```

5. **Find Your WSL2 IP Address**:
   To get your WSL2 IP address, run:
   ```bash
   wsl hostname -I
   ```

## Running the Application
- Make sure **Kafka** and **PostgreSQL** are running.
- We need at each pom.xml file add it as Maven project
- Build the project with Maven:
  ```bash
  mvn clean install
  ```
- Run each model of the Spring Boot applications alone as separate applications

## Create the images of the Application using Docker
- Using this commad you will pull and create the images for the application:
  ```bash
   docker compose -f .\docker-compose.yaml up -d
  ```
## The images of the project on my docker hub
- [Project Image](https://hub.docker.com/r/jehad950/post_user_rest_api)
---

