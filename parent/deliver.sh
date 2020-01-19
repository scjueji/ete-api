#!/usr/bin/env bash
set +x
 mvn clean install -f ./pom.xml
 mvn clean install -f ./common/pom.xml
 mvn clean install -f ./ete_getway/pom.xml
 mvn clean install -f ./ete_server/pom.xml
 mvn clean install -f ./seat_provider/pom.xml

java -jar target/eteserver-1.0-SNAPSHOT.jar
