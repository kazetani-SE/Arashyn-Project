# Project Structure

This project follows a feature-based MVC architecture. The goal is to keep related code together, making the project easier to navigate and maintain as it grows.

```text
src/main/java/com/arashi/edu
│
├── config/
├── common/
├── domain/
├── infrastructure/
└── ArashynApplication.java
```

---

# config/

Contains application-wide configuration.

Examples:

* `SecurityConfig.java`

  * Configure authentication and authorization.
  * Register security filters.
  * Define public and protected endpoints.

* `OpenApiConfig.java`

  * Configure Swagger/OpenAPI documentation.

* `JpaConfig.java`

  * Configure JPA, auditing, custom converters, etc.

Rule:

> Only place application configuration classes here.

---

# common/

Contains reusable components shared across multiple features.

## common/exception/

Centralized exception handling.

Examples:

* `GlobalExceptionHandler`

  * Converts exceptions into HTTP responses.

* `NotFoundException`

* `UnauthorizedException`

* `BadRequestException`

Rule:

> Custom exceptions should live here.

---

## common/response/

Shared response models.

Examples:

* `ApiResponse<T>`
* `ErrorResponse`

Rule:

> Use for consistent API response structures.

---

## common/util/

Utility classes.

Examples:

* `JwtUtil`
* `DateTimeUtil`
* `StringUtil`

Rule:

> Only stateless helper classes belong here.

Avoid putting business logic in util classes.

---

# domain/

Contains business features.

Each feature owns its own controller, service, repository, DTOs, and related classes.

Rule:

> Organize code by feature, not by technical layer.

---

## domain/user/

Responsible for user management.

Examples:

* User CRUD
* User profile
* User information

Files:

```text
user/
├── User.java
├── UserRepository.java
├── UserService.java
├── UserController.java
├── dto/
└── mapper/
```

---

### User.java

JPA entity representing a user.

---

### UserRepository.java

Database access layer.

Example:

```java
Optional<User> findByEmail(String email);
```

---

### UserService.java

Business logic.

Examples:

* Create user
* Update user
* Validate user data

---

### UserController.java

HTTP endpoints.

Examples:

```http
GET /users
GET /users/{id}
POST /users
```

---

### dto/

Request and response models.

Examples:

```text
CreateUserRequest
UpdateUserRequest
UserResponse
```

Rule:

> Never expose entities directly to clients.

---

### mapper/

Responsible for converting between:

```text
Entity ↔ DTO
```

Examples:

```java
User -> UserResponse
CreateUserRequest -> User
```

---

## domain/auth/

Responsible for authentication and authorization.

Examples:

* Login
* Register
* JWT generation
* Refresh token

Files:

```text
auth/
├── AuthController.java
├── AuthService.java
├── JwtService.java
└── dto/
```

---

### AuthController

Authentication endpoints.

Examples:

```http
POST /auth/login
POST /auth/register
POST /auth/refresh
```

---

### AuthService

Authentication business logic.

Examples:

* Verify credentials
* Register users
* Generate tokens

---

### JwtService

JWT-related operations.

Examples:

* Generate token
* Validate token
* Extract claims

---

### dto/

Authentication requests and responses.

Examples:

```text
LoginRequest
RegisterRequest
LoginResponse
```

---

## domain/publication/

Responsible for publication management.

Examples:

* Create publication
* Search publications
* Publication details

---

## domain/topic/

Responsible for topic management.

Examples:

* Topic CRUD
* Trend analysis
* Topic statistics

---

## domain/university/

Responsible for university management.

Examples:

* University profiles
* University rankings
* University statistics

---

## domain/ranking/

Responsible for ranking data.

Examples:

* Ranking criteria
* Ranking records
* Ranking reports

---

# infrastructure/

Contains technical implementations and external integrations.

Rule:

> Infrastructure supports the business layer but should not contain business rules.

---

## infrastructure/security/

Security implementation details.

Examples:

* JWT filter
* Authentication provider
* UserDetailsService

Files:

```text
JwtAuthenticationFilter
CustomUserDetailsService
JwtAuthenticationEntryPoint
```

Rule:

> Security mechanics belong here, not inside feature modules.

---

## infrastructure/persistence/

Database-specific implementations.

Examples:

* Custom repositories
* Database adapters
* Query implementations

Use this folder only when repository logic becomes complex.

For simple projects, standard Spring Data repositories are usually enough.

---

# ArashynApplication.java

Application entry point.

Responsible for:

* Bootstrapping Spring Boot
* Loading configurations
* Starting the application

Example:

```java
@SpringBootApplication
public class ArashynApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArashynApplication.class, args);
    }
}
```

---

# Architecture Rules

1. Controller handles HTTP requests.
2. Service contains business logic.
3. Repository handles database access.
4. DTOs are used for request/response models.
5. Entities are never returned directly to clients.
6. Shared utilities belong in `common`.
7. Technical implementations belong in `infrastructure`.
8. Business features belong in `domain`.
9. Organize by feature, not by technical layer.
10. Keep business logic out of controllers.

```
```
