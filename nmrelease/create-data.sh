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

mkdir ${datadir} && cd ${datadir} && pwd

which python3 >/dev/null 2>&1

if [ $? -eq 0 ]; then
  
python3 ../create-success-metrics-data.py ${iqurl} ${iquser} ${iqpwd} ../${payloadfile}
python3 ../create-application-evaluations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 ../create-policy-violations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 ../create-components-quarantined.py ${iqurl} ${iquser} ${iqpwd}
python3 ../create-waiver-data.py ${iqurl} ${iquser} ${iqpwd}

else

outputfile=successmetrics.csv
echo curl -u ${iquser}:${iqpasswd} -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o ${outputfile} -d@${payloadfile} ${iqurl}/api/v2/reports/metrics 
curl -u ${iquser}:${iqpasswd} -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o ${outputfile} -d@${payloadfile} ${iqurl}/api/v2/reports/metrics 

fi

echo Done


