# Assistance Platform REST API

## Start server
To start the service run "activator run". In order to activate HTTPS (recommended) use "activator run -Dhttps.port=9443".

## API

### Errors
If an known error is caused by the API call (e.g. wrong parameters) then a JSON object with an "error" field is return, which describes the occured error.

### /user
##### POST   /register
* **Input:** JSON `{"email" : "a email", "password" : "a password"}`
* **Result:** Error message or number that represents the ID of the created user.

##### POST   /login
* **Input:** JSON `{"email" : "a email", "password" : "a password"}`
* **Result:** Error message or "welcome" message (will be changed in future to return token)
