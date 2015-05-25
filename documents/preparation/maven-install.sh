#!/bin/bash

# install maven

maven_version=3.3.3

cd /opt/

sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://mirrors.gigenet.com/apache/maven/maven-3/${maven_version}/binaries/apache-maven-${maven_version}-bin.tar.gz"
sudo tar xzf apache-maven-${maven_version}-bin.tar.gz

sudo rm apache-maven-${maven_version}-bin.tar.gz