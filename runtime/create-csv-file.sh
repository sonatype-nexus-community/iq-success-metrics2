#!/bin/bash

#unix: $HOME/.netrc (chmod 400)
#win: %HOME%/_netrc
#machine localhost
#login admin
#password admin123
# curl -n 

workdir=.

iqurl=${1}
iquser=${2}
iqpasswd=${3}
periodfile=${4}

outputfile=/var/tmp/successmetrics.csv

curl -u admin:admin123 -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o ${outputfile} -d@${periodfile} ${iqurl}/api/v2/reports/metrics 



