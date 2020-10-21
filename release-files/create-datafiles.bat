@echo off

set argc=0
for %%x in (%*) do set /A argc+=1
if not %argc% == 4 goto :nargs

set iqurl=%1
set iquser=%2
set iqpwd=%3
set payloadfile=%4

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


