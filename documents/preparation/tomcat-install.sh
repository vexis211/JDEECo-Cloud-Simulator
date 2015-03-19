#!/bin/bash

# install tomcat 8 - http://tecadmin.net/steps-to-install-tomcat-server-on-centos-rhel/
cd /opt/
sudo wget http://apache.bytenet.in/tomcat/tomcat-8/v8.0.20/bin/apache-tomcat-8.0.20.tar.gz
sudo tar xzf apache-tomcat-8.0.20.tar.gz

# set environment variables
# sudo nano /etc/profile.d/tomcat.sh
# export TOMCAT_HOME=/opt/apache-tomcat-8.0.20
# export PATH=$PATH:$TOMCAT_HOME/bin
# close nano
# variables will be loaded after restart, to load it now type: source /etc/profile.d/tomcat.sh


sudo sh -c 'echo "export TOMCAT_HOME=/opt/apache-tomcat-8.0.20
export PATH=$PATH:$TOMCAT_HOME/bin"\
>/etc/profile.d/tomcat.sh'

source /etc/profile.d/tomcat.sh

# clean installers
sudo rm /opt/apache-tomcat-8.0.20.tar.gz

