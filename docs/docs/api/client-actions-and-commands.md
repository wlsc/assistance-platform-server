### Description
A set of actions that server could send to a client to run particular task/tasks to control the client device.

#### Types overview

* VOLUME_UP - raises the volume
* VOLUME_DOWN - lowers the volume
* BRIGHTNESS_UP - increases display brightness
* BRIGHTNESS_DOWN - decreases display brightness
* VIBRATE - vibration for a certain time

#### Type: VOLUME_UP / VOLUME_DOWN

```javascript
{
	"type" : "VOLUME_UP",
	"amount" : float,  // range 0.0-1.0, in percent
	"duration" : long  // in milliseconds
}
```

#### Type: BRIGHTNESS_UP / BRIGHTNESS_DOWN

```javascript
{
	"type" : "BRIGHTNESS_UP",
	"amount" : float,  // range 0.0-1.0, in percent
	"duration" : long  // in milliseconds
}
```

#### Type: VIBRATE

```javascript
{
	"type" : "VIBRATE",
	"duration" : long  // in milliseconds
}
```