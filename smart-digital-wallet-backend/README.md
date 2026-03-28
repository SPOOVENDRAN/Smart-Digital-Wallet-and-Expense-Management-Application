# Smart Digital Wallet & Expense Management Backend

Spring Boot backend for wallet and expense management.

## Tech Stack
- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 in-memory database

## Run
```bash
mvn spring-boot:run
```

The API runs on `http://localhost:8080`.

## Main APIs

### Wallet APIs
- `POST /api/wallets` - create wallet
- `GET /api/wallets` - list wallets
- `GET /api/wallets/{id}` - wallet details
- `POST /api/wallets/{id}/deposit` - add money
- `POST /api/wallets/{id}/withdraw` - withdraw money
- `GET /api/wallets/{id}/balance` - wallet balance

### Expense APIs
- `POST /api/expenses` - create expense (deducts wallet balance)
- `GET /api/expenses` - list expenses with optional filters (`walletId`, `fromDate`, `toDate`, `category`)
- `GET /api/expenses/{id}` - expense details
- `DELETE /api/expenses/{id}` - delete expense (refunds wallet balance)

## Sample Request Bodies

Create wallet:
```json
{
  "ownerName": "Prethive",
  "initialBalance": 2500.00
}
```

Deposit/Withdraw:
```json
{
  "amount": 300.00
}
```

Create expense:
```json
{
  "walletId": 1,
  "amount": 450.00,
  "category": "FOOD",
  "expenseDate": "2026-03-27",
  "description": "Dinner"
}
```

## H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:walletdb`
- Username: `sa`
- Password: *(empty)*
