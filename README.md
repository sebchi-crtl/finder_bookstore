# Online Bookstore API
* This project is a simple online bookstore API built with Java and the Spring Boot framework. It provides endpoints for managing books and integrates with a relational database using JPA. The API documentation is available through Swagger.

# Setup Instructions
~ Clone the Repository:

Copy code
* git clone https://github.com/your-username/online-bookstore.git
* cd online-bookstore
  
# Database Configuration:

Open src/main/resources/application.properties.
Configure the database connection properties according to your setup.
Build and Run:

bash
Copy code
./mvnw clean install
./mvnw spring-boot:run
The application will be accessible at http://localhost:8080.

# Swagger Documentation:

* Swagger documentation ui is available at http://localhost:8050/swagger-ui/index.html
* Swagger api documentation is available at http://localhost:8050/api-docs
Design Explanation
The application follows a three-layer architecture:

# Docker:

to run postgres docker-compose up -d

# Controller Layer: Handles incoming HTTP requests and delegates business logic to the service layer.
# Service Layer: Implements business logic and communicates with the repository.
# Repository Layer: Manages the interaction with the database using JPA.
# API Documentation
The API provides the following endpoints:

* GET /api/books: Get a list of available books.
* GET /api/books/{id}: Get details of a specific book.
* POST /api/books: Add a new book to the store.
* PUT /api/books/{id}: Update details of a book.
* DELETE /api/books/{id}: Delete a book from the store.
For detailed documentation and testing, refer to Swagger Documentation.

Database Setup
The application uses a relational database. Ensure your database is running and configure the connection in application.properties.

Error Handling
The API handles errors gracefully using custom exceptions. It returns appropriate HTTP status codes and detailed error messages in the response body.

Authentication (Bonus)
To enhance security, the application supports authentication using Spring Security. Endpoints can be secured based on user roles.

