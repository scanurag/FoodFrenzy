
# FoodFrenzy - Spring Boot with MySQL (Dockerized)

This project is a Spring Boot application named **FoodFrenzy**, backed by a MySQL database. The project has been dockerized for easier setup and deployment. The following guide will help you run the application using Docker.

## Prerequisites

Make sure you have the following installed on your system:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Maven](https://maven.apache.org/install.html)

## How to Run

### Step 1: Clone the Repository

If you haven't already, clone the project repository to your local machine:

```bash
git clone <repository-url>
cd <repository-folder>
```

### Step 2: Build the Project

Ensure that the project is built, and the JAR file is available in the `target` directory. Use Maven to package the project:

```bash
mvn clean package
```

### Step 3: Build Docker Image (Optional)

If you want to build the Docker image manually, you can run the following command:

```bash
docker build -t foodfrenzy-app .
```

### Step 4: Running with Docker Compose

To run the project using Docker Compose, follow these steps:

1. Open a terminal and navigate to the root folder of the project.
2. Use the following command to start the application:

```bash
docker-compose up --build
```

### Step 5: Access the Application

Once the containers are running, you can access the application at:

```
http://localhost:8080
```

The MySQL database will be available on port `3306`.

### Step 6: Stopping the Application

To stop the running containers, press `CTRL + C` in the terminal where Docker Compose is running, or use the following command in another terminal:

```bash
docker-compose down
```

This will stop and remove all running containers for this project.

---

Enjoy working with **FoodFrenzy**!
