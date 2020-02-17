@echo off

cd %cd%

set EXEC_MOSQUITTO="D:\Program Files\mosquitto\mosquitto.exe"

:: set MEMORY_OPTIONS=-Xms256m

:: set SOURCEPATH=..\HealthyPork\src\main\java

:: set TARGETPATH=..\HealthyPork\target\classes

:: "%JAVA_HOME%"\bin\javac -d %TARGETPATH% -source 1.8 -sourcepath %SOURCEPATH% %SOURCEPATH%\com\healthypork\main\HealthyPorkMain.java

START "" %EXEC_MOSQUITTO%

:: "%JAVA_HOME%"\bin\java %MEMORY_OPTIONS% com.healthypork.main.HealthyPorkMain

pause