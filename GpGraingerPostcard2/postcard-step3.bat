echo off
rem set JAVA_HOME=c:\GP\jdk1.7.0_45\bin

set FO_FILE=C:\GP\grainger\fo\test.fo



rem echo %JAVA_HOME%\java -Xms128M -Xmx128M transformXSLT C:\GP\Grainger\testFiles\records2.xml C:\GP\xsl\grainger-postcard-2.xsl %FO_FILE% %1 %2
rem %JAVA_HOME%\java -Xms128M -Xmx128M transformXSLT C:\GP\Grainger\testFiles\records2.xml C:\GP\xsl\grainger-postcard-2.xsl %FO_FILE% %1 %2
java -Xms128M -Xmx128M transformXSLT C:\GP\Grainger\testFiles\records2.xml C:\GP\xsl\grainger-postcard-2.xsl %FO_FILE% a b


:fo
REM Generate the PDF file
cd C:\GP\XEP

echo xep -fo %FO_FILE% C:\GP\Grainger\test-out.pdf

call xep -fo %FO_FILE% C:\GP\Grainger\test-out.pdf

REM Back to the bin directory
cd C:\GP\bin



echo off
goto end



:end


