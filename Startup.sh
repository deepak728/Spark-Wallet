#!/usr/bin/env bash
set -x

TOMCAT_VERSION=$1
TOMCAT_SUB_VERSION=$2
SERVICE_NAME=$3

#start the tomcat server via catalina.sh

sh /apps/tomcat${TOMCAT_SUB_VERSION}.${SERVICE_NAME}/bin/catalina.sh run