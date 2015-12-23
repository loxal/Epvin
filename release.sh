#!/usr/bin/env bash

# Switch to Java 7 JDK
export JAVA_HOME=$(/usr/libexec/java_home -v 1.7)
mvn clean compile gwt:clean gwt:compile test appengine:update

#gcloud config set project epvin-loxal
#mvn clean validate gcloud:deploy # gcloud (managed / unmanaged)