#!/bin/sh

docker run \
  --restart=always \
  --volume=/:/rootfs:ro \
  --volume=/var/run:/var/run:rw \
  --volume=/sys:/sys:ro \
  --volume=/var/lib/docker/:/var/lib/docker:ro \
  --publish=8088:8080 \
  --detach=true \
  --name=cadvisor \
  google/cadvisor:latest
