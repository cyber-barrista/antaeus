#!/bin/bash

set -e

JAR_PATTERN="pleo-antaeus-app-*-all.jar"
JAR_FILE=$(find /opt/antaeus -name "${JAR_PATTERN}" | tail -n1)

if [ "${JAR_FILE}" = "" ]
then
    printf "Jar file is not found \n"
    exit 1
fi

exec java -jar "${JAR_FILE}"
