#!/bin/bash

inputfile=${1}

workdir=${HOME}/run_smproto

outputfile=${workdir}/successmetrics.csv

iqurl=http://localhost:8070

#unix: $HOME/.netrc (chmod 400)
#win: %HOME%/_netrc
#machine localhost
#login admin
#password admin123

curl -n -X POST -H "Accept: application/json" -H "Content-Type: application/json" -o ${outputfile} -d@${inputfile} ${iqurl}/api/v2/reports/metrics


