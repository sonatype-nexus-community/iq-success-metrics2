@echo off

set argc=0
for %%x in (%*) do set /A argc+=1
if not %argc% == 4 goto :nargs

set iqurl=%1
set iquser=%2
set iqpwd=%3
set payloadfile=%4

datadir="./datafiles"
if [ -d "${datadir}" ]; then
fi

mkdir ${datadir}

set outputfile=%datadir%/successmetrics.csv

echo curl -u %iquser%:%iqpwd% -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o %outputfile% -d@%payloadfile%.json %iqurl%/api/v2/reports/metrics
curl -u %iquser%:%iqpwd% -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o %outputfile% -d@%payloadfile%.json %iqurl%/api/v2/reports/metrics
goto :endscript

:nargs
echo usage: create-data iqurl iquser iqpwd payload-filename

:endscript
echo Done


