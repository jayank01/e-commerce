# LocalGoods Project

## Overview

LocalGoods is a web application designed to facilitate the buying and selling of local goods. The application includes features such as user registration, password management, and email notifications.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Endpoints](#endpoints)

## Technologies Used

- **Java**: Programming language
- **Spring Boot**: Framework for building the application
- **Maven**: Dependency management
- **JUnit**: Testing framework
- **Mockito**: Mocking framework for unit tests
- **MySQL**: Database
- **SMTP**: Email service for sending notifications

## Project Structure

```
src/
├── main/
│   ├── java/com/epam/everest/LocalGoods/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   └── Handler/
│   └── resources/
│       ├── application.yaml
│       └── certs/
└── test/
    ├── java/com/epam/everest/LocalGoods/
    │   ├── controller/
    │   └── service/
```

## Setup and Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/jayank-vashistha_epam/LocalGoods.git
    cd LocalGoods
    ```

2. **Configure the database**:
    - Update the `application.yaml` file with your MySQL database credentials.

3. **Install dependencies**:
    ```sh
    mvn clean install
    ```

## Running the Application

1. **Start the application**:
    ```sh
    mvn spring-boot:run
    ```

2. **Access the application**:
    - The application will be available at `http://localhost:9090`.

## Testing

1. **Run unit tests**:
    ```sh
    mvn test
    ```

## Endpoints

### User Controller

- **POST /users/register**: Register a new user
- **POST /users/login**: Login a user

### Forget Password Controller

- **POST /forget-password/verify-mail**: Verify email and send OTP
- **POST /forget-password/verify-otp**: Verify OTP
- **POST /forget-password/change-password**: Change password
