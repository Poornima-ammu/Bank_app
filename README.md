# Bank App

A console-based banking application built with **Java** and **SQL**.

## Features

- **Register** - Create a new bank account
- **Login** - Authenticate with email and password
- **Deposit** - Add money to your account
- **Withdraw** - Withdraw money from your account (with balance check)
- **Transfer** - Send money between accounts (with transaction rollback support)
- **Transaction History** - View all your transactions with timestamp
- **Check Balance** - View your current account balance
- **Exit** - Logout from the application

## Project Structure

### Core Classes

- **BankApp.java** - Main class with console menu interface
- **Account.java** - Model class representing a bank account with properties:
  - Account Number
  - Name
  - Email
  - Password (hashed)
  - Balance

### Service & DAO Layer

- **BankService.java** - Service layer handling business logic
- **AccountDao.java** - Data Access Object for database operations:
  - Create accounts
  - User login with password verification
  - Deposit/Withdraw operations
  - Money transfer between accounts
  - Transaction history retrieval
  - Balance checking

### Utilities

- **DBConnection.java** - Manages database connections
- **PasswordUtil.java** - Password hashing utility for secure password storage

## Prerequisites

- Java JDK 8 or higher
- SQL Database (MySQL/Oracle/PostgreSQL)
- JDBC Driver for your database

## Database Setup

Create the following tables:

```sql
CREATE TABLE ACCOUNTS (
    ACC_NO INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(100),
    EMAIL VARCHAR(100) UNIQUE,
    PASSWORD VARCHAR(255),
    BALANCE DOUBLE DEFAULT 0
);

CREATE TABLE TRANSACTIONS (
    TRANS_ID INT PRIMARY KEY AUTO_INCREMENT,
    ACC_NO INT,
    TYPE VARCHAR(50),
    AMOUNT DOUBLE,
    DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ACC_NO) REFERENCES ACCOUNTS(ACC_NO)
);
