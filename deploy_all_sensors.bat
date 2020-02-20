@echo off

:: change to current directory
cd %cd%

:: ------ MOSQUITTO ------
:: set the mosquitto directory 
set EXEC_MOSQUITTO="%MOSQUITTO_DIR%\mosquitto.exe"

:: start process "mosquitto.exe"
START "" %EXEC_MOSQUITTO%

:: ------ API ------
:: change to API directory
cd API

:: start API in other shell
start cmd /k node index.js

:: ------ CEP ------
:: change to CEP directory
cd ../HealthyPork

start cmd /k java -jar HealthyPork.jar

:: ------ Grafana ------ 
set GRAFANA_URL="http://localhost:3000"
START "" %GRAFANA_URL%

:: ------ Synoptic ------
set SYNOPTIC_URL="http://localhost:5000"

:: change to Synoptic directory
cd ../Synoptic

:: start Flask Synoptic in other shell
start cmd /k python app.py

START "" %SYNOPTIC_URL%
