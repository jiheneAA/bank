# Bank account kata
Think of your personal bank account experience When in doubt, go for the simplest solution

## Technologies used in this application
- Spring Boot
- Spring Data JPA
- H2 Database (embedded)
- Spring Security
- Thymeleaf
- Maven
- GIT
- Intellij
- SonarLint

N.B. USER (bank client) can have many accounts
## Using BANK ACCOUNT Application:
1. LOGIN : http://localhost:8080/login (login: jiheneAA, password: jihene_pwd), (login: soat, password: soat_pwd)
2. Accounts : http://localhost:8080/accounts
3. Deposit : http://localhost:8080/operations/deposit
3. Withdrawal : http://localhost:8080/operations/withdrawal
4. Operations history : http://localhost:8080/operations
5. All users REST : http://localhost:8080/users
6. Current user REST : http://localhost:8080/users/current-user
7. Access to H2 : http://localhost:8080/h2-console

## TO RUN project
- mvn clean install -DskipTests
- mvn spring-boot:run
 
## User Stories
##### US 1:
**In order to** save money  
**As a** bank client  
**I want to** make a deposit in my account  
 
##### US 2: 
**In order to** retrieve some or all of my savings  
**As a** bank client  
**I want to** make a withdrawal from my account  
 
##### US 3: 
**In order to** check my operations  
**As a** bank client  
**I want to** see the history (operation, date, amount, balance)  of my operations  