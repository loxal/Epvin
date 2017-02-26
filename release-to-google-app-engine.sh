#!/usr/bin/env bash

# Set `<vm>false</vm>` in appengine-web.xml

mvn clean compile gwt:compile appengine:update
