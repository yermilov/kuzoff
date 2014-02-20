#!/bin/bash

if [ -z "$CATALINA_HOME" ] 
then
    echo CATALINA_HOME must be specified
    exit 1
fi

echo CATALINA_HOME=$CATALINA_HOME

sh "$CATALINA_HOME/bin/shutdown.sh"

rm -rf "$CATALINA_HOME/webapps/kuzoff-http"
rm -f "$CATALINA_HOME/webapps/kuzoff-http.war"

cp build/libs/kuzoff-http.war "$CATALINA_HOME/webapps"
