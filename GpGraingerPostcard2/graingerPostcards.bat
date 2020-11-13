@ECHO OFF
REM.-- Prepare the Command Processor

set CLASSPATH=.;C:\GP\lib\grainger-jars\xml-apis-1.0.b2.jar;C:\GP\lib\grainger-jars\opencsv-4.5.jar;C:\GP\lib\grainger-jars\guava-29.0-jre.jar;C:\GP\lib\grainger-jars\poi-3.16.jar;
set CLASSPATH=%CLASSPATH%;C:\GP\lib\grainger-jars\poi-ooxml-3.16.jar;C:\GP\lib\grainger-jars\poi-ooxml-schemas-3.16.jar;C:\GP\lib\grainger-jars\xmlbeans-2.6.0.jar;
set CLASSPATH=%CLASSPATH%;C:\GP\lib\grainger-jars\commons-collections4-4.1.jar;C:\GP\lib\grainger-jars\commons-lang3-3.10.jar;C:\GP\lib\grainger-jars\commons-beanutils-1.9.3.jar;
set CLASSPATH=%CLASSPATH%;C:/GP/lib/pdfbox-2.0.21.jar;C:/GP/lib/commons-logging-1.2.jar

set uname=

@echo off

title Grainger Postcard Menu

:home
rem cls
echo.
echo.====================== Grainger Postcard Menu ======================
echo.
echo   1) Merge SKU and Lettershop extract files. Generate input file for Presort 
echo         - Files must be in the inputSKU, inputExtract, and inputTab folders
echo         - Only 1 file allowed in each folder
echo         - Output is a file ready for Presort
echo.
echo   2) Download images found in Presort
echo         - File must be in the presort folder
echo         - Only 1 file allowed in the folder
echo.
echo   3) Generate PDF
echo.
echo   4) Split PDF
echo.
echo   5) End
echo.
set /p web=Type option:
if "%web%"=="1" goto menu_1
if "%web%"=="2" goto menu_2
if "%web%"=="3" goto menu_3
if "%web%"=="4" goto menu_4
if "%web%"=="5" goto end
goto home

:menu_1
IF "%uname%"=="" SET /P uname=Please enter file identifier: 
echo File Identifer: %uname%

@ECHO ON
java -Xms2048M -Xmx2048M GpGraingerCardMerge %uname%
@ECHO OFF
GOTO home

:menu_2
IF "%uname%"=="" SET /P uname=Please enter file identifier: 
echo File Identifer: %uname%

@ECHO ON
java -Xms2048M -Xmx2048M GpGraingerDownloadImages
@ECHO OFF
GOTO home

:menu_3
IF "%uname%"=="" SET /P uname=Please enter file identifier: 
echo File Identifer: %uname%

@ECHO ON
java -Xms2048M -Xmx2048M GpGraingerCardGen %uname%
@ECHO OFF

IF ERRORLEVEL 1 goto end

set FO_FILE=C:\GP\grainger\reorder\fo\%uname%_file.fo
echo java -Xms128M -Xmx128M transformXSLT C:\GP\Grainger\reorder\fo\%uname%_records.xml C:\GP\xsl\grainger-postcard-5.xsl %FO_FILE% a b
java -Xms128M -Xmx128M transformXSLT C:\GP\Grainger\reorder\fo\%uname%_records.xml C:\GP\xsl\grainger-postcard-5.xsl %FO_FILE% a b


:fo
REM Generate the PDF file
echo on.
rem cd C:\GP\XEP
rem cd C:\Program Files\RenderX\XEP
cd C:\GP\XEP

echo off

echo xep -fo %FO_FILE% C:\GP\Grainger\reorder\output\%uname%-out.pdf
call xep -fo %FO_FILE% C:\GP\Grainger\reorder\output\%uname%-out.pdf

echo.
echo C:\GP\Grainger\reorder\output\%uname%-out.pdf Created
echo.

REM Back to the bin directory
cd C:\GP\bin

GOTO home


:menu_4
IF "%uname%"=="" SET /P uname=Please enter file identifier: 
echo File Identifer: %uname%

@ECHO ON
java -Xms2048M -Xmx2048M GpGraingerCardSplitPages %uname%-out.pdf 2000
@ECHO OFF

GOTO home



:end
set uname=
echo.
echo.


