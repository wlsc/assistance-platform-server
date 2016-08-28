# REST API for Clients

Welcome to API wiki!

This page briefly describes and explained JSON API communication protocol between recommender clients and server back-end. 

## Quick access

* [Error messages](#client-erorrs)
* [User: Registration](#registering-a-user)
* [User: Password reset (NOT IMPLEMENTED!)](#reset-password)
* [User: Log in](#log-in)
* [User: Refresh token](#refresh-token)
* [User: Register for back-channel](#register-for-back-channel)
* [User: List users devices](#list-users-devices)
* [User: Set user defined name for device](#set-user-defined-name-for-device)
* [User: Get user's profile: short version](#get-users-profile-short-version)
* [User: Get user's profile: long version](#get-users-profile-long-version)
* [User: UPDATE user's profile (NOT IMPLEMENTED!)](#update-users-profile)
* [Assistance: Getting list of available modules](#get-list-of-available-modules)
* [Assistance: Activate Module](#activate-a-module)
* [Assistance: Deactivate Module](#deactivate-a-module)
* [Assistance: List of current activations](#list-of-current-activations)
* [Assistance: Current module feedback](#current-information-from-modules)
* [Module Assistance: Current information from modules](#current-information-from-modules)
* [Module Assistance: Current information from specific module](#current-information-from-specific-module)
* [Module Assistance: Custom request to a module](#custom-request-to-a-module)
* [Sensor data: Upload](#uploading-sensor-data)

## Current (v0.6)

#### ERROR MESSAGES
The assistance platform REST API tries to return proper HTTP status codes.
* 200 - OK - Success
* 400 - Bad request - The request was invalid, e.g. incomplete
* 401 - Unauthorized - The X-AUTH-TOKEN was not provided
* 500 - Internal server error - Something went wrong on server side

If something went wrong which needs more specification, mostly the API provides an error like this.

```javascript
HTTP/1.1 400 Bad Request
Content-Length: 39

{
"code" : integer,
"message" : string
}
  ```

The code is a machine readable code which specifies a certain error type.
The message is a descriptive text (which can change in future).

Current errors for the User API are:
* 0 - Unspecified error
* 1 - Unauthorized
* 2 - Bad authentication data (email / password was wrong)
* 3 - The user with the provided email already exists.
* 4 - Not all parameters (email and password) were provided.
* 5 - The module id was not provided.
* 6 - The module is already activated.
* 7 - The module is not activated.
* 8 - The module does not exist.
* 9 - The user is already logged in. [thrown when registering]
* 10 - Missing parameters (general).
* 11 - Invalid parameters (general).
* 12 - Device ID not known.
* 13 - Platform is not supported.

For the module API:
* 995 - Not all required parameters for module registration were provided.
* 996 - The module with the provided id already exists.
* 997 - The user with the provided id does not exist.

### USER

#### REGISTERING A USER

* REQUEST

```javascript
POST /users/register

{
"email" : "example@example.com", 
"password" : "password"
}
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

{
"user_id" : integer
}
  ```

#### Reset password

* REQUEST

```javascript
GET /users/password

{
"email" : "hand.mustermann@example.com"
}
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK
  ```

Then user should received confirmation url for password reset on his email address (password randomly generated). User confirms that and then he is able to log in with that randomly generated password again.

#### LOG IN

Note: The "device" object should be used like following.

* Send spec on first login (os, os_version, etc.). The device id will be returned.
* On subsequent logins JUST send the id, without spec.
* If changes to the spec are detected (os_version, device_identifier, etc.), then provide id AND spec - this'll update the spec for the device

NOTE: device_identifier does NOT equal id / device_id. id / device_id are internal numbers for devices assigned by the assistance platform (server). Device identifier is the identifier provided by the client platform and should be provided in the specification.

* REQUEST

```javascript
POST /users/login

{
"email" : "example@example.com", 
"password" : "password",
"device" : { 
    "id" : long, /* Optional: Provide ONLY id OR id and spec OR spec only - See description above!! */
    "os" : string, /* e.g. android, ios, blackberry, windowsphone */
    "os_version" : string, /* e.g. 5.1.1 */
    "brand" : string, /* e.g. Samsung */
    "model" : string, /* e.g. Galaxy XY */
    "device_identifier" : string
}
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

{
"token" : string,
"device_id" : long
}
  ```

#### REFRESH TOKEN

The client can request a new token within the time range of validity (normally 24 hours). This new token will be again valid for 24 hours.

* REQUEST

```javascript
POST /users/refresh_token
X-AUTH-TOKEN: your_token
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

{
"token" : string
}
  ```
#### Register for back-channel

A device needs to explicitly register a back-channel from the platform to this device of the user, because the project uses existing messaging services especially for the mobile devices. So the client has to pass the token of the specific service to the platform.

(Note: This is NOT done / integrated in the login request, because it needs a asynchronous request to acquire the token from the respective service)

* REQUEST

```javascript
POST /devices/register_for_messaging
X-AUTH-TOKEN: your_token

{ 
  "device_id" : long, /* This is the ID that is contained in the login response */ 
  "messaging_registration_id" : string /* This is the ID / Token that is returned form the respective service, e.g. GCM */
}
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

{
}
  ```

####  List users devices

* REQUEST

```javascript
GET /devices/list
X-AUTH-TOKEN: your_token

  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

[
    {
        "id": long,
        "userId": long,
        "brand": string,
        "model": string,
        "messagingRegistrationId": string,
        "userDefinedName": string,
        "lastUsage": long,
        "os": string,
        "os_version": string,
        "device_identifier": string
    }, ...
]
  ```

#### Set user defined name for device

* REQUEST

```javascript
POST /devices/set_user_defined_name
X-AUTH-TOKEN: your_token

{ 
  "device_id" : long,
  "user_defined_name" : string /* The user defined name for a device, e.g. "My Galaxy" */
}
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

{
}
  ```

#### Get user's profile: short version

* REQUEST

```javascript
GET /users/profile/short
X-AUTH-TOKEN: your_token

  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

{
"firstName" : "Hans",
"lastName" : "Mustermann",
"email" : "hans.mustermann@domain.de",
"lastLogin" : long,
"joinedSince" : long,
}
  ```

#### Get user's profile: long version

* REQUEST

```javascript
GET /users/profile/long
X-AUTH-TOKEN: your_token

  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK

{
"firstName" : "Hans",
"lastName" : "Mustermann",
"email" : "hans.mustermann@domain.de",
"services [NOT IMPLEMENTED]"  : [{
               "name" : "Google",
               "firstname" : "Hanso",
               "lastname" : "Mustermanno",
               "email" : "hans.mustermann@gmail.com",
               "updated" : long,
               "created" : long
               },
               {
               "name" : "Facebook",
               "firstname" : "Dieter",
               "lastname" : "Klein",
               "email" : "hans.mustermann@gmx.de",
               "updated" : long,
               "created" : long
               },
               {
               "name" : "Xing",
               "firstname" : "Hans",
               "lastname" : "Mustermann",
               "email" : "hans.mustermann@gmail.com",
               "updated" : long,
               "created" : long
               }],
"lastLogin" : long,
"joinedSince" : long,
}
  ```

#### UPDATE user's profile

* REQUEST

```javascript
PUT /users/profile
X-AUTH-TOKEN: your_token

{
    "firstname" : "Hans",
    "lastname" : "Mustermann",
    "services [NOT IMPLEMENTED]"  : [{
        "name" : "Google",
        "email" : "hans.mustermann@gmail.com"
    },
    {
        "name" : "Facebook",
        "email" : "hans.mustermann@gmx.de"
    }]
}
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK
  ```

#### GET LIST OF AVAILABLE MODULES

* REQUEST

(optional) :language = ISO 639-1 code for client language (e.g. en, de) [if parameter not specified, it is en]

```javascript
GET /assistance/list/:language
X-AUTH-TOKEN: your_token
```

* RESPONSE

```javascript
HTTP/1.1 200 OK

[
    {
        "name": "Quantified self",
        "id": "de.tudarmstadt.informatik.tk.assistanceplatform.modules.quantifiedself",
        "logoUrl": "http://xyz.de/lgo123.png",
        "descriptionShort": "Quantifies you in every way",
        "descriptionLong": "Really quantifies out of every angle",
        "requiredCapabilities": [
            { 
              "type" : "accelerometer",
                "collection_interval": double, /* The maximum interval between event readings of this type (in seconds). Readings can be cached on the client. (in seconds) double; 1.0 = 1 measurement per second, 60.0 = 1 measurement per minute */
                "update_interval": double, /* The maximum interval in seconds after which the sensor readings have to be sent to the platform. -1.0 means that that the readings are only sent when the device is connected via WiFi or hasn't sent any updates for 24 hours. */
            },
            { 
              "type" : "position",
                "accuracy": int, /* Only available for the position sensor. 0: low, 1: balanced, 2: best */
                "collection_interval": double,
                "update_interval": double
            }
        ],
        "optionalCapabilites": [
            { 
              "type" : "facebooktoken",
                "permissions": array of string /* The permissions to request from facebook for this module. e.g. ["email", "public_profile", "user_friends"] */
            }
        ],
        "copyright": "TK Department TU Darmstadt"
        "supportEmail": "support@xy.de" /* E-Mail address where the end-user can get support regarding this module */
    },
    {
        "name": "Hot Places",
        "id": "de.tudarmstadt.informatik.tk.assistanceplatform.modules.hotplaces",
        "logoUrl": "http://blabla.de/hotzone.png",
        "descriptionShort": "Finds the hottest places",
        "descriptionLong": "Finds the hottest places while you are moving",
        "requiredCapabilities": [
{ 
              "type" : "position",
                "collection_frequency": double, 
                "required_update_frequency": double, 
                "min_required_readings_on_update": int 
            }
        ],
        "optionalCapabilites": null,
        "copyright": "TK Department TU Darmstadt",
        "supportEmail": "support@xy.de" 
    }
]
  ```

#### ACTIVATE A MODULE

* REQUEST

```javascript
POST /assistance/activate
X-AUTH-TOKEN: your_token

{
   "module_id": "de.tudarmstadt.informatik.tk.assistanceplatform.modules.quantifiedself"
}
```

* RESPONSE

```javascript
HTTP/1.1 200 OK
```

#### DEACTIVATE A MODULE

* REQUEST

```javascript
POST /assistance/deactivate
X-AUTH-TOKEN: your_token

{
   "module_id": "de.tudarmstadt.informatik.tk.assistanceplatform.modules.quantifiedself"
}
```

* RESPONSE

```javascript
HTTP/1.1 200 OK
```

#### List of current activations

Returns an array of IDs of the activated modules (for the authorized user).

* REQUEST

```javascript
GET /assistance/activations
X-AUTH-TOKEN: your_token
```

* RESPONSE

```javascript
HTTP/1.1 200 OK

[ 
    "de.tudarmstadt.informatik.tk.assistanceplatform.modules.quantifiedself",
]
```

#### Current information from modules

Returns a list of "module information cards" from the currently activated modules. A module information card provides a container of data which represents the last relevant information for the requesting user.

(required) :deviceId - The device id of the requesting device.

The content specification can be found here [Client Feedback Channel Format](./Client-Feedback-Channel-Format)

* REQUEST

```javascript
GET /assistance/current/:deviceId
X-AUTH-TOKEN: your_token
```

* RESPONSE

```javascript
HTTP/1.1 200 OK

[
    {
        "modulePackageName": string,
        "content": {
            "type": string,
            ...
        },
        "created": date
    },
    {
        "modulePackageName": string,
        "content": {
            "type": string,
            ...
        },
        "created": date
    }
]
```

#### Current information from specific module

Returns a list of "module information cards" from the requested module.

(required) :moduleId - The id of the module which should be asked about most current information.
(required) :deviceId - The device id of the requesting device.

* REQUEST

```javascript
GET /assistance/:moduleId/current/:deviceId
X-AUTH-TOKEN: your_token
```

* RESPONSE

```javascript
HTTP/1.1 200 OK

[
    {
        "moduleId": "de.tudarmstadt.informatik.tk.assistanceplatform.modules.hotplaces",
        "timestamp": 1446751643000,
        "payload": string
    }
]
```

#### Custom request to a module

With this endpoint it is possible to send very specific requests to a module. Normally this will only be used by module-specific clients. The platform acts like a proxy in this case.

(required) :moduleId - The id of the module which should be asked about most current information.
(required) *path - The path that should be requested on the module.

Currently only GET and POST requests are possible.

* REQUEST

```javascript
GET/POST /assistance/:moduleId/custom/*path
X-AUTH-TOKEN: your_token

(optional) your POST-data
```

* RESPONSE

```javascript
HTTP/1.1 SPECIFIED BY RESPONDING MODULE

SPECIFIED BY RESPONDING MODULE
```

### SENSOR DATA

#### UPLOADING SENSOR DATA

The upload endpoint allows to upload multiple collected sensor readings in batch mode.

**NOTE:** The sensor readings have to be sorted by time descanding, so the oldest events appear at first in the JSON.

* REQUEST

```javascript
POST /sensordata/upload
X-AUTH-TOKEN: your_token

{
   "device_id" : the current device id (returned on login)
   "sensorreadings" : [
     {
        "type" : sensortype (see below),
        ... attributes of sensor (see below) ...
     }
   ]
}
  ```

* RESPONSE

```javascript
HTTP/1.1 200 OK
```

**The supported sensor types and required fields can be found here:** [Sensor types](Sensor-Types-and-API-mapping)

#### tbd