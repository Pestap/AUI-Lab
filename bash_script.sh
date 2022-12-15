#!/bin/bash

echo "HELLO"
java -jar lab-gateway.jar &
sleep 5
java -jar lab-licenses.jar &
sleep 5
java -jar lab-pilots.jar &
echo "END"

apachectl -D FOREGROUND

