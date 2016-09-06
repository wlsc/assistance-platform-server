This document will provide you with all basic infos you need to get started with developing a module for the "Assistance Platform".

# Introduction

## Assistance platform
The Assistance Platform is a project which aims on providing assistance to the user in all thinkable situations. For example notify the user in certain situations or set his phone to silent mode in a meeting. BUT the Assistance Platform itself isn't that intelligent in the first place. It basically "just" offers a nice technical interface for clients to push and pull data. The real value comes form so called "modules", see the following section.## 

## Module
A module is some sort of extension of the "Assistance Platform". It should implement assistance logic (when to notify the user, when to send a mail etc.) but it can also implement the extraction of higher-level information from a stream of user / device events. So as a module developer you will implement an "Assistance Module" and (optionally) a "Data (Processing) Module". Multiple Modules (normally one Assistance and one Data Module) are put together in a so called "Module Bundle". More of that later.

## Basic workflow / communication ways
The platform does collect necessary data from registered (and active) user. For example it tracks the position and app usage. Furthermore users can activate (and deactivate) modules to support the user. 

### Proactive assistance
All activated modules will be notified by the platform about the users events (e.g. positions). Data Modules then are able to receive and process these events and gain higher level events. Assistance Modules then can react on those higher level events and tell the platform to trigger some action on the client device(s), e.g. send a notification, set display brightness. 

### Reactive assistance
A user can always request current assistance information from the platform. The platform will then ask all activated modules (for this particular user) if they have relevant information. The responses of the modules will be collected, prioritized and sent back to the requesting user who then can inspect the offered assistance. A module can specifiy to show text, images, maps, links, etc.. These information are organized in so called "Information Cards". Each module can provide such a "Information Card" which is then presented to the user, alongside the "Information Cards" of other modules (if any).

### Overview Platform
Here you find an very simplified image over the interactions between clients, platform and modules.

![](https://github.com/Telecooperation/server_platform_assistance/blob/master/docs/images/PlatformOverview.png)

# Getting started!
So now you should be able to somehow understand the concept and you want to implement your own module, what to do? This guide will show you how to implement a "Drive calm" module. It should measure the speed of a user and proactively warn him if he is going faster than 100km/h. The reactive assistance should show him how fast he is going.

### Hint: You don't like tutorials and want to immediately get hands on working code?
Check out the repository and have a look at the "DriveCalmModule" project. [TODO ggf. Link einfügen und so]

## Prerequisites
Please make sure you have the following things installed on your developer machine:
* JDK8
* Appropriate Java IDE (e.g. Eclipse)
* Maven

## Create your project
Create a new maven project and give it the name "DriveCalmModule". Then add the following dependency to your pom.xml:
```xml
<dependency>
  <groupId>de.tudarmstadt.informatik.tk.assistanceplatform</groupId>
  <artifactId>AssistancePlatformModuleAPI</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Furthermore add the following repo to your pom.xml:
```xml
	<repositories>
		<repository>
			<releases>
				<enabled>false</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>warn</checksumPolicy>
			</releases>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>never</updatePolicy>
				<checksumPolicy>fail</checksumPolicy>
			</snapshots>
			<id>assi-nexus</id>
			<name>Assi Nexus</name>
			<url>http://130.83.163.56:9001/content/repositories/snapshots</url>
			<layout>default</layout>
		</repository>
	</repositories>
```

## What to implement first?
So as a module developer your are extending a very specific family of classes and therewith specify your "Module Bundle". So the starting point for your module will be the extension of the `ModuleBundle` class:
```java
package de.tudarmstadt.informatik.tk.assistanceplatform.drivecalm.module;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.Module;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundle;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.bundle.ModuleBundleInformation;

public class DriveCalmModuleBundle extends ModuleBundle {

	@Override
	public String getModuleId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModuleBundleInformation getBundleInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Module[] initializeContainedModules() {
		// TODO Auto-generated method stub
		return null;
	}
}
```

So what you will need to implement is:
* `getModuleId()` - Just return the ID of your module, make sure that it is unique.
* `getBundleInformation()` - Here you can specify the bundle information. These are mainly needed for presenting relevant information to the end-user.
* `initializeContainedModules()` - This is the most important part. Here you'll construct the contained Assistance and Data Module(s)

Lets begin with the super-simple `getModuleId` method:
```java
private static final String ID = "de.tudarmstadt.informatik.tk.assistanceplatform.drivecalm";

@Override
public String getModuleId() {
    return ID;
}
```

Then implement the `getBundleInformation()` method like following:
```java
@Override
public ModuleBundleInformation getBundleInformation() {
	String name = "Drive calm";
	String logoUrl = "http://urltologo";
	String shortDescription = "Helps you maintaining a moderate speed limit while driving";
	String longDescription = "This module will measure your speed and warn you if you go faster then 100km/h. In the request-assistance mode you can view your current speed.";
	String copyright = "Copyright by YOU 2015";
	String devMail = "youremail@adress.com";
	String supportMail = devMail;

	Capability[] requiredCapabilities = new Capability[] { new Capability(
			"position", 1, 2) };

	Capability[] optionalCapabilites = new Capability[] { new Capability(
			"motionactivity", 1, 2) };

	ModuleBundleInformation info = new ModuleBundleInformation(name,
			logoUrl, shortDescription, longDescription,
			requiredCapabilities, optionalCapabilites, copyright, devMail,
			supportMail);
		
	return info;
}
```

What did happen here? We declared a `ModuleBundleInformation` object which contains name, description, etc.. From the technical point of view the **most important thing here is the definition of the so called "Capabilities"**. 

With capabilities you define the requirements that your module needs from the users devices. For the required capabilities we defined the following parameters:
* `"position"` - Event Type - This is the name of the sensor / event we need from the users device. In this case long / lat position events.
* `1` - Collection Interval - We want the device to collect these evens in an interval of 1 second and store them locally.
* `2` - Required update interval - We want to get the events delivered at least in an interval of 2 seconds

Beside the required capabilities a module can define optional capabilities, that it can use to improve the assistance quality. In the case above we request "motion activity" events, which can tell the probability that a user is driving.

Here you can find out which sensors / event types are available: [Sensor types and API mapping](./Sensor-Types-and-API-mapping)

The next step is implementing the `initializedContainedModules()` method:
```java
@Override
protected Module[] initializeContainedModules() {
	Module assistanceModule = new DriveCalmAssistanceModule();
	Module dataModule = new DriveCalmDataModule();
		
	return new Module[] {
		assistanceModule,
		dataModule
	};
}
```

You just instantiate your modules and return them combined in an array. But wait... we don't have these classes yet? Yes, right, lets create them!

# Implementing the modules

## Data Module
The goal of the data module is to detect if the user is going faste than 100km/h and firing a respective event, so the assistance module can react on this. The raw skeleton will look like this:
```java
package de.tudarmstadt.informatik.tk.assistanceplatform.drivecalm.module;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.DataModule;

public class DriveCalmDataModule extends DataModule {

	@Override
	protected void doBeforeStartup() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doAfterStartup() {
		// TODO Auto-generated method stub
	}
}
```

We will need to implement the `doBeforeStartup` method. The `doAfterStartup` method can be left blank.

The `doBeforeStartup` method can be used to register a event listener for specific type of events (e.g. position). It can also be used to start specific algorithms or a Apache Spark application.

Here is the plan for our simple module:
* Listen for positions events
* Store the last event of a user in a local hashmap
* On each new event calculate the traveled distance between the two positions
* Therewith calculate the speed and fire a `DriveCalmRecommendationEvent`

Here is an example implementation:
```java
	private HashMap<Long, Position> lastUserPositions = new HashMap<>();

	private static final double kmPerHourThreshold = 100;

	@Override
	protected void doBeforeStartup() {
		this.registerForEventOfType(Position.class, this::consumeEvent);
	}

	private void consumeEvent(Channel<Position> channel, Position newEvent) {
		Long userId = newEvent.userId;

		// Try getting the last user position
		Position lastPosition = getLastUserPosition(userId);

		// // If this isn't the first position then
		if (lastPosition == null) {
			// Create a new position entry for the current value
			setLastUserPosition(newEvent);
		} else {
			// // We have a last position, so we can calculate a distance
			// Check if the last position is older then the new position
			if (lastPosition.getTimestamp().before(newEvent.getTimestamp())) {
				// Difference based speed
				double calculatedSpeed = (lastPosition.distance(newEvent) / 1000.0)
						/ ((newEvent.getTimestamp().getTime() - lastPosition
								.getTimestamp().getTime()) / 1000.0 / 60. / 60.0);

				DriveCalmRecommendationEvent recommendationEvent = buildRecommendationEvent(newEvent, calculatedSpeed);
				
				if (calculatedSpeed > kmPerHourThreshold) {
					fireDriveCalmRecommendationForPosition(recommendationEvent);
				}

				// Mark this event as last position of user
				setLastUserPosition(newEvent);
			}
		}
	}

	private DriveCalmRecommendationEvent buildRecommendationEvent(Position pos, double speed) {
		DriveCalmRecommendationEvent event = new DriveCalmRecommendationEvent();
		event.userId = pos.userId;
		event.deviceId = pos.deviceId;
		event.speed = speed;
		event.timestamp = pos.timestamp;
		
		return event;
	}

	private void fireDriveCalmRecommendationForPosition(Position pos) {
		DriveCalmRecommendationEvent eventToFire = new DriveCalmRecommendationEvent();
		eventToFire.userId = pos.userId;
		this.emitEvent(eventToFire);
	}
```

The detection logic could have been simplified by just checking the `speed` attribute of the `Position` event, but I wanted to present a slightly more complex alternative.

Important is the `emitEvent` method. Therewith we can push events into the event stream so other modules (including our Assistance module) can receive the information. We'll fire a `DriveCalmRecommendationEvent` - extends `UserDeviceEvent` (implies user id and device id) and adds a `speed` field.
```java
package de.tudarmstadt.informatik.tk.assistanceplatform.drivecalm.module;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.UserDeviceEvent;

public class DriveCalmRecommendationEvent extends UserDeviceEvent {
	public double speed;

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
```

## AssistanceModule
So now we have the logic which detects the exceedance of 100km/h and then fires an respective event. We now want to write an "Assistance Module" which reacts to those `DriveCalmRecommendationEvent` events and warns the user. So lets start with an empty extension of the `AssistanceModule` class.
```java
package de.tudarmstadt.informatik.tk.assistanceplatform.drivecalm.module;

import java.util.Collection;

import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.AssistanceModule;
import de.tudarmstadt.informatik.tk.assistanceplatform.modules.assistance.informationprovider.IInformationCardCustomizer;
import de.tudarmstadt.informatik.tk.assistanceplatform.services.modulerestserver.MappedServlet;

public class DriveCalmAssistanceModule extends AssistanceModule {
	@Override
	protected void doBeforeStartup() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doAfterStartup() {
		// TODO Auto-generated method stub
	}

	@Override
	protected IInformationCardCustomizer generateInformationCardCustomizer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<MappedServlet> generateCustomServelets() {
		// TODO Auto-generated method stub
		return null;
	}
}
```

First lets go through the methods that can be implemented:
* `doBeforeStartup` - Again here you can register for events, like in data modules. We'll register a listener for `DriveCalmRecommendationEvent`s.
* `doAfterStartup` - E.g. start services. We'll leave it blank in this example.
* `generateInformationCardCustomizer` - Implement this if you want to provide a request-based assistance and provide so called "Information Cards" to the user. We'll provide the last known speed of the user here.
* `generateCustomServelets` - Can be used to implement a custom REST Webservice for custom Client-Apps. We'll leave this blank for now.

### Proactive assistance
Lets start with the event listener:
```java
	@Override
	protected void doBeforeStartup() {
		this.registerForEventOfType(DriveCalmRecommendationEvent.class,
				this::consumeDriveCalmRecommendation);
	}

	private void consumeDriveCalmRecommendation(
			Channel<DriveCalmRecommendationEvent> channel,
			DriveCalmRecommendationEvent event) {
		getActionRunner().showMessage(event.userId,
				new long[] { event.deviceId }, "Exceeded speed threshold!",
				"You are going " + event.speed + " km/h. Drive slower!");
	}
```

Interesting here is the `getActionRunner()` call. The action runner provides is a set of methods you can use to trigger actions on the client side, like showing a push message in this case. You just pass the receivers UserID + DeviceID(s) and set a title / message. The distribution to the devices will be handled by the platform. Now we have finished the proactive assistance - if the user is driving faster then 100km/h he will be presented with the specified message.

### Reactive assistance (using Cassandra persistency!)
When the user opens his client he wants to get the most up-to-date information from his activated modules. The abstraction you need to implement therefore is a implementation of `IInformationCardCustomizer` and instantiate it in the `generateInformationCardCustomizer` method. The implementation of the `IInformationCardCustomizer` interface defines the customization of the `ModuleInformationCard` which will be sent to the client.

#### Preparing the data module for saving events
In our case we'll just send back the current speed. But where to get that value from? Basically we calculate it in the Data Module, but we need to save the last speed somehow to make it available at request from the client. So at first we'll want to extend the Data Module code. We need to add the following steps:
* Create the required Cassandra Table Schema on startup.
* On each position change save the calculated speed.

So here are the modifications to the data module:
```java
	@Override
	protected void doBeforeStartup() {
		createSchema(); // This is new!
		this.registerForEventOfType(Position.class, this::consumeEvent);
	}

	private void createSchema() { // This is new!
		String schemaCQL = "CREATE TABLE drive_calm_speeds "
				+ "(speed double, "
				+ "device_id bigint, "
				+ "user_id bigint, "
				+ "id uuid, "
				+ "timestamp timestamp, "
				+ "PRIMARY KEY (user_id)"
				+ ") ";

		CassandraServiceFactory.getSessionProxy().createSchema(schemaCQL);
	}
```

Next we add the save routine 
```java
	private void consumeEvent(Channel<Position> channel, Position newEvent) {
		Long userId = newEvent.userId;

		// Try getting the last user position
		Position lastPosition = getLastUserPosition(userId);

		// // If this isn't the first position then
		if (lastPosition == null) {
			// Create a new position entry for the current value
			setLastUserPosition(newEvent);
		} else {
			// // We have a last position, so we can calculate a distance
			// Check if the last position is older then the new position
			if (lastPosition.getTimestamp().before(newEvent.getTimestamp())) {
				// Difference based speed
				double calculatedSpeed = (lastPosition.distance(newEvent) / 1000.0)
						/ ((newEvent.getTimestamp().getTime() - lastPosition
								.getTimestamp().getTime()) / 1000.0 / 60. / 60.0);

				DriveCalmRecommendationEvent recommendationEvent = buildRecommendationEvent(newEvent, calculatedSpeed);
				
				if (calculatedSpeed > kmPerHourThreshold) {
					fireDriveCalmRecommendationForPosition(recommendationEvent);
				}
				
				// Save in any case the current speed (THIS IS NEW!)
				saveDriveCalmRecommendationForPosition(recommendationEvent);

				// Mark this event as last position of user
				setLastUserPosition(newEvent);
			}
		}
	}
	
        ...

	private void saveDriveCalmRecommendationForPosition(DriveCalmRecommendationEvent eventToFire) {
		this.getEventPersistency().persist(eventToFire);
	}
```

What does happen here? After calculating the `DriveCalmRecommendationEvent` we save it (despite the user exceeded the threshold or not). Therefore we use the `getEventPersistency()` method which provides a implementation of the `IUserDeviceEventPersistency` which provides a `persist(event)` method. This is a convenience method to save events. In order for this to work we need to add the following `@Table` annotation to the `DriveCalmRecommendationEvent` (it tells which table to map the class to):
```java
@Table(name = "drive_calm_speeds")
public class DriveCalmRecommendationEvent extends UserDeviceEvent {
...
}
```

#### Provide the last speed information on request
In the last section we saved the most current speed to the Cassandra Database. We now want to provide a possibility for the user / client to get the last speed. Therefore we implement the `generateInformationCardCustomizer` which provides a implementation of the `IInformationCardCustomizer` interface.
In order to keep it simple, we'll use an anonymous "inline" interface implementation here. Here are the basic steps:
* Prepare a Cassandra query to get the speed of a user
* On request (`customizeModuleInformationCard`) get the last speed of the database.
* Customize the card by setting the timestamp and payload (-> speed)

[TODO: Wenn Template / View Kram geklärt ist hier einbinden]

```java
	@Override
	protected IInformationCardCustomizer generateInformationCardCustomizer() {
		PreparedStatement ps = getCassandraSession().prepare("SELECT * FROM drive_calm_speeds WHERE user_id = ? LIMIT 1");
		
		return new IInformationCardCustomizer() {
			
			@Override
			public void customizeModuleInformationCard(ModuleInformationCard card,
					long userId, long deviceId) {
				ResultSet rs = getCassandraSession().execute(ps.bind(userId));
				
				Row row = rs.one();
				
				if(row != null) {
					// The timestamp should be set to represent the "up-to-dateness"
					card.timestamp = row.getTimestamp("timestamp");
					
					// Set the payload to the saved speed (this is the information which will be presented to the user)
					card.payload = Double.toString(row.getDouble("speed"));
				}
			}
		};
	}
```

The code should be straight forward. There are two important things: Note that the timestamp of the card is explicitly set, otherwise it'll be zero - that means, that it will be very unimportant for the user. Try to give a timestamp which is representative for the "currentness" of the module information. Another important thing to note is that the `getCassandraSession()` is provided by the API. So you get a preconfigured Cassandra Session with which you can work with :+1:. But always be sure to think of efficient indexing and querying of the Cassandra Database!

## Fire this thing up!
The last step to get this running is adding a main method. For the sake of simplicity we add it to the module bundle. Assuming you want to run the module against the default system just use:
```java
public static void main(String[] args) {
	ModuleBundle bundle = new DriveCalmModuleBundle();
	BundleBootstrapper.bootstrap(bundle);
}
```

This'll initialize everything for you and start the module.

In case you are running a local test system (e.g. via Docker [TODO Link einfügen]), then you can also pass a address of the platform, e.g.:
```java
public static void main(String[] args) {
	ModuleBundle bundle = new DriveCalmModuleBundle();
	boolean localMode = true;
	BundleBootstrapper.bootstrap(bundle, "ip:port", localMode);
}
```

Furthermore you see the declaration of localMode. This will deactivate the remote Apache Spark features and perform the respective computations on the locally available CPUs.

## Deployment
[TODO Jenkins usw. erklären]

### Apache Spark Requirements
If your data module uses Apache Spark as processing engine then you need to provide a "fat jar". This "fat jar" has to be saved in USER_HOME/{MODULE_ID}.jar on the machine which runs the Module Bundle.

## Room for improvements
* Prüfen wann letzte Nachricht gesendet wurde um Spam zu vermeiden
* Use user settings for the threshold

# Where to go from now?
To get an overview which data is available you should inspect the `de.tudarmstadt.informatik.tk.assistanceplatform.data` package which contains the sensor / event POJOs.

Furthermore you should inspect the provided `AssistanceModuleAPI` and especially the provided services.
* Client Action Runner
* Persistency
* Custom Servlets

If you want to develop complex modules you should deep dive into [Apache Spark](http://spark.apache.org/) and its [Docs](http://spark.apache.org/docs/latest/).