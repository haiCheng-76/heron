#!/bin/bash
#author:582895699@qq.com
#date:2020年11月14日15:14:27
PARENT_DIR=$(dirname $PWD)
MAINCLASS="website.lhc.heron.HeronApplication"
JVM_OPTS="-Xms512m -Xmx1g"
CONSOLE_FILE_NAME="console.log"
cd $PARENT_DIR
if [ -e $CONSOLE_FILE_NAME ]; then
  touch $CONSOLE_FILE_NAME
fi
nohup java $JVM_OPTS -cp $PWD/conf:$PWD/lib/* $MAINCLASS >$PWD/$CONSOLE_FILE_NAME 2>&1 &
pid=$!
echo "Application started successfully!"
echo 'pid: '+$pid
echo $pid >$PWD/pid.pid
