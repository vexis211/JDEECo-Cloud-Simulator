#!/bin/bash

# connect
ssh -p 10012 centos@openstack.d3s.mff.cuni.cz -i .ssh/skalicky-default.pem

# update all packages
sudo -y yum update

# install nano - vim is too big
sudo yum -y install nano

# install needed software
source java-install.sh

# install and set samba client
sudo yum -y install samba-client
# needed to connect
sudo yum -y install cifs-utils

# mount samba drive
sudo mkdir /media/simulationdrive
sudo sh -c 'echo "//10.50.0.16/simulationdrive  /media/simulationdrive  cifs  guest,uid=1000,iocharset=utf8  0  0" >> /etc/fstab'
sudo mount -a

# start-up script
sudo sh -c 'echo "

export JAVA_HOME=/opt/jdk1.8.0_40
export JRE_HOME=/opt/jdk1.8.0_40/jre

export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin

java -jar /home/centos/worker/cloudsimulator.worker-0.0.1-SNAPSHOT.jar
">>/etc/rc.d/rc.local'
sudo chmod +x /etc/rc.d/rc.local

cd ~
mkdir worker