@echo off

set VERSION=0.0.1
set SHADED_JAR=coupon-prototype-%VERSION%-shaded.jar
set ABS_PATH=%~dp0target\%SHADED_JAR%

if exist "%ABS_PATH%" (
    java -jar %ABS_PATH% %*
) else (
   echo %SHADED_JAR% not found, aborting.
   echo Run maven to compile and package shaded jar.
)