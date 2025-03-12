#!/bin/bash 

cd /opt/codedeploy-agent/deployment-root/420095b2-6a59-4a36-ab9b-b146f739c57d/d-4C2I0KPMB/deployment-archive
chmod +x gradlew
./gradlew build
mv build/libs/studentify-1.0.0.war studentify.war
