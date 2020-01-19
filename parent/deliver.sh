#!/usr/bin/env bash
set +x
echo 'The following Maven command installs your Maven-built Java application'
echo 'into the local Maven repository, which will ultimately be stored in'
echo 'Jenkins''s local Maven repository (and the "maven-repository" Docker data'
echo 'volume).'
 

 mvn clean  -f ./parent/pom.xml
 mvn clean  install -f ./parent/common/pom.xml
 mvn clean install -f ./parent/eteserver/pom.xml


java -jar ./parent/eteserver/target/eteserver-1.0-SNAPSHOT.jar
