
## Getting Started

**Download the java app for success metrics**
  * Click on the *quickstart.zip* file, then download (there is a download button on the lower right)
  * Unzip the contents to your directory of choice, then navigate to the *quickstart* directory

```
unzip quickstart.zip
cd quickstart
```

**Make Config Updates for Success Metrics**

 * Edit either the *weekly.json* or *monthly.json* file to adjust the firstTimePeriod (the week or month to start reporting from) 
 * You may also choose to add an end period (Additional information can be found here: https://help.sonatype.com/iqserver/automating/rest-apis/success-metrics-data-rest-api---v2).
 * For larger installations, we recommend limiting the data to extract to smaller period (e.g. since the previous month or few weeks) or subset of organisations and applications. Information on how to adjust the request body (in the weekly.json ot monthly.json files) can be found at the success metrics page above.

**Create the csv files**

*There are some scripts available to help ease creation of the required CSV files. There are two ways you can create these files depending on if you have python3 available on the system where the scripts will be run*
 
 * If you have python3 available
 * Open a command prompt and run 


```
Windows: create-datafiles.bat <iq-host-url> <iq-username> <iq-password> <period-file>
Linux: create-datafiles.sh <iq-host-url> <iq-username> <iq-password> <period-file>

iq-host-url - your Nexus IQ Url, but with no backslash at the end
iq-username - your Nexus IQ user name that has access to data set you'd like to report on
iq-password - your Nexus IQ password
period-file - weekly.json or monthly.json

Example:  create-datafiles.bat http://localhost:8070 admin admin123 monthly.json

The script will run three python3 programs that each extract data from different Nexus IQ REST API's

All output files are saved to the current directory

The following CSV files are created:

successmetrics.csv - from the Success Metrics API (https://help.sonatype.com/iqserver/automating/rest-apis/success-metrics-data-rest-api---v2)
policyviolations.csv - from the Policy Violation API (https://help.sonatype.com/iqserver/automating/rest-apis/policy-violation-rest-api---v2)
applicationevaluations.csv - from the Report-related API (https://help.sonatype.com/iqserver/automating/rest-apis/report-related-rest-apis---v2)

```

 * If you do not have python3 on your system
 * Open a command prompt and run 

```
Windows: create-csvfile.bat <iq-host-url> <iq-username> <iq-password> <period>
Linux: create-csvfile.sh <iq-host-url> <iq-username> <iq-password> <period>

iq-host-url - your Nexus IQ Url, but with no backslash at the end
iq-username - your Nexus IQ user name that has access to data set you'd like to report on
iq-password - your Nexus IQ password
period - weekly or monthly

Example:  create-csvfile.bat http://localhost:8070 admin admin123 monthly

The output is saved to successmetrics.csv in the current directory

```

**Start the reporting app**
   
   This app is a simple web app running by default on port 4040. 
   
   By default, the app looks for the data to load in the file *successmetrics.csv* from the current directory

   You only need to keep the app running long enough to review the reports and print them to PDF

   Still within the command prompt window, run
```
Windows: run-app.bat  
Linux: run-app.sh
```

The data file is loaded on start-up of the app. Larger files may take a few mins.

When the file is loaded, you should see output similar to below after which app is ready for access

```
2020-07-06 10:45:48.036  INFO 93848 --- [           main] o.nexusiq.successmetrics.StartupRunner   : Ready for browsing at http://localhost:4040
```

Open a browser and go to http://localhost:4040

**Save PDF files**

The *Unsigned Report* is designed to be saved to pdf. It contains most of the other reports. The recommended way to do to this is by selecting the 'Save to PDF' option with the Print menu option of your wweb browser.

**Development**

Should you wish to edit the source code: 

  * Clone the repository
  * Make your changes
  * At the root directory of the repo
```
To test:
`mvn clean spring-boot:run`

To build:
`mvn clean package spring-boot:repackage`

To run:
`java -jar target/success-metrics-<version>.jar`
```

**The Fine Print**
* We recommend running it for 4 weeks of data at a time and for sets of orgs instead of the full scope if you have a large dataset.
* It is worth noting that this is NOT SUPPORTED by Sonatype, and is a contribution of ours to the open source community (read: you!)
* The application only supports Nexus IQ instances running with the embedded database. It does not run with installations using an external Postgres database.

* Don't worry, using this community item does not "void your warranty". In a worst case scenario, you may be asked by the Sonatype Support team to remove the community item in order to determine the root cause of any issues.

* Remember:

* Use this contribution at the risk tolerance that you have
* Do NOT file Sonatype support tickets related to iq-success-metrics
* DO file issues here on GitHub, so that the community can pitch in
* Phew, that was easier than I thought. Last but not least of all:

* Have fun creating and using this application and the Nexus platform, we are glad to have you here!
