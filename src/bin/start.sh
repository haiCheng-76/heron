#!/bin/sh
#author: 582895699@qq.com
cd ..
# 配置自己的启动类
man_class='webiste.lhc.heron.HeronApplication'
home=$(pwd)
jvm_params='-Xms512m -Xmx2g'
conf_dir=$home/conf/
lib_dir=$home/lib/*
nohup java $jvm_params -cp $conf_dir:$lib_dir $man_class > start.log 2>&1 &
pid=$!
echo "Application started successfully!"
echo 'pid: '+$pid
cd $home/bin
echo $pid > pid.pid
