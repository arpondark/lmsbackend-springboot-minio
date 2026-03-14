# LMS Backend

## JWT Login

This backend now supports JWT-based authentication.

### Login Endpoint

- `POST /api/auth/login`
- Request body:

```json
{
  "username": "admin",
  "password": "your-password"
}
```

- Response body includes `accessToken`.

### Use Token

Send the token in the `Authorization` header:

`Authorization: Bearer <accessToken>`

### Required Config

Set these properties in `src/main/resources/application.properties`:

- `jwt.secret` (at least 32 characters)
- `jwt.expiration-ms`
- `jwt.issuer`

### Run

```powershell
.\mvnw.cmd spring-boot:run
```

