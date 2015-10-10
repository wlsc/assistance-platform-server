#!/bin/sh

sudo apt-get update
sudo apt-get install postgresql postgresql-contrib
adduser assistance-postgres
sudo su -c "createuser assistance-postgres" postgres
sudo su -c "createdb assistance-postgres" postgres