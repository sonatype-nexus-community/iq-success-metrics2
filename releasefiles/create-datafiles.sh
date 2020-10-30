#!/bin/sh

if [ "$#" -ne 4 ]; then
  echo "usage: ${0} <iq-url> <iq-user> <iq-passwd> <payloadfile>"
  exit -1
fi

iqurl=${1}
iquser=${2}
iqpwd=${3}
payloadfile=${4}

outputdir="datafiles"
mkdir ${outputdir} >/dev/null 2>&1

outputfile=${outputdir}/successmetrics.csv
curl -s -u ${iquser}:${iqpwd} -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o ${outputfile} -d@${payloadfile} ${iqurl}/api/v2/reports/metrics 
echo ${outputfile}

which python3 >/dev/null 2>&1

if [ $? -eq 0 ]; then
python3 create-application-evaluations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 create-policy-violations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 create-components-quarantined.py ${iqurl} ${iquser} ${iqpwd}
python3 create-waiver-data.py ${iqurl} ${iquser} ${iqpwd}
fi 

echo Done


