#!/bin/bash

# install java 1.8 - http://tecadmin.net/install-java-8-on-centos-rhel-and-fedora/
sudo yum -y install wget
cd /opt/
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u40-b25/server-jre-8u40-linux-x64.tar.gz"
sudo tar xzf server-jre-8u40-linux-x64.tar.gz

cd /opt/jdk1.8.0_40/
sudo alternatives --install /usr/bin/java java /opt/jdk1.8.0_40/bin/java 2
#sudo alternatives --config java

sudo alternatives --install /usr/bin/jar jar /opt/jdk1.8.0_40/bin/jar 2
sudo alternatives --install /usr/bin/javac javac /opt/jdk1.8.0_40/bin/javac 2
sudo alternatives --set jar /opt/jdk1.8.0_40/bin/jar
sudo alternatives --set javac /opt/jdk1.8.0_40/bin/javac

# clean installers
sudo rm /opt/server-jre-8u40-linux-x64.tar.gz

