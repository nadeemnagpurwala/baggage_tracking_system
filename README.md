# Baggage Tracking System (Java CLI Application)

A simple command-line baggage tracking system built with **Java**, **PostgreSQL**, and **Maven**.  
Supports **Admin** and **User** roles for tracking, updating, and managing baggage information.

---

## Prerequisites

Ensure the following are installed on your system:

- Java (JDK 11 or above recommended)
- Maven
- PostgreSQL

---

## Getting Started

### 1. Database Setup (PostgreSQL)

Use **pgAdmin** or your preferred SQL client to execute the following:

```sql
-- Create the roles table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

-- Insert default roles
INSERT INTO roles (role_name) VALUES ('admin'), ('user');

-- Create the users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100),
    role_id INTEGER REFERENCES roles(id)
);

-- Create the baggage table
CREATE TABLE baggage (
    id SERIAL PRIMARY KEY,
    location VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    user_id INTEGER REFERENCES users(id)
);
```
### 2. Configure Database Connection

Update the DB configuration in your Config.java file

- Example:
    - String url = "jdbc:postgresql://localhost:5432/your_database_name";
    - String user = "your_username";
    - String password = "your_password";

### 3. Build and Run the Application

Navigate to the project root and run :
1. mvn clean install
2. mvn exec:java

You’ll see the main menu:
1. Register
2. Login
3. Exit

### 4. User Operations

After logging in as a user, the following options are available:
1. Check In Baggage
2. Get Baggage Information
3. Logout

### 5. Admin Operations

After logging in as an admin, the following options are available:
1. View All Checked In Baggage
2. Get Baggage Status
3. Update Baggage Status and Location
4. Delete Claimed Baggage Record
5. Get Baggage Status Summary
6. Logout

### 6. Security Notes
- Passwords are hashed securely using BCrypt.
- Ensure PostgreSQL is running and accessible before launching the app.

### 7. License
This project is open for educational and personal use.
