# Assistance Platform REST API

## Start server
To start the service run "activator run". In order to activate HTTPS (recommended) use "activator run -Dhttps.port=9443".

## API

### Errors
If an known error is caused by the API call (e.g. wrong parameters) then a JSON object with an "error" field is return, which describes the occured error.

### /user
##### POST   /register
* **Input:** JSON `{"email" : "a email", "password" : "a password"}`
* **Result:** If successfull, JSON object: `{"user_id" : (ID of the newly created user)}`

##### POST   /login
* **Input:** JSON `{"email" : "a email", "password" : "a password"}`
* **Result:** If successfull, JSON object: `{"token" : (token)}`
* **Further nodes:** Provide the received token in all subsequent requests. Provide the token in the *X-AUTH-TOKEN* field.
