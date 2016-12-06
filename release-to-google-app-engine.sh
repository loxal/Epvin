#!/usr/bin/env bash

# Set `<vm>false</vm>` in appengine-web.xml

# Switch Java JDK
#export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

mvn clean compile gwt:compile appengine:deploy
