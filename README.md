# auth-api

OAuth 2.0 Authorization server implemented based on the documentation at https://www.oauth.com/

Grant types supported:

- client_credentials
- refresh_token

Note:

- The API is currently version 0 (v0) and may get breaking changes. The version will be incremented to version 1 (v1) when the API is more stable and later breaking changes will result in a new
  version.

## Upcoming changes

See [GitHub Issues](https://github.com/JarandAuth/auth-api/issues)

## Contributing

I want to implement as much functionality as possible myself to learn about OAuth 2.0. If you find something not implemented according to the guide at https://oauth.com, I would really appreciate if
you submit an issue explaining what's wrong. You might as well submit a pull request fixing it if you want, but that's definitely not required.

### Feature requests

The project does not currently accept new feature requests.

### Bugs

Noticed something which is not working as expected? Please open a bug report: [Create bug report](https://github.com/JarandAuth/auth-api/issues/new?assignees=&labels=bug&template=bug_report.md&title=)

## How to

#### 1. Use the scripts in the scripts-folder to "dockerize" the application with a Postgres database:

```bash
bash scripts/postgres.sh
bash scripts/buildAndRun.sh
```

#### 2. Create user

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v0/jarand-user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "example.user@jarand.dev",
    "username": "ExampleUser",
    "displayName": "Example user",
    "password": "example-password"
}'
```

This returns a user with one client for using the password flow and one client for using the client credentials and refresh token flows:

```json
{
  "id": "c7128eb1-9928-4add-b483-38fda7167aed",
  "email": "example.user@jarand.dev",
  "username": "ExampleUser",
  "displayName": "Example user",
  "timeOfCreation": "2021-06-21T13:37:35.217560800Z",
  "loginClient": {
    "clientId": "0c995527-2a36-42ca-97be-c0a88eca0ea6",
    "username": "ExampleUser",
    "password": "$2a$10$lwVsjo0wC0hg1Ik/RxtI5OcjJe1KEM8v2QogAdcUFbMACFbJNwN6O",
    "ownerId": "c7128eb1-9928-4add-b483-38fda7167aed",
    "timeOfCreation": "2021-06-21T13:37:35.217560800Z",
    "grantedTypes": [
      {
        "grantType": "password",
        "clientId": "0c995527-2a36-42ca-97be-c0a88eca0ea6"
      }
    ]
  },
  "clients": [
    {
      "clientId": "5d406c1e-d4c7-4f0f-8ed1-27aaa18f466c",
      "clientSecret": "$2a$10$olknEq98XHrif41XiOI7k.000KgSnkD9u5ftGEnU5fzMk27Kbl8dC",
      "ownerId": "c7128eb1-9928-4add-b483-38fda7167aed",
      "timeOfCreation": "2021-06-21T13:37:35.217560800Z",
      "grantedTypes": [
        {
          "grantType": "client_credentials",
          "clientId": "5d406c1e-d4c7-4f0f-8ed1-27aaa18f466c"
        },
        {
          "grantType": "refresh_token",
          "clientId": "5d406c1e-d4c7-4f0f-8ed1-27aaa18f466c"
        }
      ]
    }
  ]
}
```

#### 2. Get tokens using the password flow

- Replace USERNAME with the username submitted in step 1
- Replace PASSWORD with the password submitted in step 1

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v0/oauth/token' \
--form 'grant_type="password"' \
--form 'username="USERNAME"' \
--form 'password="PASSWORD"'
```

#### 3. Get tokens using the client credentials flow

- Replace CLIENT_ID with clientId from the client in the `clients`-list returned in step 1
- Replace CLIENT_SECRET with the password submitted in step 1

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v0/oauth/token' \
--form 'grant_type="client_credentials"' \
--form 'client_id="CLIENT_ID"' \
--form 'client_secret="CLIENT_SECRET"'
```

#### 4. Get access token using the refresh token flow

- Replace CLIENT_ID with clientId from the client in the `clients`-list returned in step 1
- Replace CLIENT_SECRET with the password submitted in step 1
- Replace REFRESH_TOKEN with refresh token from the client credentials or password flow

```bash
curl --location --request POST 'http://localhost:8080/security/auth/v0/oauth/token' \
--form 'grant_type="refresh_token"' \
--form 'client_id="CLIENT_ID"' \
--form 'client_secret="CLIENT_SECRET"' \
--form 'refresh_token="REFRESH_TOKEN"'
```
