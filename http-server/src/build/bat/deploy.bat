@echo off

if ["%CATALINA_HOME%"] == [] (
    echo CATALINA_HOME must be specified
    exit 1
)
echo CATALINA_HOME=["%CATALINA_HOME%"]

call "%CATALINA_HOME%\bin\shutdown.bat"

rd /S /Q "%CATALINA_HOME%\webapps\kuzoff-http"
del /F /Q "%CATALINA_HOME%\webapps\kuzoff-http.war"

copy build\libs\kuzoff-http.war "%CATALINA_HOME%\webapps"
