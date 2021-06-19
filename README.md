# auth-api

OAuth 2.0 Authorization server implemented based on the documentation at https://www.oauth.com/

Grant types supported:

- client_credentials

TODO:

- Finish the refresh token flow
- Add scope parameter
- Proper error messages for authentication failure

# How to

1. Use the scripts in the scripts-folder to run the application with a Postgres database:

```bash
bash scripts/postgres.sh
bash scripts/buildAndRun.sh
```

2. Create user

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v1/jarand-user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "example.client@jarand.dev",
    "username": "example-client",
    "displayName": "Example client",
    "password": "example-password"
}'
```

3. Get tokens using the client credentials flow

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v1/oauth/token' \
--form 'grant_type="client_credentials"' \
--form 'client_id="example-client"' \
--form 'client_secret="example-password"'
```
