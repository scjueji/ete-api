#!/usr/bin/env bash
set +x
echo 'The following Maven command installs your Maven-built Java application'
echo 'into the local Maven repository, which will ultimately be stored in'
echo 'Jenkins''s local Maven repository (and the "maven-repository" Docker data'
echo 'volume).'
 mvn clean install -f ./pom.xml
 mvn clean install -f ./common/pom.xml
 mvn clean install -f ./ete_getway/pom.xml
 mvn clean install -f ./ete_server/pom.xml
 mvn clean install -f ./seat_provider/pom.xml

java -jar target/eteserver-1.0-SNAPSHOT.jar
