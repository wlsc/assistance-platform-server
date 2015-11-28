CREATE KEYSPACE assistancedata WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE sensor_motionactivity (
	walking int,
	running int,
	driving int,
	stationary int,
	unknown int,
	onFootOptional int,
	tiltingOptional int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_loudness (
	loudness float,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);
CREATE TYPE location (
	longitude double,
	latitude double,
	accuracy double
);

CREATE TABLE sensor_labelaction (
	UUID text,
	startTime timestamp,
	endTime timestamp,
	startLocation frozen<location>,
	endLocation frozen<location>,
	tags set<text>,
	label text,
	labelClickTime bigint,
	labelClickIndex int,
	labelFirstTime text,
	labelTime text,
	isStationary boolean,
	isModified boolean,
	isRemoved boolean,
	isUserCreated boolean,
	referenceSplitUUID text,
	referenceMergeUUID text,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_mobileconnection (
	carriername text,
	mobileCountryCode text,
	mobileNetworkCode text,
	voipAvailableOptional boolean,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_gyroscope (
	x double,
	y double,
	z double,
	xUncalibratedNoDriftOptional float,
	yUncalibratedNoDriftOptional float,
	zUncalibratedNoDriftOptional float,
	xUncalibratedEstimatedDriftOptional float,
	yUncalibratedEstimatedDriftOptional float,
	zUncalibratedEstimatedDriftOptional float,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_connectionstatus (
	isWifi boolean,
	isMobile boolean,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_magneticfield (
	x double,
	y double,
	z double,
	xUncalibratedNoHardIronOptional float,
	yUncalibratedNoHardIronOptional float,
	zUncalibratedNoHardIronOptional float,
	xUncalibratedEstimatedIronBiasOptional float,
	yUncalibratedEstimatedIronBiasOptional float,
	zUncalibratedEstimatedIronBiasOptional float,
	accuracyOptional int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_position (
	latitude double,
	longitude double,
	accuracyHorizontal double,
	speed double,
	altitudeOptional double,
	accuracyVerticalOptional double,
	courseOptional int,
	floorOptional int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_wificonnection (
	ssid text,
	bssid text,
	channelOptional int,
	frequencyOptional int,
	linkSpeedOptional int,
	signalStrenghtOptional int,
	networkIdOptional int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_accelerometer (
	x double,
	y double,
	z double,
	accuracyOptional int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_foreground (
	packageName text,
	appName text,
	className text,
	activityLabel text,
	color text,
	url text,
	eventType text,
	keystrokes int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_call_log (
	callId bigint,
	callType int,
	name text,
	number text,
	date bigint,
	duration bigint,
	isNew boolean,
	isUpdated boolean,
	isDeleted boolean,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_networktraffic (
	appName text,
	rxBytes bigint,
	txBytes bigint,
	background boolean,
	longitudeOptional double,
	latitudeOptional double,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp, appName)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_light (
	value float,
	accuracy int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_powerstate (
	isCharging boolean,
	percent float,
	charingStateOptional int,
	charingModeOptional int,
	powerSaveModeOptional boolean,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);