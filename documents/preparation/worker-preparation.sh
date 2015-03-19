#!/bin/bash

# connect
ssh -p 10012 centos@openstack.d3s.mff.cuni.cz -i .ssh/skalicky-default.pem

# update all packages
sudo yum update

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