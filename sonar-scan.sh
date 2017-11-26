#!/bin/bash
#
# SonarQube Scanner with Code Coverage for Maven
#

echo -e "Running Maven...\n"
mvn clean
mvn validate
mvn package

echo -e "Running sonar-scanner...\n"
sonar-scanner