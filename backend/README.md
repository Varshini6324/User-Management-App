# User Management System - Java Spring Boot Backend

This directory contains the complete self-contained Java Spring Boot backend. It replaces the original Node.js backend.

## Requirements
- **Java SE Development Kit (JDK) 8** or higher
- **MongoDB** running locally or remotely (configured in `src/main/resources/application.properties`)

## Folder Structure
- `src/` - The Java source code (Controllers, Repositories, Models, Exceptions)
- `pom.xml` - Maven project configuration and dependencies
- `mvnw` / `mvnw.cmd` - Maven Wrapper scripts (no Maven installation required)
- `app.jar` - Pre-compiled, runnable Spring Boot application package

## How to Run

### Option 1: Run the pre-compiled JAR (Recommended for deployment)
To run the server immediately:
```bash
java -jar app.jar
```

### Option 2: Run with Maven Wrapper (Development mode)
To start the application and automatically handle recompilation:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

### Option 3: Clean and Rebuild the JAR
If you make changes to the Java source files and want to package a new `app.jar`:
```bash
# Windows
.\mvnw.cmd clean package -DskipTests
copy target\usermanagement-0.0.1-SNAPSHOT.jar app.jar

# Linux / macOS
./mvnw clean package -DskipTests
cp target/usermanagement-0.0.1-SNAPSHOT.jar app.jar
```

## API Endpoints
- `POST /user-api/users` - Create a user
- `GET /user-api/users` - Retrieve active users list
- `GET /user-api/users/{id}` - Retrieve a specific active user by ID
- `DELETE /user-api/users/{id}` - Soft-delete a user (sets status to false)
- `PATCH /user-api/users/{id}` - Reactivate a user (sets status to true)

## Deploying to Cloud Platforms (Render, Railway, Heroku, etc.)

Since you pushed the backend code to GitHub under `backend/`, you can deploy it to any standard Java supporting cloud environment:

1. **Root Directory:** Set the Root Directory of your deployment service to `backend` (if you are deploying just the backend service).
2. **Build Command:** Configure the service's build command to:
   ```bash
   ./mvnw clean package -DskipTests
   ```
3. **Start Command:** Configure the service's start command to:
   ```bash
   java -jar target/usermanagement-0.0.1-SNAPSHOT.jar
   ```
4. **Environment Variables:** Set any necessary environment variables, such as a remote MongoDB connection URI string if you aren't using localhost:
   - To override properties dynamically, you can set standard Spring Boot env variables (e.g. `SPRING_DATA_MONGODB_URI` for the database connection string, or `SERVER_PORT` for a dynamic port assigned by platforms like Render/Heroku).

