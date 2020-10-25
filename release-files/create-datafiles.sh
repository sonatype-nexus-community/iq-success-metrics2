#!/bin/sh

if [ "$#" -ne 3 ]; then
  echo "usage: ${0} <iq-url> <iq-user> <iq-passwd>"
  exit -1
fi

iqurl=${1}
iquser=${2}
iqpwd=${3}

datadir="./datafiles"

if [ -d "${datadir}" ]; then

cd ${datadir}

python3 ../create-application-evaluations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 ../create-policy-violations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 ../create-components-quarantined.py ${iqurl} ${iquser} ${iqpwd}
python3 ../create-waiver-data.py ${iqurl} ${iquser} ${iqpwd}

else 

echo "output directory ${datadir} does not exist"

fi

echo Done


