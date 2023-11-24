# Shopping List Server

This Spring Boot application manages user created shopping lists.

## Prerequisites

Before running the application, ensure you have the following:

- Java Development Kit (JDK) installed
- Maven installed
- MySQL database installed

## Setup

### 1. Clone the repository
```bash
git clone https://github.com/leonbojic/shopping-list-server.git
cd shopping-list-server
```
### 2. Configure the Database:

- Make sure you have a MySQL database installed and running.
- Open src/main/resources/application.properties and update the database connection properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name 
spring.datasource.username=your_username 
spring.datasource.password=your_password 
```
### 3. Build the JAR using Maven

- Open the terminal in the project directory:
```bash
mvn clean package
```
### 4. Run the Application
```bash
java -jar target/shopping-list-server-0.0.1-SNAPSHOT.jar
```
The endpoints will be available at http://localhost:8080

## Additional Notes

This is meant to be used with shopping-list-client at:
https://github.com/leonbojic/shopping-list-client

