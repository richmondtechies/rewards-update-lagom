#!/bin/sh
JVM_OPTS="-Xmx128m -Xms128m"
rm RUNNING_PID
# This should be changed if you use Play sessions
mvn clean package -Dmaven.test.skip=true
    CONFIG="-Dconfig.resource=application.conf -Dlogs.base.dir=logs"
tar -xvf rewards-update/target/rewards-update*.tar -C rewards-update/target
java -cp "rewards-update/target/rewards-update/rewards-update/lib/*" $JAVA_OPTS $CONFIG play.core.server.ProdServerStart
