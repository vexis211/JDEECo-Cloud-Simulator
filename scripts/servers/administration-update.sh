#!/bin/bash

tomcat_name=apache-tomcat-8.0.20

# copy new war
sudo rm /opt/${tomcat_name}/webapps/jdeeco-simulator-administration.war
sudo mv ~/jdeeco-simulator-administration.war /opt/${tomcat_name}/webapps/

# restart
sudo /opt/${tomcat_name}/bin/shutdown.sh
sudo /opt/${tomcat_name}/bin/startup.sh