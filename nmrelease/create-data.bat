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
mkdir ${datadir}
fi

python3 --version > nul 2> nul

if %errorlevel% == 0 goto :usepython3

echo using curl [%errorlevel%]
set outputfile=successmetrics.csv
echo curl -u %iquser%:%iqpwd% -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o %outputfile% -d@%payloadfile%.json %iqurl%/api/v2/reports/metrics
curl -u %iquser%:%iqpwd% -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o %outputfile% -d@%payloadfile%.json %iqurl%/api/v2/reports/metrics
goto :endscript

:usepython3

echo using python [%errorlevel%]
python3 create-success-metrics-data.py %iqurl% %iquser% %iqpwd% %payloadfile%
python3 create-policy-violations-data.py %iqurl% %iquser% %iqpwd% 
python3 create-application-evaluations-data.py %iqurl% %iquser% %iqpwd% 
python3 create-components-quarantined.py %iqurl% %iquser% %iqpwd%
python3 create-waiver-data.py %iqurl% %iquser% %iqpwd%

goto :endscript

:nargs
echo usage: create-data iqurl iquser iqpwd payload-filename

:endscript
echo Done


