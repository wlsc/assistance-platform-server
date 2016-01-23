#!/bin/sh

docker run \
--name postgres \
--publish 5432:5432 \
-d \
--restart=unless-stopped \
--volume /var/lib/assistance/postgresql/data:/var/lib/postgresql/data \
postgres
