#!/bin/sh

iqurl=${1}
iquser=${2}
iqpwd=${3}

python3 create-application-evaluations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 create-policy-violations-data.py ${iqurl} ${iquser} ${iqpwd} 
python3 create-components-quarantined.py ${iqurl} ${iquser} ${iqpwd}
python3 create-waiver-data.py ${iqurl} ${iquser} ${iqpwd}
