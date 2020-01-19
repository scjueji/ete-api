#!/usr/bin/env bash
set +x
echo 'The following Maven command installs your Maven-built Java application'
echo 'into the local Maven repository, which will ultimately be stored in'
echo 'Jenkins''s local Maven repository (and the "maven-repository" Docker data'
echo 'volume).'
 
sh "chmod 777 ./parent/ete_server/pom.xml"
 mvn clean  -f ./parent/pom.xml
 mvn clean install -f ./parent/ete_server/pom.xml

sh "ls -l"
java -jar target/eteserver-1.0-SNAPSHOT.jar
