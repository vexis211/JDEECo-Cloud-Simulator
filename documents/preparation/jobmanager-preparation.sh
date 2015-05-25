#!/bin/bash

# launch instance from image
# flavour: 4.8096 

# connect
ssh -p 10011 centos@openstack.d3s.mff.cuni.cz -i .ssh/skalicky-default.pem

# update all packages
sudo yum -y update

# install nano - vim is too big
sudo yum -y install nano

# install needed software
source java-install.sh
source maven-install.sh
source tomcat-install.sh

# format big volume
sudo mkfs.ext4 /dev/vdb

# mount volume
sudo mkdir /media/simulationdrive
mount /dev/vdb /media/simulationdrive
sudo chmod 777 /media/simulationdrive

# install samba and share simulationdrive
source samba-server-install.sh

# start-up script
sudo sh -c 'echo "

export JAVA_HOME=/opt/jdk1.8.0_40
export JRE_HOME=/opt/jdk1.8.0_40/jre

export M2_HOME=/opt/apache-maven-3.3.3

export TOMCAT_HOME=/opt/apache-tomcat-8.0.20

export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin:$M2_HOME/bin:$TOMCAT_HOME/bin

">>/etc/rc.d/rc.local'
sudo chmod +x /etc/rc.d/rc.local

cd ~
mkdir jobmanager

# mysql --user=root --password= --database=jdeeco_simulation

# java -jar /home/centos/jobmanager/cloudsimulator.jobmanager-0.0.1-SNAPSHOT.jar
