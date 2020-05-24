@ECHO OFF

SET iqurl=%1
SET iquser=%2
SET iqpasswd=%3
SET periodfile=%4

SET outputdir=%HOME%

SET outputfile=%outputdir%/successmetrics.csv

curl -u %iquser%:%iqpasswd% -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o %outputfile% -d@%periodfile%.json %iqurl%/api/v2/reports/metrics

echo "Created CSV file %outputfile%"
