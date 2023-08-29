#!/bin/bash

source ~/.bashrc

ROOT_PATH="/home/ubuntu/Reminiscence-BE"
JAR="$ROOT_PATH/build/libs/reminiscence-0.0.1-SNAPSHOT.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

echo "$JAR 실행" >> $START_LOG
nohup java -jar $JAR > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $JAR)
echo "서비스 PID: $SERVICE_PID" >> $START_LOG
