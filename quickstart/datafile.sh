#!/bin/bash

#unix: $HOME/.netrc (chmod 400)
#win: %HOME%/_netrc
#machine localhost
#login admin
#password admin123
# curl -n 

workdir=.

iqurl=http://localhost:8070
iquser=admin
iqpasswd=admin123
periodfile=weekly.json

outputfile=/var/tmp/successmetrics.csv

curl -u admin:admin123 -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o ${outputfile} -d@${periodfile} ${iqurl}/api/v2/reports/metrics 



