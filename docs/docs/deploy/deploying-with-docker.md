# Motivation
In some cases one might to have an isolated "Assistance Platform" instance for your own. Theses could be for example:
* Testing own modules
* Testing custom clients
* Run integration tests

This document will provide you with the needed information on how to set up such an isolated instance.

# Prerequisites 
We tried making these steps as easy as possible by using [Docker](http://docker.com/). In order to understand Docker and the available possibilities, you should also check out the [Docker User Guide](http://docs.docker.com/v1.8/userguide/). This page also contains instructions how to install Docker on your machine.

CURRENTLY you need to send a mail with your Docker Hub username to bennet.jeutter@gmail.com. This'll change in the future (own dedicated repository). [TODO]

# Deploying, stopping and removing
## Deploying an isolated "Assistance Platform"
1. Checkout the [Assistance Platform Docker Environment Description](https://github.com/Telecooperation/assistance-platform-server/tree/master/deployment/docker)
2. `cd` into your `docker` folder
3. run `docker-compose up`

Finish! You now have your own "Assistance Platform" instance running. 

## Stop the instance
In order to stop the "Assistance Platform" just use `docker-compose stop`. This will shut down the Docker containers and "freeze" them. The next time you'll fire `docker-compose up` they will be un-freezed and started again.

## Remove the instance
Sometimes you might want to start a completely fresh instance (purge the old data etc.). Therefore you should stop the instance (`docker-compose stop`) and then run `docker-compose rm`. NOTE that this will be permanent and all persisted data  until then will be lost!

# Testing against your isolated instance
On some machines (OSX and windows) Docker will spawn a VM for you. You will probably need the IP of this machine. Therefore type: `docker-machine ls` which will give you the IP(s) of your VM(s). Use the right own (usually default).

On Linux "localhost" should be fine.

# Limitations for module developers
Currently the Apache Spark Module is NOT working properly if trying connecting your module form outside the Docker machine, since the Spark Master can only be accessed from the inside. In order to test your Apache Spark implementations, you can activate the so called ["local mode"](../developer/Module-Developers.-1.-Getting-started-for-Module-Developers-(WIP)#fire-this-thing-up).

# Deploying in (quasi) production mode for standalone servers
In order to keep data safe and allow automatic restarts after reboot the production config should be used. Therefore use:
`sudo docker-compose -f docker-compose.yml -f docker-compose.prod.yml up`