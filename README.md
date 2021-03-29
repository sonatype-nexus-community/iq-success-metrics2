
## Getting Started

**Prerequisites**
  * Java 8+
  * (optional) python3  

&nbsp;
&nbsp;

**Download the java app for success metrics**
  * Click on the *success-metrics.zip* file, then download (there is a download button on the lower right)
  * Unzip the contents into a directory of your choice

```
unzip success-metrics.zip
cd success-metrics
```

&nbsp;
&nbsp;

**(Optional) Make Config Updates for Success Metrics**

 * Edit either the *weekly.json* or *monthly.json* file to adjust the firstTimePeriod (the week or month to start reporting from) 
 * You may also choose to add an end period (Additional information can be found here: https://help.sonatype.com/iqserver/automating/rest-apis/success-metrics-data-rest-api---v2).
 * For larger installations, we recommend limiting the data to extract to a smaller period (e.g. since the previous month or few weeks) or subset of organisations and/or applications. Information on how to adjust the request body (in the weekly.json or monthly.json files) can be found at the success metrics page above.

&nbsp;
&nbsp;

**Create the csv files**

*There is a script available to help ease creation of the required CSV file(s).*
 
 * Open a command prompt and run 

```
Windows: create-data.bat <iq-host-url> <iq-username> <iq-password> <period-file>
Linux: create-data.sh <iq-host-url> <iq-username> <iq-password> <period-file>

iq-host-url - your Nexus IQ Url, (with no backslash at the end - it will not work with a trailing forward slash)
iq-username - your Nexus IQ user name (choose a user name that has access to data set you'd like to report on)
iq-password - your Nexus IQ password
period-file - weekly.json or monthly.json

Example (Windows):  create-data.bat http://localhost:8070 admin admin123 monthly.json
```

The script will create a file called *successmetrics.csv*. We suggest opening the file and check to ensure it contains metrics data.

&nbsp;

(Optional) 
If you have python3 available, you can run the following script to produce additonal data files for reporting, all of which will be read by the app on startup.
This script is optional and not required for the main success metrics report
```
cd reports2
Windows: create-data2.bat <iq-host-url> <iq-username> <iq-password>
Linux: create-data2.sh <iq-host-url> <iq-username> <iq-password>
cd ..

iq-host-url - your Nexus IQ Url, (with no backslash at the end - it will not work with a trailing forward slash)
iq-username - your Nexus IQ user name (choose a user name that has access to data set you'd like to report on)
iq-password - your Nexus IQ password

Example (Windows):  create-data2.bat http://localhost:8070 admin admin123
```

These additional files include list of policy violations, applications scanned last date, list of components in quarantine (nxrm3) and list of waivers.

The files are created in the reports2 directory

(Make sure to return to the main directory ie. successmetrics).

&nbsp;
&nbsp;

**Start the reporting app**
   
   This app is a simple web app running by default on port 4040. 
   
   By default, the app looks for the *successmetrics.csv* file in the current directory and in the reports2 directory for the optional data files

   You only need to keep the app running long enough to review the reports and print them to PDF

   Still within the command window, run
   
```
(make sure you are in the main directoyr ie. successmetrics)

Windows: runapp.bat 
Linux: sh runapp.bat
```

The data file(s) are loaded on start-up of the app. Larger files may take a few mins.

On completion, you should see output similar to below after which app is ready for access

```
2020-10-19 20:49:01.271  INFO 93369 --- [  restartedMain] o.s.cs.metrics.service.FileService       : Data loaded.
2020-10-19 20:49:01.271  INFO 93369 --- [  restartedMain] o.s.cs.metrics.runner.StartupRunner      : Ready for browsing at http://localhost:4040
```

Open a browser and go to http://localhost:4040

&nbsp;
&nbsp;

**Save PDF files**

The *Summary Report* is designed to be saved to pdf. It contains most of the other reports. The recommended way to do to this is by selecting the *Save to PDF* option within the Print menu option of your web browser.

&nbsp;
&nbsp;

**Advanced Options**

You can override the following defaults by setting following system properties when you run the command to run the application.

  * To run on another port: (default: 4040) 

    *-Dserver.port=nnn*

  * Include the latest period: (default: false) 

    The latest period is the period when you run the application. It is likely it has not ended, therefore data for the period will be incomplete.
    By default, the application will exclude the period from the report. 
    To include data for the latest period, set the following property:

    *-Ddata.includelatestperiod=true*

  * Save to PDF automatically: (default: web) 

    The application runs in two modes. By default, it will start up and you access all the reports and charts via your web browser (web mode).
    Alternatively, you may wish to just create a pdf file (pdf mode).  In the pdf mode, the file in created in a sub-directory called 
    successmetrics-reports and is time-stamped file name. The application will then immediately exit. 
    To run in pdf mode, set the following property:

    *-Dspring.profiles.active=pdf*

```
Example: 

To run the application on port 4455, just produce the pdf report and include the latest period
(you can add the options to the runapp.bat file)

java -jar -Dserver.port=4455 -Ddata.includelatestperiod=true -Dspring.profiles.active=pdf successmetrics-<version>.jar
```

&nbsp;
&nbsp;

**Development**

Should you wish to edit the source code: 

  * Clone the repository
  * Make your changes
  * At the command line in the root directory of the repo

```
To test:
./gradlew bootRun

To build:
gradle clean build examples bundle

To run:
java -jar success-metrics-<version>.jar
```

&nbsp;
&nbsp;
&nbsp;

**The Fine Print**
* We recommend running it for 4 weeks of data at a time and for sets of orgs instead of the full scope if you have a large dataset.
* It is worth noting that this is NOT SUPPORTED by Sonatype, and is a contribution of ours to the open source community (read: you!)

* Don't worry, using this community item does not "void your warranty". In a worst case scenario, you may be asked by the Sonatype Support team to remove the community item in order to determine the root cause of any issues.

* Remember:

* Use this contribution at the risk tolerance that you have
* Do NOT file Sonatype support tickets related to iq-success-metrics
* DO file issues here on GitHub, so that the community can pitch in
* Phew, that was easier than I thought. Last but not least of all:

* Have fun creating and using this application and the Nexus platform, we are glad to have you here!
