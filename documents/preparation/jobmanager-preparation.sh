#!/bin/bash

# connect
ssh -p 10012 centos@openstack.d3s.mff.cuni.cz -i .ssh/skalicky-default.pem

# update all packages
sudo yum -y update

# install needed software
source java-install.sh
source tomcat-install.sh

# format big volume
sudo mkfs.ext4 /dev/vdb

# mount volume
sudo mkdir /media/simulationdrive
mount /dev/vdb /media/simulationdrive

# install samba and share simulationdrive
source tomcat-install.sh

# start-up script
sudo sh -c 'echo "

export JAVA_HOME=/opt/jdk1.8.0_40
export JRE_HOME=/opt/jdk1.8.0_40/jre

export TOMCAT_HOME=/opt/apache-tomcat-8.0.20

export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin:$TOMCAT_HOME/bin

java -jar /home/centos/jobmanager/cloudsimulator.jobmanager-0.0.1-SNAPSHOT.jar
">>/etc/rc.d/rc.local'
sudo chmod +x /etc/rc.d/rc.local
