#!/usr/bin/env bash

VERSION=0.2.1
SHADED_JAR=coupon-prototype-${VERSION}-shaded.jar

if [ -e target/${SHADED_JAR} ]; then
    java -jar target/${SHADED_JAR} $*
else
    echo -e "${SHADED_JAR} not found, aborting.\nRun maven to compile and package shaded jar."
fi