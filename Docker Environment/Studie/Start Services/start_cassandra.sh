#!/bin/sh

broadcast="127.0.0.1"

if [ ! -z $1 ]
then
    broadcast="$1"
fi

seeds=$broadcast

if [ ! -z $2 ]
then
    seeds="$2"
fi

docker run \
--name cassandra \
--restart=unless-stopped \
--publish 7000:7000 \
--publish 9042:9042 \
--publish 9160:9160 \
--volume "/etc/assistance/cassandra:/etc/cassandra" \
-e CASSANDRA_BROADCAST_ADDRESS=$broadcast \
-e CASSANDRA_SEEDS=$seeds \
-d \
cassandra
