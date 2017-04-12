#!/bin/sh
JVM_OPTS="-Xmx128m -Xms128m"
#If you are facing error, remove RUNNING_PID manually.
rm RUNNING_PID
# This should be changed if you use Play sessions
mvn clean package -Dmaven.test.skip=true
CONFIG="-Dconfig.resource=application-dev.conf -Dlogs.base.dir=logs
cd rewards-update
tar -xvf target/rewards-update-01.00.00.28-SNAPSHOT.tar -C target
java -cp "target/rewards-update/rewards-update/lib/*" $JAVA_OPTS $CONFIG play.core.server.ProdServerStart
