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
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_loudness (
	loudness float,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);
CREATE TYPE location (
	longitude double,
	latitude double,
	accuracy double
);

CREATE TABLE sensor_labelaction (
	UUID uuid,
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
	id uuid,
	device_id bigint,
	user_id bigint,
	timestamp timestamp,
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), UUID)
);

CREATE TABLE sensor_mobileconnection (
	carriername text,
	mobileCountryCode text,
	mobileNetworkCode text,
	voipAvailableOptional boolean,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
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
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_connectionstatus (
	isWifi boolean,
	isMobile boolean,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
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
	servertimestamp timestamp,
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
	servertimestamp timestamp,
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
	servertimestamp timestamp,
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
	servertimestamp timestamp,
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
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp, appName, packageName, className)
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
	servertimestamp timestamp,
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
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp, appName)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_light (
	value float,
	accuracy int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
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
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_ringtone (
	mode int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_runningprocess (
	name text,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp, name)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_runningtask (
	name text,
	stackPosition int,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp, name)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_runningservice (
	packageName text,
	className text,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp, packageName, className)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_powerlevel (
	percent float,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	servertimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TYPE alarm (
	type int,
	defaultOffset boolean,
	offsetOptional int,
	absoluteDateOptional text,
	proximityOptional int,
	locationTitleOptional text,
	locationLatitudeOptional double,
	locationLongitudeOptional double,
	locationRadiusOptional double
);

CREATE TABLE sensor_calendar (
	eventId text,
	calendarId text,
	allDay boolean,
	availability int,
	description text,
	startDate timestamp,
	endDate timestamp,
	location text,
	status int,
	title text,
	recurrenceRule text,
	alarms set<frozen<alarm>>,
	isDeleted boolean,
	created text,
	URLOptional text,
	isDetachedOptional boolean,
	lastModifiedDateOptional text,
	durationOptional text,
	originalAllDayOptional boolean,
	originalIdOptional text,
	originalInstanceTimeOptional bigint,
	recurrenceExceptionDateOptional text,
	recurrenceExceptionRuleOptional text,
	lastDateOptional bigint,
	recurrenceDateOptional text,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	serverTimestamp timestamp,
	PRIMARY KEY ((user_id, device_id), eventId, calendarId)
);

CREATE TABLE sensor_facebooktoken (
	oauthToken text,
	permissions set<text>,
	declinedPermissions set<text>,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	serverTimestamp timestamp,
	PRIMARY KEY (user_id, timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TABLE sensor_tucancredentials (
	username text,
	password text,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	serverTimestamp timestamp,
	PRIMARY KEY (user_id, timestamp)
) WITH CLUSTERING ORDER BY(timestamp DESC);

CREATE TYPE labeledvalue (
	label text,
	value text
);

CREATE TABLE sensor_contact (
	globalContactId text,
	givenName text,
	familyName text,
	note text,
	isDeleted boolean,
	displayNameOptional text,
	starredOptional int,
	lastTimeContactedOptional int,
	timesContactedOptional int,
	contactTypeOptional int,
	namePrefixOptional text,
	middleNameOptional text,
	previousFamilyNameOptional text,
	nameSuffixOptional text,
	nicknameOptional text,
	phoneticGivenNameOptional text,
	phoneticMiddleNameOptional text,
	phoneticFamilyNameOptional text,
	organizationNameOptional text,
	departmentNameOptional text,
	jobTitleOptional text,
	phoneNumbersOptional set<frozen<labeledvalue>>,
	emailAddressesOptional set<frozen<labeledvalue>>,
	device_id bigint,
	user_id bigint,
	id uuid,
	timestamp timestamp,
	serverTimestamp timestamp,
	PRIMARY KEY ((user_id, globalContactId))
);