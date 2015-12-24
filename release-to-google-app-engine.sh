#!/usr/bin/env bash

# Set `<vm>false</vm>` in appengine-web.xml

# Switch Java JDK
export JAVA_HOME=$(/usr/libexec/java_home -v 1.7)

mvn clean package appengine:update
