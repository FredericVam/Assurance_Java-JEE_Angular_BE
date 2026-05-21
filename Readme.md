# Backend Spring Boot bancaire prêt à lancer

projet backend inspiré d'un contexte bancaire :
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- JPA / Hibernate
- Validation
- Logs simples orientés audit

## Fonctionnalités
- inscription / connexion
- création de comptes bancaires
- consultation de ses comptes
- virement entre comptes
- historique des transactions


## Pré-requis
- Java 17+
- Maven 3.9+
- PostgreSQL

## Base PostgreSQL
Créer la base :

```sql
CREATE DATABASE bankdb;
```

Par défaut, le projet utilise :
- base : `bankdb`
- user : `postgres`
- mot de passe : `postgres`

On peux modifier ça dans `src/main/resources/application.properties`.

## Lancement

```bash
mvn spring-boot:run
```

ou

```bash
mvn clean package
java -jar target/bankapi-0.0.1-SNAPSHOT.jar
```

## Compte admin injecté au démarrage
- utilisateur : `admin`
- mot de passe : `Admin123`

## Endpoints

### Health
```http
GET /api/health
```

### Auth
```http
POST /api/auth/register
POST /api/auth/login
```

Exemple register :
```json
{
  "email": "user1@test.com",
  "password": "Password123"
}
```

Exemple login :
```json
{
  "user": "user1@test.com",
  "password": "Password123"
}
```

### Accounts
Nécessite `Authorization: Bearer <token>`

```http
POST /api/accounts
GET /api/accounts
GET /api/accounts/{iban}
```

Exemple création compte :
```json
{
  "initialBalance": 1500.00
}
```

### Transactions
Nécessite `Authorization: Bearer <token>`

```http
POST /api/transactions/transfer
GET /api/transactions/history/{iban}
```


## Idées d'amélioration pour une banque
- refresh token
- RBAC plus fin
- audit dédié en base
- rate limiting
- Docker Compose avec PostgreSQL
- tests JUnit / Mockito
- OpenAPI / Swagger
- pagination sur l'historique
- simulation anti-fraude plus poussée
