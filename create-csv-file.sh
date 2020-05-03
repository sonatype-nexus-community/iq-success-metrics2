#!/bin/bash

period=${1}

workdir=${HOME}/run_smproto

outputfile=${workdir}/${period}.csv
inputfile=${workdir}/${period}.json

iqurl=http://localhost:8070

#unix: $HOME/.netrc (chmod 400)
#win: %HOME%/_netrc
#machine localhost
#login admin
#password admin123

curl -n -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o ${outputfile} -d@${inputfile} ${iqurl}/api/v2/reports/metrics 


