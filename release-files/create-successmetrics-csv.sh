#!/bin/sh

if [ "$#" -ne 4 ]; then
  echo "usage: ${0} <iq-url> <iq-user> <iq-passwd> <payload-jsonfile>"
  exit -1
fi

iqurl=${1}
iquser=${2}
iqpwd=${3}
payloadfile=${4}

datadir="./datafiles"

if [ -d "${datadir}" ]; then
rm -rf "${datadir}"
fi

mkdir ${datadir}

outputfile=${datadir}/successmetrics.csv

echo curl -u ${iquser}:xxx -X POST -H \"Accept: text/csv\" -H \"Content-Type: application/json\" -o ${outputfile} -d@${payloadfile} ${iqurl}/api/v2/reports/metrics 
curl -u ${iquser}:${iqpwd} -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o ${outputfile} -d@${payloadfile} ${iqurl}/api/v2/reports/metrics 

echo Done


