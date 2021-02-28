#! /bin/bash
if apid=$(pgrep -f api-module-1.0.0.jar)
then
    echo "Running, pid is $apid"
    kill -9 $apid
    echo "Stopped"
    echo "Starting api-service!"
    java -Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=9010 \
-Dcom.sun.management.jmxremote.rmi.port=9010 \
-Dcom.sun.management.jmxremote.local.only=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Xms20G -Xmx20G -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 api-module-1.0.0.jar --server.instance=1 --spring.application.name=api-service &
else
    echo "Starting api-service..."
    java -Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=9010 \
-Dcom.sun.management.jmxremote.rmi.port=9010 \
-Dcom.sun.management.jmxremote.local.only=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Xms20G -Xmx20G -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 api-module-1.0.0.jar --server.instance=1 --spring.application.name=api-service &
fi

