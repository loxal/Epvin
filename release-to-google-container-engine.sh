#!/usr/bin/env bash

# Set `<vm>true</vm>` in appengine-web.xml

# Switch Java JDK
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

mvn clean validate compile gwt:compile gcloud:deploy
