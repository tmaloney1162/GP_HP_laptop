echo off
rem set JAVA_HOME=c:\GP\jdk1.7.0_45\bin

set CLASSPATH=.;C:/GP/lib/tmp;C:/GP/lib/tmp/xml-apis-1.0.b2.jar;C:/GP/lib/tmp/opencsv-4.5.jar;C:/GP/lib/tmp/guava-29.0-jre.jar;C:/GP/lib/tmp/poi-3.16.jar;
set CLASSPATH=%CLASSPATH%;C:/GP/lib/tmp/poi-ooxml-3.16.jar;C:/GP/lib/tmp/poi-ooxml-schemas-3.16.jar;C:/GP/lib/tmp/xmlbeans-2.6.0.jar;C:/GP/lib/tmp/commons-collections4-4.1.jar;
set CLASSPATH=%CLASSPATH%;C:/GP/lib/tmp/commons-lang3-3.10.jar;C:/GP/lib/tmp/commons-beanutils-1.9.3.jar;
set CLASSPATH=%CLASSPATH%;C:/GP/lib/tmp/pdfbox-2.0.21.jar

rem %JAVA_HOME%\javac -d ..\bin ./postcard/GpGraingerCardGen.java

echo on
javac -d ..\bin GpGraingerCardGen.java
javac -d ..\bin GpGraingerCardMerge.java
javac -d ..\bin GpGraingerDownloadImages.java
javac -d ..\bin GpGraingerCardSplitPages.java

