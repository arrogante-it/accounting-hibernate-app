# AccountingHibernateApp

#### Persistence layer using Hibernate for an Accounting application.

## Install application:
- Clone application to your directory:
`git clone https://github.com/arrogante-it/accounting-hibernate-app.git`

- Build project: `mvn clean install`

## Prerequisite:
- JDK 11 or higher
- Maven 4.0.0 or higher

## Program main functional:

### ContractDAO methods:
- getById
- getAll
- save
- update
- delete

### CustomerDAO methods:
- getById
- getAll
- save
- update
- delete

### PaymentDAO methods:
- getById
- getAll
- save
- update
- delete
- findAllByContract
- findAllByCustomer
- findAllAmountMoreThan
