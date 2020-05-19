**Download the java app for success metrics**
Go to github:  https://github.com/sotudeko/smproto/
Click on the *quickstart.zip* file, then download (there is a download button on the lower right)
Unzip the contents to your directory of choice, then navigate to the quickstart directory

```
unzip quickstart.zip
cd quickstart
```

**Make Config Updates**

Edit either the weekly.json or monthly.json to adjust the firstTimePeriod (the week or month to start reporting from) 
(Additional information can be found here: https://help.sonatype.com/iqserver/automating/rest-apis/success-metrics-data-rest-api---v2).

**Create the csv file**
Open a command prompt and run 

Windows: create-csvfile.bat <iq-host-url> <iq-username> <iq-password> <periodFile>
Linux: create-csvfile.sh <iq-host-url> <iq-username> <iq-password>   

iq-host-url - your Nexus IQ Url, but with no backslash at the end
iq-username - your Nexus IQ user name that access to data set you'd likw to report on
iq-password - your Nexus IQ password
periodFile - weekly.json or monthly.json

`Example:  create-csvfile.bat http://localhost:8070 admin admin123 weekly.json` 

The output is saved to c:/temp/successmetrics.csv (Windows) or /var/tmp/successmetrics.csv (Linux)

**Start the reporting app (you only need to keep it running long enough to review the reports and print them to PDF)**
This app is a simple web app running by default on port 4040

By default, the app looks for the data in the file */var/tmp/successmetrics.csv (Windows: c:/temp/successmetrics.csv)*

Still within the command prompt window, run

Windows: run-app.bat file  
Linux: run-app.sh

This file is loaded on start-up of the app. Larger files may take a few mins.

When the file is loaded, you should see output similar to below after which app is ready for access

```
2020-05-11 19:07:30.554  INFO 88726 --- [           main] org.demo.smproto.ReadCSVFileRunner       : Number of entries: 52
2020-05-11 19:07:30.554  INFO 88726 --- [           main] org.demo.smproto.ReadCSVFileRunner       : Ready for browsing*
```

Open a browser and go to http://localhost:4040

**Notes:**  
Do not run it for all of your orgs/apps and 50+ weeks if you have a large dataset (a large dataset would
We recommend running it for 4 weeks at a time and for sets of orgs instead of the full scope if you have a large dataset.
It is worth noting that this is NOT SUPPORTED by Sonatype, and is a contribution of ours to the open source community.  Don't worry, using this community item does not "void your warranty".
Use this contribution at the risk tolerance that you have
Please do NOT file Sonatype support tickets related to iq-success-metrics
Please DO file issues on GitHub, so that the community can pitch in



**Development**

Should you wish to edit the source code: 

Clone the repository
Make your changes

To test:
`mvn clean spring-boot:run`

To build:
`mvn clean package spring-boot:repackage`

To run:
`java -jar target/smproto-<version>.jar`



