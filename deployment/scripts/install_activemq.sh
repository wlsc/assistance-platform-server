#!/bin/sh

# Add activemq user
useradd -m activemq -d /opt/activemq

# Download and extract activemq
cd /opt/activemq
wget http://www.apache.org/dyn/closer.cgi?filename=/activemq/5.14.0/apache-activemq-5.14.0-bin.tar.gz&action=download
tar zxvf apache-activemq-5.14.0-bin.tar.gz
rm apache-activemq-5.14.0-bin.tar.gz

# Symlink for current version
ln -snf apache-activemq-5.14.0 current

# Set rights for the activemq folder
chown -R activemq:users apache-activemq-5.14.0

# Prepare settings for activemq
cp current/bin/env /etc/default/activemq
sed -i '~s/^ACTIVEMQ_USER=""/ACTIVEMQ_USER="activemq"/' /etc/default/activemq
vim /etc/default/activemq
chmod 644 /etc/default/activemq

# Put activemq to services so it starts auto after reboot
ln -snf /opt/activemq/current/bin/activemq /etc/init.d/activemq