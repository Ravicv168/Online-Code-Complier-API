# Online Code Compiler API

This is an Online Code Compiler API built using Spring Boot. It allows users to compile and execute code dynamically in multiple programming languages such as Java and Python through an HTTP-based API. The API provides concurrency management, basic logging, and feedback to users about code execution.

## Features
- Code Execution Support for Java and Python.
- Concurrency Management using ThreadPoolExecutor to handle multiple requests.
- RESTful API with JSON responses for easy integration.
- Basic Logging for debugging and monitoring the system's activity.

## Technologies Used
- Spring Boot (for API development)
- Java (for backend logic and code execution)
- Python (for Python code execution)
- Thread Pool Executor (for managing concurrent tasks)
- Maven (for project management)

## Installation
## Clone the repository
   git clone https://github.com/yourusername/online-code-compiler-api.git

  
## Build the project using Maven
   cd online-code-compiler-api
   mvn clean install

## Run the application use:
   mvn spring-boot:run

## API Endpoints
- To execute you code - POST /api/compiler

## Testing the API
Postman: Use Postman to test the endpoints by sending requests.
