# FinPal — AI Teen Finance Coach (Full-Stack)

## Quick Start

### Backend
```bash
cd backend
./mvnw spring-boot:run   # or mvnw.cmd on Windows
```
- Runs on http://localhost:8080
- H2 console at http://localhost:8080/h2 (JDBC URL: `jdbc:h2:mem:finpal`)

### Frontend
```bash
cd frontend
npm install
npm run dev
```
- Opens http://localhost:5173 — API is proxied to the backend

### CSV Format
`Date,Merchant,Description,Amount,Type`
```
2025-09-01,Starbucks,Iced latte,-5.25,DEBIT
2025-09-02,Uber,Home to school,-8.10,DEBIT
2025-09-03,Steam,Indie game,-12.99,DEBIT
2025-09-05,Allowance,Monthly allowance,100,CREDIT
```

Import on the Transactions page and explore categories + tips.
