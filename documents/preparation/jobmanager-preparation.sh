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