#!/bin/sh

docker run -d -p 9100:9100 --name node-exporter --restart=always --net="host" prom/node-exporter
