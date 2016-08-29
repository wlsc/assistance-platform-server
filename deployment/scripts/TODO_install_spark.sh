#!/bin/sh

sudo apt-get update
sudo apt-get install postgresql postgresql-contrib
sudo -i -u postgres
adduser assistance-postgres
createdb assistance-postgres
sudo -i -u assistance-postgres