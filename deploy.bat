@ECHO OFF

set M2_HOME=D:\frameworks\apache-maven-2.2.1
set MVN_EXEC=D:\frameworks\apache-maven-2.2.1\bin\mvn

%MVN_EXEC% clean javadoc:jar source:jar install

exit