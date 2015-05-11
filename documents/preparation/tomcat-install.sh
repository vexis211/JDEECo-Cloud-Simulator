#!/bin/bash

# install tomcat 8 - http://tecadmin.net/steps-to-install-tomcat-server-on-centos-rhel/
tomcat_name=apache-tomcat-8.0.20

cd /opt/
sudo wget http://apache.bytenet.in/tomcat/tomcat-8/v8.0.20/bin/${tomcat_name}.tar.gz
sudo tar xzf ${tomcat_name}.tar.gz

# set environment variables
# sudo nano /etc/profile.d/tomcat.sh
# export TOMCAT_HOME=/opt/${tomcat_name}
# export PATH=$PATH:$TOMCAT_HOME/bin
# close nano
# variables will be loaded after restart, to load it now type: source /etc/profile.d/tomcat.sh

# clean installers
sudo rm /opt/${tomcat_name}.tar.gz


#sudo /opt/${tomcat_name}/bin/shutdown.sh
#sudo /opt/${tomcat_name}/bin/startup.sh
# ssh -L 8080:localhost:8080 centos@openstack.d3s.mff.cuni.cz -p 10011 -i .ssh/skalicky-default.pem -N -v
# sudo mv ~/jdeeco-simulator-administration.war /opt/${tomcat_name}/webapps/


# mysql
sudo yum install http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm
sudo yum install mysql-community-server
sudo systemctl start mysqld
# for restart:
# sudo systemctl restart mysqld

#mysql -u root -p <20150501_01-First\ deployment.sql
