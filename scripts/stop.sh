#!/bin/bash

ROOT_PATH="/home/ubuntu/Reminiscence-BE"
JAR="$ROOT_PATH/build/libs/reminiscence-0.0.1-SNAPSHOT.jar"
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR) # 실행중인 Spring 서버의 PID

if [ -z "$SERVICE_PID" ]; then
  echo "서비스 NotFound" >> $STOP_LOG
else
  echo "서비스 종료 " >> $STOP_LOG
  kill -9 "$SERVICE_PID"
fi