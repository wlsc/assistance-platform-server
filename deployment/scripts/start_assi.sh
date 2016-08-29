#!/bin/sh

activemq="127.0.0.1"
activemqport="61616"
db="127.0.0.1"
dbport="5432"
cassandra="127.0.0.1"

if [ ! -z $1 ]
then
    activemq="$1"
fi

if [ ! -z $2 ]
then
    activemqport="$2"
fi

if [ ! -z $3 ]
then
    db="$3"
fi

if [ ! -z $4 ]
then
    dbport="$4"
fi

if [ ! -z $5 ]
then
    cassandra="$5"
fi

echo $activemq
echo $activemqport
echo $db
echo $dbport
echo $cassandra

docker run \
--name='assistanceplatform' \
--restart=unless-stopped \
-p 9000:9000 \
-e ACTIVEMQ_PORT_61616_TCP_ADDR=$activemq \
-e ACTIVEMQ_PORT_61616_TCP_PORT=$activemqport \
-e DB_PORT_5432_TCP_ADDR=$db \
-e DB_PORT_5432_TCP_PORT=$dbport \
-e CASSANDRA_PORT_9042_TCP_ADDR=$cassandra \
-d \
m156/assistance-platform-service \
-Dconfig.resource=docker.conf
