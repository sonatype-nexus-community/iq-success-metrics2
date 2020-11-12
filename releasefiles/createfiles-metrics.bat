@echo off

set iqurl=%1
set iquser=%2
set iqpwd=%3

python3 create-policy-violations-data.py %iqurl% %iquser% %iqpwd% 
python3 create-application-evaluations-data.py %iqurl% %iquser% %iqpwd% 
python3 create-components-quarantined.py %iqurl% %iquser% %iqpwd%
python3 create-waiver-data.py %iqurl% %iquser% %iqpwd%
