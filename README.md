

**Download the source code:**

`git clone https://github.com/sotudeko/smproto.git`


*Go into the downloaded directory*

`cd ./smproto`

**Quick start:**

```
unzip quickstart.zip
cd quickstart
./create-csv-file.sh <iq-host-url> <iq-username> <iq-password> weekly.json # (or monthly.json)
sh run.sh
```
*go to the 'Run the app' section below*


**Using the app**

*1. Create a CSV file containing success metrics data*

This is done by using the Success Metrics API to query IQ 

Example:

`curl -u admin:admin123 -X POST -H "Accept: text/csv" -H "Content-Type: application/json" -o /var/tmp/successmetrics.csv -d@./sm-run/weekly.json http://localhost:8070/api/v2/reports/metrics`

This command reads the file weekly.json in the sm-run current directory. It contains a payload describing data set to retrieve from IQ on my local machine (replace user name, password and IQ host url accordingly).

More info: https://help.sonatype.com/iqserver/automating/rest-apis/success-metrics-data-rest-api---v2

There are examples of the payload content in the sm-run directory: weekly.json and monthly.json

The output is saved to /var/tmp/successmetrics.csv (On Windows, change to c:/temp)


*2. Build the app*

This app is a simple web app running by default on port 4040

By default, the app looks for the data in the file */var/tmp/successmetrics.csv (Windows: c:/temp/successmetrics.csv)*

This file is loaded on start-up of the app. Larger files may take a few mins.

In the smproto directory

Build and run
`mvn clean package spring-boot:repackage`
`java -jar target/smproto-<version>.jar`

or just run:
`mvn clean spring-boot:run`

You may also use the *./sm-build/build-jar.sh* file to build a jar file which you can then run with `java -jar <jarfile>`

*3. Run the app*

When you start the app and the csv file is loaded, you should see output similar to below after which app is ready for access

```
2020-05-11 19:07:30.554  INFO 88726 --- [           main] org.demo.smproto.ReadCSVFileRunner       : Number of entries: 52
2020-05-11 19:07:30.554  INFO 88726 --- [           main] org.demo.smproto.ReadCSVFileRunner       : Ready for browsing*
```

In a browser, go to http://localhost:4040

Follow the links to see data and reports (If you want to access the database user=user password=install)




