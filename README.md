# Baby Banking System

## Description
Project is REAST API for manipulate with users, their balances and contact details, like email and phone number.
There are functions for deep, flexible search users, update them, transfer money between accounts

## Stack
- **Programming language**: Java 17
- **Framework**: Spring Boot
- **Database**: PostgreSQL
- **Project management**: Maven
- **Extra tools**:
  - **Elasticsearch** (to realize flexible search users)
  - **Redis** (for caching API requests)
  - **JWT** (authentication)
  - **Testcontainers** (for integration tests)
  - **Swagger** (for generate API docs)

## Setup
### Requirements
- Installing Docker

### Launch project
1. Clone repository :
   ```bash
   git clone https://github.com/sh4dowbolt/babyBankingSystem.git
   cd babyBankingSystem
   ```
2. Launch docker-compose project
   ```bash 
   docker compose up -d
   ```
3. That'a all. App will be availavle on port 8080   

## Design decisions
1. **Caching**:
   - Using  Redis for caching on API layer, to deacrease database loading
   - Cache keys include keys of the entities to provisioning actual status of data

2. **Authentication**:
   - Simple JWT-authethication with `USER_ID` in claims. Authenticition available by phone + password.
   You can authorize by next users:

      | № | pnhone number | password      |
      |---|---------------|---------------|
      | 1 | 79991234567   | password123   |
      | 2 | 78881234567   | securepass456 |
      | 3 | 77771234567   | qwerty789     |


3. **Search users**:
   - Filtration by `name`, `email`, `phone` и `dateOfBirth` using Elasticsearch.

4. **Transaction operation**:
   - The operation of transfer money is protected by trancastions and checking:
     - Checking enough balance of source sender.
     - Checking exists source and targer accounts.
     - Thread safety with `@Transactional` and pesimistic lock.

5. **Function for increase all users balances**:
   - Implemented via Spring Scheduler, every 30 seconds increase all users balances by fiction percent with limit 207% of init deposit. The percent can be assigmment in 'application.properties'.
  
## Extra options
- **Swagger**: Configuratipon docs of API.
- **Logging**: Logging implemented by Spring AOP (for examples, to transfer money, increase users balances by schedule).

## Testing
- **Unit tests**: Coverage function of transfer money.
- **Integration tests**: Using Testcontainers for test API with MockMvc, Redis, Elasticsearch, PGSql

## Structure of the database
Tables according to the scheme:
- `USER`: Main data of users
- `ACCOUNT`: Balances of ursers
- `EMAIL` и `PHONE`: Conctact data about users

## Example of requests
- **Search for users**:
  ```http
  GET /api/v1/users?name=John&page=0&size=10
  ```
- **Update email**:
  ```http
  PUT /api/v1/email/{emailId}
  Body: {"email": "new@example.com"}
  ```
- **Transfer money**:
  ```http
  POST /api/v1/accounts
  Body: {"toUserId": 2, "value": 100.50}
  ```

## Documentation
Swagger UI available by:  
`http://localhost:8080/swagger-ui.html`

## Links
- [GitHub Repository](<https://github.com/sh4dowbolt/babyBankingSystem.git>)
