@echo off

set argc=0
for %%x in (%*) do set /A argc+=1
if not %argc% == 3 goto :nargs

set iqurl=%1
set iquser=%2
set iqpwd=%3

set datadir="./datafiles"

if dirnotexists %datadir%
goto :nodir

cd %datadir%

python3 ../create-policy-violations-data.py %iqurl% %iquser% %iqpwd% 
python3 ../create-application-evaluations-data.py %iqurl% %iquser% %iqpwd% 
python3 ../create-components-quarantined.py %iqurl% %iquser% %iqpwd%
python3 ../create-waiver-data.py %iqurl% %iquser% %iqpwd%

goto :endscript

:nargs
echo usage: create-data iqurl iquser iqpwd payload-filename
goto :endscript

:nodir
echo output directory %datadir% does not exist

:endscript
echo Done


