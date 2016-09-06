Since the Assistance Platform uses Docker for deployment and virtualization management it is recommended that you "dockerize" also your modules. This document provides you with the necessary steps to do so.

# Maven Setup
At first you want to setup your maven project, especially in order for it to produce a FAT jar for Apache Spark. Therefore add the following plugin to your `pom.xml`. NOTE that you need to replace `YOUR_MAIN_CLASS` with the absolute name of the class that contains your main method (e.g. de.tudarmstadt.informatik.tk.assistanceplatform.drivecalm.module.DriveCalmModuleBundle).

```xml
			<!-- Maven Assembly Plugin -->
    <build>
        <plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-assembly-plugin</artifactId>
				<version>2.5</version>
				<configuration>
					<!-- get all project dependencies -->
					<descriptorRefs>
						<descriptorRef>jar-with-dependencies</descriptorRef>
					</descriptorRefs>
					<!-- MainClass in mainfest make a executable jar -->
					<archive>
						<manifest>
							<mainClass>YOUR_MAIN_CLASS</mainClass>
						</manifest>
					</archive>

				</configuration>
				<executions>
					<execution>
						<id>make-assembly</id>
						<!-- bind to the packaging phase -->
						<phase>package</phase>
						<goals>
							<goal>single</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
       </plugins>
  </build>
```

# Dockerize your module

## Dockerfile
You need to write a `Dockerfile` (yes, thats the exact name of the file) in order to dockerize your application. 

Create a file with the following contents, however note that you need to fill out the following three parameters:
* YOURMODULE - The name of your project. In order to find out the exact jar file name, just run `mvn package` in the Terminal.
* YOURMODULEID - The ID for your bundle specified in your `ModuleBundle` implementation
* YOUR_MAIN_CLASS - Like in maven the absolute name of your main class (e.g `de.tudarmstadt.informatik.tk.assistanceplatform.drivecalm.module.DriveCalmModuleBundle`)

```
FROM maven:3-jdk-8

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src /code/src
RUN ["mvn", "package"]
RUN ["cp","target/YOURMODULE-jar-with-dependencies.jar","/root/YOURMODULEID.jar"]

ENTRYPOINT ["mvn","exec:java","-D","exec.mainClass=YOUR_MAIN_CLASS"]
```

## Build docker image
Now `cd` into your module directory (which contains the `Dockerfile`). 
Then run: `docker build -t (DOCKERHUB_USERNAME/)YOUR_MODULE_NAME .`

This will create the respective docker image. If you want to publish to an remote repository you need to adjust the `-t` parameter respectively. 

## Run docker image
Now you can run an instance of your module by typing `docker run YOUR_MODULE_NAME`. Use `docker run -d YOUR_MODULE_NAME` to dettach from output (for background process). 

**Advice:** If there are multiple modules running on the same machine, then pass the Module-REST-Port as argument to the main method via `-Dexec.args`. You can also do so for the IP.