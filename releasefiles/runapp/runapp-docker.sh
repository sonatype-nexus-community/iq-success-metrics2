#!/bin/sh

iqurl=$1
iquser=$2
iqpwd=$3
mode=$4
frequency=$5

map2datadir=$(dirname `pwd`)
datadir=/tmp/smapp

docker run -p 4040:4040  -v ${map2datadir}:${datadir} -e iq.url=${iqurl} -e iq.user=${iquser} -e iq.pwd=${iqpwd} -e spring.profiles.active=${mode} -e sm.data=${frequency} -e data.dir=${datadir} iq-success-metrics2:79


