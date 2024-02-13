# AccountingHibernateApp

#### Persistence layer using Hibernate for an Accounting application.

## Install application:
- Clone application to your directory:
`git clone https://github.com/arrogante-it/accounting-hibernate-app.git`

- Build project: `mvn clean install`

- CREATE DATABASE accounting_db;
- CREATE USER accounting IDENTIFIED BY 'accounting';
- GRANT ALL PRIVILEGES ON accounting_db.* TO 'accounting';
- FLUSH PRIVILEGES;

- execute SQL script: src/main/resources/create_table.sql

## Prerequisite:
- JDK 11 or higher
- Maven 4.0.0 or higher
- MySQL 8.0 or higher 

## Program main functional:

### ContractDao methods:
- Save 
- Update
- Delete
- Get by id
- Get all

### CustomerDAO methods:
- Save 
- Update
- Delete
- Get by id
- Get all

### PaymentDAO methods:
- Save 
- Update
- Delete
- Get by id
- Get all
- Find all by contract
- Find all by customer
- Find all amount more than
