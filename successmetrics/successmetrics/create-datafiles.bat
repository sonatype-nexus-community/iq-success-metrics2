@echo off

set argc=0
for %%x in (%*) do set /A argc+=1

if not %argc%==4 goto :nargs

set iqurl=%1
set iquser=%2
set iqpwd=%3
set payloadfile=%4

set datadir="datafiles"
mkdir %datadir% >nul 2>nul

set outputfile=%datadir%/successmetrics.csv
curl -s -u %iquser%:%iqpwd% -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o %outputfile% -d@%payloadfile%.json %iqurl%/api/v2/reports/metrics
echo %outputfile%

python3 --version >nul 2>nul

if %errorlevel%==0 goto :endscript

python3 create-policy-violations-data.py %iqurl% %iquser% %iqpwd% 
python3 create-application-evaluations-data.py %iqurl% %iquser% %iqpwd% 
python3 create-components-quarantined.py %iqurl% %iquser% %iqpwd%
python3 create-waiver-data.py %iqurl% %iquser% %iqpwd%

goto :endscript

:nargs
echo usage: create-datafiles.bat iqurl iquser iqpwd payloadfile

:endscript
echo Done