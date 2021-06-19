# auth-api

OAuth 2.0 Authorization server implemented based on the documentation at https://www.oauth.com/

Grant types supported:

- client_credentials

Note:

- The API is currently version 0 (v0) and may get breaking changes. The version will be incremented to version 1 (v1) when the API is more stable and later breaking changes will result in a new
  version.

TODO:

- Finish the refresh token flow ([Issue #1](https://github.com/JarandAuth/auth-api/issues/1))
- Add scope parameter ([Issue #2](https://github.com/JarandAuth/auth-api/issues/2))
- Proper error messages for authentication failure ([Issue #3](https://github.com/JarandAuth/auth-api/issues/3))
- Protect internal endpoints ([Issue #5](https://github.com/JarandAuth/auth-api/issues/5))

# How to

#### 1. Use the scripts in the scripts-folder to "dockerize" the application with a Postgres database:

```bash
bash scripts/postgres.sh
bash scripts/buildAndRun.sh
```

#### 2. Create user with a default client

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v0/jarand-user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "example.user@jarand.dev",
    "username": "ExampleUser",
    "displayName": "ExampleUser",
    "password": "example-password"
}'
```

#### 3. Get tokens using the client credentials flow

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v0/oauth/token' \
--form 'grant_type="client_credentials"' \
--form 'client_id="example.user@jarand.dev"' \
--form 'client_secret="example-password"'
```

### Creating another client

- Replace ID in the URL with the id of the user

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v0/jarand-user/ID/jarand-client' \
--header 'Content-Type: application/json' \
--data-raw '{
    "clientId": "example-client",
    "clientSecret": "example-password"
}'
```
