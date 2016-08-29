#!/bin/sh

docker run \
--name='activemq' \
-d \
-p 61616:61616 \
--restart=unless-stopped \
webcenter/activemq:latest
