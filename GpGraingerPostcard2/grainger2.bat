echo off
rem set JAVA_HOME=c:\GP\jdk1.7.0_45\bin
set CLASSPATH=.;C:/GP/lib/tmp;C:/GP/lib/tmp/xml-apis-1.0.b2.jar;C:/GP/lib/tmp/opencsv-4.5.jar;C:/GP/lib/tmp/guava-29.0-jre.jar;C:/GP/lib/tmp/poi-3.16.jar;
set CLASSPATH=%CLASSPATH%;C:/GP/lib/tmp/poi-ooxml-3.16.jar;C:/GP/lib/tmp/poi-ooxml-schemas-3.16.jar;C:/GP/lib/tmp/xmlbeans-2.6.0.jar;C:/GP/lib/tmp/commons-collections4-4.1.jar;
set CLASSPATH=%CLASSPATH%;C:/GP/lib/tmp/commons-lang3-3.10.jar;C:/GP/lib/tmp/commons-beanutils-1.9.3.jar;

set FO_FILE=C:\GP\grainger\fo\test.fo


rem %JAVA_HOME%\java -Xms2048M -Xmx2048M GpGraingerCardGen

@echo on
java -Xms2048M -Xmx2048M GpGraingerCardGen


echo %JAVA_HOME%\java -Xms128M -Xmx128M transformXSLT C:\GP\Grainger\testFiles\records2.xml C:\GP\xsl\grainger-postcard-2.xsl %FO_FILE% %1 %2
%JAVA_HOME%\java -Xms128M -Xmx128M transformXSLT C:\GP\Grainger\testFiles\records2.xml C:\GP\xsl\grainger-postcard-2.xsl %FO_FILE% %1 %2




:fo
REM Generate the PDF file
cd C:\GP\XEP

echo xep -fo %FO_FILE% C:\GP\Grainger\test-out2.pdf

call xep -fo %FO_FILE% C:\GP\Grainger\test-out2.pdf

REM Back to the bin directory
cd C:\GP\bin



echo off
goto end



:end


