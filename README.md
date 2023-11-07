# Ebank_Axon_microsevices_model
This project explains how to use CQRS and event sourcing in general, as well as how to use Axon with Spring Boot in the microservices space.
```
 the Axon Framework, a Java framework for building scalable and high-performance event-driven applications. 
```
## 📚Prerequisite
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![XAMPP](https://img.shields.io/badge/XAMPP-FB7A24.svg?style=for-the-badge&logo=XAMPP&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36.svg?style=for-the-badge&logo=Apache-Maven&logoColor=white)
![AXON](https://camo.githubusercontent.com/a13e831e1d35fe5af514fbe63432e5d5b1ec2ace8319206d230bfd90123e2691/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f41584f4e2d4672616d65776f726b2d253233454532452e7376673f7374796c653d666f722d7468652d6261646765)
![IntelliJ](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=for-the-badge&logo=IntelliJ-IDEA&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-badge&logo=MySQL&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
## 🌳Project Tree
```
.
├── main
│   ├── java
│   │   └── dev
│   │       └── elma
│   │           └── demo
│   │               ├── DemoApplication.java
│   │               ├── commands
│   │               │   ├── aggregates
│   │               │   │   └── AccountAggregate.java
│   │               │   └── controllers
│   │               │       └── AccountCommandController.java
│   │               ├── commonapi
│   │               │   ├── commands
│   │               │   │   ├── BaseCommand.java
│   │               │   │   ├── CreateAccountCommand.java
│   │               │   │   ├── CreditAccountCommand.java
│   │               │   │   └── DebitAccountCommand.java
│   │               │   ├── dtos
│   │               │   │   ├── CreateAccountCommandDTO.java
│   │               │   │   ├── CreditAccountCommandDTO.java
│   │               │   │   └── DebitAccountCommandDTO.java
│   │               │   ├── enums
│   │               │   │   ├── AccountStatus.java
│   │               │   │   └── TransactionType.java
│   │               │   ├── events
│   │               │   │   ├── AccountCreatedEvent.java
│   │               │   │   ├── AccountCreditedEvent.java
│   │               │   │   ├── AccountDebitedEvent.java
│   │               │   │   └── BaseEvent.java
│   │               │   └── exceptions
│   │               │       └── AccountCommandExceptions.java
│   │               └── queries
│   │                   ├── controllers
│   │                   │   └── QueriesController.java
│   │                   ├── entities
│   │                   │   ├── Account.java
│   │                   │   └── AccountTransaction.java
│   │                   ├── query
│   │                   │   ├── GetAllAcount.java
│   │                   │   ├── GetOne.java
│   │                   │   └── GetTransactionOfAccount.java
│   │                   ├── repositories
│   │                   │   ├── AccountRepository.java
│   │                   │   └── AccountTransactionRepository.java
│   │                   └── services
│   │                       └── AccountEventHandlerServices.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates


```
## Maven Dependencies
<table>
    <tr>
        <th>Axon</th>
        <th>Swagger</th>
        <th>Other</th>
    </tr>
    <tr>
        <td><a href="https://mvnrepository.com/artifact/org.axonframework/axon-spring-boot-starter/4.7.4"><img src="https://upload.wikimedia.org/wikipedia/commons/5/52/Apache_Maven_logo.svg"/></a></td>
        <td><a href="https://springdoc.org/"><img src="https://upload.wikimedia.org/wikipedia/commons/5/52/Apache_Maven_logo.svg"/></a></td>
        <td><a href="https://start.spring.io/"><img src="https://upload.wikimedia.org/wikipedia/commons/5/52/Apache_Maven_logo.svg"/></a></td>
    </tr>
</table>

## Project: General Schema
<img width="100%" alt="image" src="https://github.com/Elma-dev/Ebank_Axon_microsevices_model/assets/67378945/3ad9e0a7-c6c9-4af6-b67b-56cfe5661efd">

## Class Diagram
<img width="100%" alt="image" src="https://github.com/Elma-dev/Ebank_Axon_microsevices_model/assets/67378945/fabe8a72-dc56-48a3-94d1-b711cc715f95">

## Commands Building
`Commands are a fundamental concept. They are messages that represent requests to change the state of an aggregate (a domain object).`
```
There are 3 commands in our application:
    * Create Account Command
    * Credit Amount to Account Command
    * Debit Amount from Account Command
Also We have Base Command as an abstarct Class
``` 
### Create Base Class
The `@TargetAggregateIdentifier` annotation is often used in command handling methods within an aggregate to specify which field within the aggregate represents the target aggregate identifier. This is important for Axon to route commands to the correct aggregate instance.
```java
@AllArgsConstructor
public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier
    @Getter
    private T id;
}
```
### Create Account Command
```java
@Getter
public class CreateAccountCommand extends BaseCommand<String>{
    private BigDecimal balance;
    private String currency;
    public CreateAccountCommand(String id,BigDecimal balance,String currency) {
        super(id);
        this.balance=balance;
        this.currency=currency;
    }
}
```
### Create Debit Command
```java
@Getter
public class CreditAccountCommand extends BaseCommand<String>{
    private BigDecimal amount;
    private String currency;
    public CreditAccountCommand(String accountId, BigDecimal amount,String currency) {
        super(accountId);
        this.amount=amount;
        this.currency=currency;
    }
}
```

### Create Credit Command
```java
@Getter
public class DebitAccountCommand extends BaseCommand<String>{
    private BigDecimal amount;
    private String currency;
    public DebitAccountCommand(String accountId, BigDecimal amount, String currency) {
        super(accountId);
        this.amount=amount;
        this.currency=currency;
    }
}
```
## Event Building
`Events are closely related to commands, as mentioned in the previous response. While commands represent requests to perform actions, events represent the results or consequences of those actions.`
