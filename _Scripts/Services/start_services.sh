#!/bin/sh

activemq start
sh start_postgres.sh
sh start_cassandra.sh