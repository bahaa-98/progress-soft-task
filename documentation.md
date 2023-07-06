# Project Documentation

## Table of Contents
- [Installation](#installation)
- [Entity](#entity)
- [Controller](#controller)
- [Mapper](#mapper)
- [DTOs](#dtos)
- [Repository](#repository)
- [Service](#service)
- [Implementation](#implementation)
- [Exception Handling](#exception-handling)

## Installation
To set up the Data Warehouse project locally, follow these steps:
1. Clone the repository:
2. Navigate to the project directory
3. Install the required dependencies using a package manager such as Maven: mvn install
4. Configure the database connection in the `application.properties` file: url=jdbc:postgresql://localhost:5554/datawarehouse, username=root, password=root1234 
5. Run the Docker image: docker compose up
6. Build the project: mvn clean
7. Run the application: java -jar target/api-0.0.1-SNAPSHOT.jar
8. The application should now be running locally at `http://localhost:8080`.




## Entity
The `DealsEntity` represents a deal in the system. It contains the necessary fields and annotations to define the entity's structure and mapping to the database.

## Controller
The `DealsController` handles the RESTful API endpoints related to deals. It provides methods to create, retrieve, update, and delete deals. Each endpoint is responsible for validating input, calling the appropriate service methods, and returning the appropriate HTTP responses.

## Mapper
The `DealsMapper` is responsible for mapping data between the `DealsEntity` and DTOs. It provides methods to convert data from the entity to DTOs and vice versa, ensuring a clear separation between the database layer and the API layer.

## DTOs
The project includes the following DTOs:
- `DealRequestDto`: Represents the data required to create or update a deal.
- `DealResponseDto`: Represents the response data returned when retrieving a deal.

## Repository
The `DealsRepository` is responsible for the database interactions related to deals. It provides methods to perform CRUD operations and other queries on the `DealsEntity`. The repository communicates directly with the database using an ORM or other data access mechanism.

## Service
The `DealsService` defines the business logic for managing deals. It acts as an intermediary between the controller and repository. It performs validations, applies business rules, and coordinates data persistence and retrieval.

## Implementation
The `DealsServiceImpl` implements the `DealsService` interface. It provides the actual implementation of the business logic for managing deals. It utilizes the repository and mapper to perform the necessary operations.

## Exception Handling
The Deals project implements exception handling to ensure proper error handling and user-friendly error responses. 