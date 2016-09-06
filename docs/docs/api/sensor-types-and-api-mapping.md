This page lists the supported sensors and the fields that the client has to provide when submitting the respective sensor readings.

#### POSITION SENSOR (type = "position")
```javascript
* REQUIRED

    "latitude" : double
    "longitude" : double
    "accuracyHorizontal" : double        
    "speed" : float /* Meters per second */
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    
    "altitude" : double
    "accuracyVertical" : double 
    "course" : int
    "floor" : int
```

#### GYROSCOPE SENSOR (type = "gyroscope")
```javascript
* REQUIRED

    "x" : double
    "y" : double
    "z" : double
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
   
    xUncalibratedNoDrift : float
    "yUncalibratedNoDrift" : float
    "zUncalibratedNoDrift" : float
    
    xUncalibratedEstimatedDrift  : float
    "yUncalibratedEstimatedDrift" : float
    "zUncalibratedEstimatedDrift" : float
```

#### ACCELEROMETER SENSOR ( type = "accelerometer" )
```javascript
* REQUIRED

    "x" : double
    "y" : double
    "z" : double
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    
    "accuracy" : int
```

#### MAGNETIC FIELD SENSOR (type = "magneticfield")
```javascript
* REQUIRED

    "x" : double
    "y" : double
    "z" : double
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    
    "accuracy" : int

    "xUncalibratedNoHardIron" : float
    "yUncalibratedNoHardIron" : float
    "zUncalibratedNoHardIron" : float
    
    "xUncalibratedEstimatedIronBias" : float
    "yUncalibratedEstimatedIronBias" : float
    "zUncalibratedEstimatedIronBias" : float
```

#### MOTION ACTIVITY SENSOR (type = "motionactivity")
All integer values here are probabilities (values: 0-100) in percent.

```javascript
* REQUIRED

    "walking" : integer
    "running" : integer
    "cycling" : integer
    "driving" : integer
    "stationary" : integer
    "unknown" : integer
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    
    "onFoot" : integer    
    "tilting" : integer
```

#### CONNECTION EVENT (type = "connection")
```javascript
* REQUIRED

    "isWifi" : boolean 
    "isMobile" : boolean 
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
```

#### WIFI CONNECTION EVENT (type = "wificonnection")
```javascript
* REQUIRED

    "ssid" : string 
    "bssid" : string
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    
    "channel" : int   
    "frequency" : int
    "linkSpeed" : int
    "signalStrength" : int
    "networkId" : int
```

#### MOBILE DATA CONNECTION EVENT (type = "mobileconnection")
```javascript
* REQUIRED

    "carrierName" : string
    "mobileCountryCode" : string
    "mobileNetworkCode" : string
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    
    "voipAvailable" : boolean  
```

#### LOUDNESS EVENT (type = "loudness")
```javascript
* REQUIRED

    "loudness" : float 
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
```

#### FOREGROUND EVENT (type = "foreground")
```javascript
* REQUIRED

    "eventType" : string
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    
    "packageName" : string
    "appName" : string
    "className" : string
    "activityLabel" : string
    "color" : string
    "url" : string
    "keystrokes" : integer
```

#### LIGHT EVENT (type = "light")
```javascript
* REQUIRED

    "value" : float,
    "accuracy" : int,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

```

#### CALL LOG EVENT (type = "call_log")
```javascript
* REQUIRED

    "callId" : long,
    "callType" : int,
    "name" : String,
    "number" : String,
    "date" : long,
    "duration" : long,
    "isNew" : boolean,
    "isUpdated" : boolean,
    "isDeleted" : boolean,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

```

#### NETWORK TRAFFIC EVENT (type = "networktraffic")
```javascript
* REQUIRED
    "appName" : string,
    "rxBytes" : long,
    "txBytes" : long,
    "background" : boolean,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL
    "longitude" : double,
    "latitude" : double

```

#### POWERSTATE EVENT (type = "powerstate")
```javascript
* REQUIRED

    "isCharging" : boolean,
    "percent" : float
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!

* OPTIONAL

    "chargingState" : int, <—  0: NONE, 1: LOW, 2: OKAY, 3: FULL, 4: MALFUNCTION/NOT CHARGING, BUT CONNECTED
    "chargingMode" : int,  <—  0: AC, 1: USB, 2: WIRELESS (currently only Android)
    "powerSaveMode" : boolean <— (currently only iOS)
```

#### RINGTONE EVENT (type = "ringtone")
```javascript
* REQUIRED
    "mode" : int,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
```

### RUNNING PROCESS EVENT (type = "runningprocess")
```javascript
* REQUIRED
    "name" : string,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
```

### RUNNING TASK EVENT (type = "runningtask")
```javascript
* REQUIRED
    "name" : string,
    "stackPosition" : int,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
```

### RUNNING SERVICE EVENT (type = "runningservice")
```javascript
* REQUIRED
    "packageName" : string,
    "className" : string,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
```

### POWERLEVEL EVENT (type = "powerlevel")
```javascript
* REQUIRED
    "percent" : float,
    "created" : "2015-08-04T22:04:50Z"    <--  in ISO 8601 format!
```

### CALENDAR EVENT (type = "calendar")
```javascript
* REQUIRED
    "eventId" : string,
    "calendarId" : string,
    "allDay" : boolean,
    "availability" : int, // -1: NotSupported, 0: Busy, 1: Free, 2: Tentative und 3: Unavailable.
    "description" : string,
    "startDate" : string, // ISO 8601
    "endDate" : string, // ISO 8601
    "location" : string,
    "status" : int, // -1: none, 0: Tentative, 1: Confirmed, 2: Canceled,
    "title" : string,
    "recurrenceRule" : string,
    "alarms" : [{ /* array of alarms */
        "type" : int, /* REQUIRED */
        "defaultOffset" : boolean, /* REQUIRED* /
        "offset" : int, /* OPTIONAL */
        "absoluteDate" : string, // ISO 8601 /* OPTIONAL */
        "proximity" : int, // <- 0: None, 1: Enter, 2: Leave (Types for location based alarm) /* OPTIONAL */
        "locationTitle" : string, /* OPTIONAL */
        "locationLatitude" : double, /* OPTIONAL */
        "locationLongitude" : double, /* OPTIONAL */
        "locationRadius" : double (in meters) /* OPTIONAL */
    }],
    "isDeleted" : boolean,
    "created" : string // ISO 8601

* OPTIONAL

    "URL" : string,
    "isDetached" : boolean,
    "lastModifiedDate" : string, // ISO 8601
    "duration" : string,
    "originalAllDay" : boolean,
    "originalId" : string,
    "originalInstanceTime" : long,
    "recurrenceExceptionDate" : string,
    "recurrenceExceptionRule" : string,
    "lastDate" : long,
    "recurrenceDate" : string 
```

### CONTACT EVENT (type = "contact")
```javascript
* REQUIRED
    "globalContactId" : string,
    "givenName" : string,
    "familyName" : string,
    "note" : string,
    "isDeleted" : boolean,

* OPTIONAL

    "displayName" : string,
    "starred" : int,
    "lastTimeContacted" : int,
    "timesContacted" : int,
    "contactType" : int,  <- 0: Person, 1: Organisation
    "namePrefix" : string,
    "middleName" : string,
    "previousFamilyName" : string,
    "nameSuffix" : string,
    "nickname" : string,
    "phoneticGivenName" : string,
    "phoneticMiddleName" : string,
    "phoneticFamilyName" : string,
    "organizationName" : string,
    "departmentName" : string,
    "jobTitle" : string,
    "phoneNumbers" : [{"label": "Home", "value": "+49 123 456789"}, {"label": "Office", "value": "+49 987 654321"}], // array of labeled values
    "emailAddresses" : [{"label": "Home", "value": "chris@fancymail.com"}, {"label": "Office", "value": "professor.christian@tu-darmstadt.de"}] // array of labeled values
```

# Social Networks

## Facebook

### FACEBOOK TOKEN EVENT (type = "facebooktoken")
NOTE: This is saved on per-user basis! Unlike the other event types (which are saved per-device basis).

```javascript
* REQUIRED
    "oauthToken" : string,
    "permissions" : ["email", "public_profile", "user_friends"], /* array of strings */
    "declinedPermissions" : array of string,
    "created" : string // ISO 8601
```

## TuCan

### TUCAN CREDENTIALS EVENT (type = "tucancredentials")
NOTE: This is saved on per-user basis! Unlike the other event types (which are saved per-device basis).
NOTE 2: This will be saved encrypted, so only allowed modules will be able to get those values from the database.

```javascript
* REQUIRED
    "username" : string,
    "password" : string,
    "created" : string // ISO 8601
```