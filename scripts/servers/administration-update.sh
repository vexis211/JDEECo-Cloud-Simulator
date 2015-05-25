#!/bin/bash

tomcat_name=apache-tomcat-8.0.20

# stop
sudo /opt/${tomcat_name}/bin/shutdown.sh

# copy new war
sudo rm /opt/${tomcat_name}/webapps/jdeeco-simulator-administration.war
sudo rm -r /opt/${tomcat_name}/webapps/jdeeco-simulator-administration
sudo mv ~/administration/jdeeco-simulator-administration.war /opt/${tomcat_name}/webapps/

# start
sudo /opt/${tomcat_name}/bin/startup.sh