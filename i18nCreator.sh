#!/usr/bin/evn sh

APPDIR=`dirname $0`;
java -cp "$APPDIR/src/main/java:$APPDIR/bin:${GWT_HOME}/gwt-user.jar:${GWT_HOME}/gwt-dev.jar" com.google.gwt.i18n.tools.I18NSync \
    -out $APPDIR/src/main/java loxal.lox.service.meta.client.meta.layout.I18n;

#newer i18nCreator version
#HOMEDIR=`dirname $0`;
#java -cp $HOMEDIR/gwt-user.jar:$HOMEDIR/gwt-dev.jar com.google.gwt.i18n.tools.I18NCreator "$@";