#!/bin/sh

#git pull

mvn clean package -Dmaven.test.skip=true
java -jar ./target/iotdb-kmx-rest-1.0-SNAPSHOT-jar-with-dependencies.jar

