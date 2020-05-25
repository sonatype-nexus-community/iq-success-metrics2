package org.demo.smproto.service;

public class SQLStatement {
	
	public static String ApplicationsOnboarded = "select time_period_start as label, count(application_id) as pointA from metrics group by time_period_start order by time_period_start";
	public static String NumberOfScans = "select time_period_start as label, sum(evaluation_count) as pointA from metrics group by time_period_start";
	public static String ApplicationScans = "select time_period_start as label, count(application_id) as pointA from metrics where evaluation_count > 0 group by time_period_start";
	public static String ApplicationCriticalViolations = "select application_name as label, sum(DISCOVERED_COUNT_SECURITY_CRITICAL) as pointA, sum(DISCOVERED_COUNT_SECURITY_SEVERE) as pointB, sum(DISCOVERED_COUNT_SECURITY_MODERATE) as pointC from metrics group by application_name order by pointA desc, pointB desc";
	public static String MostScannedApplications = "select application_name as label, sum (evaluation_count) as pointA from metrics group by application_name order by 2 desc";

	public static String CriticalSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Critical) as pointA, sum(fixed_Count_Security_Critical) as pointB, sum(waived_Count_Security_Critical) as pointC from metrics group by time_period_start";
	public static String SevereSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Severe) as pointA, sum(fixed_Count_Security_Severe) as pointB, sum(waived_Count_Security_Severe) as pointC from metrics group by time_period_start";
	public static String ModerateSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Moderate) as pointA, sum(fixed_Count_Security_Moderate) as pointB, sum(waived_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String DiscoveredSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Critical) as pointA, sum(discovered_Count_Security_Severe) as pointB, sum(discovered_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String FixedSecurityViolations = "select time_period_start as label, sum(fixed_Count_Security_Critical) as pointA, sum(fixed_Count_Security_Severe) as pointB, sum(fixed_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String WaivedSecurityViolations = "select time_period_start as label, sum(waived_Count_Security_Critical) as pointA, sum(waived_Count_Security_Severe) as pointB, sum(waived_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String OpenSecurityViolations = "select time_period_start as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Critical) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) as pointC from metrics group by time_period_start";

	public static String CriticalLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Critical) as pointA, sum(fixed_Count_License_Critical) as pointB, sum(waived_Count_License_Critical) as pointC from metrics group by time_period_start";
	public static String SevereLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Severe) as pointA, sum(fixed_Count_License_Severe) as pointB, sum(waived_Count_License_Severe) as pointC from metrics group by time_period_start";
	public static String ModerateLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Moderate) as pointA, sum(fixed_Count_License_Moderate) as pointB, sum(waived_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String DiscoveredLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Critical) as pointA, sum(discovered_Count_License_Severe) as pointB, sum(discovered_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String FixedLicenseViolations = "select time_period_start as label, sum(fixed_Count_License_Critical) as pointA, sum(fixed_Count_License_Severe) as pointB, sum(fixed_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String WaivedLicenseViolations = "select time_period_start as label, sum(waived_Count_License_Critical) as pointA, sum(waived_Count_License_Severe) as pointB, sum(waived_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String OpenLicenseViolations = "select time_period_start as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Critical) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC from metrics group by time_period_start";

	
	//public static String MTTR = "select TIME_PERIOD_START as label, avg(case when ifnull(MTTR_CRITICAL_THREAT,0) <>0 then MTTR_CRITICAL_THREAT else null end)/86400000 as pointA,  avg(case when MTTR_SEVERE_THREAT <> 0 then MTTR_SEVERE_THREAT else null end)/864000000 as pointB,  avg(case when MTTR_MODERATE_THREAT <> 0 then MTTR_MODERATE_THREAT else null end)/86400000  as pointC from metrics  group by time_period_start";

	public static String MTTR = "select TIME_PERIOD_START as label, \n" + 
			"ifnull(avg(case when ifnull(MTTR_CRITICAL_THREAT,0) <>0 then ifnull(MTTR_CRITICAL_THREAT,0) else null end)/86400000,0) as pointA,  \n" + 
			"ifnull(avg(case when ifnull(MTTR_SEVERE_THREAT,0) <> 0 then ifnull(MTTR_SEVERE_THREAT,0) else null end)/864000000,0) as pointB,  \n" + 
			"ifnull(avg(case when ifnull(MTTR_MODERATE_THREAT,0) <> 0 then ifnull(MTTR_MODERATE_THREAT,0) else null end)/86400000,0)  as pointC \n" + 
			"from metrics  group by time_period_start";
	//public static String MTTR = "select TIME_PERIOD_START as label, avg(ifnull(MTTR_CRITICAL_THREAT,0)/86400000) as pointA,  avg(ifnull(MTTR_SEVERE_THREAT,0)/864000000) as pointB,  avg(ifnull(MTTR_MODERATE_THREAT,0)/86400000)  as pointC from metrics  group by time_period_start";

	public static String OrganisationsOpenViolations = "select  distinct organization_name as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL ) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE ) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE ) as pointC from metrics";
	
	public static String LatestTimePeriodStart = "select distinct time_period_start as label, 0 as pointA from metrics order by 1 desc limit 1";
	
	public static String TimePeriods = "select distinct time_period_start as label from metrics order by 1";

	public static String PolicyViolationsAge90 = "select * from POLICYVIOLATIONS where parsedatetime(open_time, 'yyyy-MM-dd', 'en') < CURRENT_DATE - INTERVAL 90 DAY";
	public static String PolicyViolationsAge60 = "select * from POLICYVIOLATIONS where parsedatetime(open_time, 'yyyy-MM-dd', 'en') > CURRENT_DATE - INTERVAL 90 DAY and parsedatetime(open_time, 'yyyy-MM-dd', 'en') < CURRENT_DATE - INTERVAL 30 DAY";
	public static String PolicyViolationsAge30 = "select * from POLICYVIOLATIONS where parsedatetime(open_time, 'yyyy-MM-dd', 'en') > CURRENT_DATE - INTERVAL 30 DAY "; 

	public static String PolicyViolationTables = "DROP TABLE IF EXISTS POLICYVIOLATIONS;" + 
			"CREATE TABLE POLICYVIOLATIONS (" + 
			"  policy_name VARCHAR(250) NOT NULL," + 
			"  application_name VARCHAR(250) NOT NULL," + 
			"  open_time VARCHAR(250) DEFAULT NULL," + 
			"  component VARCHAR(250) DEFAULT NULL) " +
			" AS SELECT policyname, applicationname, opentime, component FROM CSVREAD ";
	
	public static String MetricsTable = "DROP TABLE IF EXISTS METRICS; " +
			"CREATE TABLE METRICS (" +
			" application_Id VARCHAR(250) DEFAULT NULL," +
			" application_Name VARCHAR(250) DEFAULT NULL," +
			" application_Public_Id VARCHAR(250) DEFAULT NULL," +
			" discovered_Count_License_Critical INT DEFAULT NULL," +
			" discovered_Count_License_Moderate INT DEFAULT NULL," +
			" discovered_Count_License_Severe INT DEFAULT NULL," +
			" discovered_Count_Security_Critical INT DEFAULT NULL," +
			" discovered_Count_Security_Moderate INT DEFAULT NULL," +
			" discovered_Count_Security_Severe INT DEFAULT NULL," +
			" evaluation_Count INT DEFAULT NULL," +
			" fixed_Count_License_Critical INT DEFAULT NULL," +
			" fixed_Count_License_Moderate INT DEFAULT NULL," +
			" fixed_Count_License_Severe INT DEFAULT NULL," +
			" fixed_Count_Security_Critical INT DEFAULT NULL," +
			" fixed_Count_Security_Moderate INT DEFAULT NULL," +
			" fixed_Count_Security_Severe INT DEFAULT NULL," +
			" mttr_Critical_Threat DOUBLE DEFAULT NULL," +
			" mttr_Moderate_Threat DOUBLE DEFAULT NULL," +
			" mttr_Severe_Threat DOUBLE DEFAULT NULL," +
			" open_Count_At_Time_Period_End_License_Critical INT DEFAULT NULL," +
			" open_Count_At_Time_Period_End_License_Moderate INT DEFAULT NULL," +
			" open_Count_At_Time_Period_End_License_Severe INT DEFAULT NULL," +
			" open_Count_At_Time_Period_End_Security_Critical INT DEFAULT NULL," +
			" open_Count_At_Time_Period_End_Security_Moderate INT DEFAULT NULL," +
			" open_Count_At_Time_Period_End_Security_Severe INT DEFAULT NULL," +
			" organization_Name VARCHAR(250) DEFAULT NULL," +
			" time_Period_Start VARCHAR(250) DEFAULT NULL," +
			" waived_Count_License_Critical INT DEFAULT NULL," +
			" waived_Count_License_Moderate INT DEFAULT NULL," +
			" waived_Count_License_Severe INT DEFAULT NULL," +
			" waived_Count_Security_Critical INT DEFAULT NULL," +
			" waived_Count_Security_Moderate INT DEFAULT NULL," +
			" waived_Count_Security_Severe INT DEFAULT NULL)" +
			"AS SELECT " +
			"applicationId," +
			"applicationName," +
			"applicationPublicId," +
			"discoveredCountLicenseCritical," +
			"discoveredCountLicenseModerate," +
			"discoveredCountLicenseSevere," +
			"discoveredCountSecurityCritical," +
			"discoveredCountSecurityModerate," +
			"discoveredCountSecuritySevere," +
			"evaluationCount," +
			"fixedCountLicenseCritical," +
			"fixedCountLicenseModerate," +
			"fixedCountLicenseSevere," +
			"fixedCountSecurityCritical," +
			"fixedCountSecurityModerate," +
			"fixedCountSecuritySevere," +
			"mttrCriticalThreat," +
			"mttrModerateThreat," +
			"mttrSevereThreat," +
			"openCountAtTimePeriodEndLicenseCritical," +
			"openCountAtTimePeriodEndLicenseModerate," +
			"openCountAtTimePeriodEndLicenseSevere," +
			"openCountAtTimePeriodEndSecurityCritical," +
			"openCountAtTimePeriodEndSecurityModerate," +
			"openCountAtTimePeriodEndSecuritySevere," +
			"organizationName," +
			"timePeriodStart," +
			"waivedCountLicenseCritical," +
			"waivedCountLicenseModerate," +
			"waivedCountLicenseSevere," +
			"waivedCountSecurityCritical," +
			"waivedCountSecurityModerate," +
			"waivedCountSecuritySevere " +
			" from csvread ";

	
}


//			"  id INT AUTO_INCREMENT  PRIMARY KEY," + 
