## Quick access

* [Error messages](./REST-API#client-erorrs)
* [Module: Register new module](#registering-a-module)
* [Module: Localize module information] (#localizing-module-information)
* [Module: Update module information](#update-module-information)

### MODULES (not intended to be used by any client! Just by modules!)

#### REGISTERING A MODULE

* REQUEST

```javascript
POST /modules/register

{
        "id" : "de.tudarmstadt.tk.assistance.hotzones",
        "name" : "Hotzones",
        "logoUrl" : "http://urlToLogo",
        "descriptionShort" : "Short Description (255 chars)",
        "descriptionLong" : "Long (2048 chars)",
        "requiredCaps" : [
           { 
              "type" : "position",
 "collection_interval": double, /* The maximum interval between event readings of this type (in seconds). Readings can be cached on the client. (in seconds) double; 1.0 = 1 measurement per second, 60.0 = 1 measurement per minute */
                "update_interval": double, /* The maximum interval in seconds after which the sensor readings have to be sent to the platform. -1.0 means that that the readings are only sent when the device is connected via WiFi or hasn't sent any updates for 24 hours. */
            }
        ]
        "optionalCaps" : []
        "copyright" : "TK Informtik TU Darmstadt",
        "administratorEmail" : "email of administrator",
        "supportEmail" : "email of support",
        "restContactAddress" : "address or just port of module rest server (e.g. 127.0.0.1:8123 or 8123)"
}
```

* RESPONSE

```javascript
HTTP/1.1 200 OK

```

#### LOCALIZING MODULE INFORMATION

* REQUEST

```javascript
POST /modules/localize

{
        "languageCode" : "de",
        "id" : "de.tudarmstadt.tk.assistance.hotzones",
        "name" : "Beliebte Orte",
        "logoUrl" : "http://urlToGermanLogo",
        "descriptionShort" : "Kurze Beschreibung (255 chars)",
        "descriptionLong" : "Lange Beschreibung (2048 chars)"
}
```

* RESPONSE

```javascript
HTTP/1.1 200 OK

```

### UPDATE MODULE INFORMATION

* REQUEST

```javascript
POST /modules/update

{
        "id" : "de.tudarmstadt.tk.assistance.hotzones",
        "name" : "Hotzones",
        "logoUrl" : "http://urlToLogo",
        "descriptionShort" : "Short Description (255 chars)",
        "descriptionLong" : "Long (2048 chars)",
        "requiredCaps" : [
            {
 "type" : "position",
 "collection_interval": double, /* The maximum interval between event readings of this type (in seconds). Readings can be cached on the client. (in seconds) double; 1.0 = 1 measurement per second, 60.0 = 1 measurement per minute */
                "update_interval": double, /* The maximum interval in seconds after which the sensor readings have to be sent to the platform. -1.0 means that that the readings are only sent when the device is connected via WiFi or hasn't sent any updates for 24 hours. */
            }
        ],
        "optionalCaps" : [],
        "copyright" : "TK Informtik TU Darmstadt",
        "administratorEmail" : "email of administrator",
        "supportEmail" : "email of support",
        "restContactAddress" : "address or just port of module rest server (e.g. 127.0.0.1:8123 or 8123)"
}
```

* RESPONSE

```javascript
HTTP/1.1 200 OK

```

### Send message / data to client

* REQUEST

```javascript
POST /action/sendmessage

{
  "userId" : long,
  "deviceIds" : [ long values ],
  "visibleNotification" : { /* optional */
    "title" : string,
    "body" : string
  },
  "data" : string /* Payload like action command in JSON format - TBD */
}
```


* RESPONSE

```javascript
HTTP/1.1 200 OK

```

### Get list of activations for module

* REQUEST

```javascript
GET /modules/activations/:moduleId

```

* RESPONSE

```javascript
HTTP/1.1 200 OK

[ /* Array of user IDs that are registered for module with ID :moduleId */
id_1,
...,
id_n
]
```

#### tbd