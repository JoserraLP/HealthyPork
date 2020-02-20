@echo off

:: change to current directory
cd %cd%

:: ------ MOSQUITTO ------
:: set the mosquitto directory 
set EXEC_MOSQUITTO="%MOSQUITTO_DIR%\mosquitto.exe"

:: start process "mosquitto.exe"
START "" %EXEC_MOSQUITTO%

:: ------ CEP ------
:: change to CEP directory
cd ./HealthyPork

start cmd /k java -jar HealthyPork.jar

:: ------ Synoptic ------
set SYNOPTIC_URL="http://localhost:5000"

:: change to Synoptic directory
cd ../Synoptic

:: start Flask Synoptic in other shell
start cmd /k python app.py

START "" %SYNOPTIC_URL%

:: ------ Simulator ------

:: change to Simulator director
cd ../Simulator

:: Start Simulator in other shell
start cmd /k python app.py
