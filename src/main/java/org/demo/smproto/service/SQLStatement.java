package org.demo.smproto.service;

public class SQLStatement {
	
	public static String ApplicationsOnboarded = "select time_period_start as period, count(application_id) as count from metric group by time_period_start";
	public static String NumberOfScans = "select time_period_start as period, sum(evaluation_count) as count from metric group by time_period_start";
	public static String ApplicationScans = "select time_period_start as period, count(application_id) as count from metric where evaluation_count > 0 group by time_period_start";
	
	public static String CriticalSecurityViolations = "select time_period_start as period, sum(discovered_Count_Security_Critical) as countA, sum(fixed_Count_Security_Critical) as countB, sum(waived_Count_Security_Critical) as countC from metric group by time_period_start";
	public static String SevereSecurityViolations = "select time_period_start as period, sum(discovered_Count_Security_Severe) as countA, sum(fixed_Count_Security_Severe) as countB, sum(waived_Count_Security_Severe) as countC from metric group by time_period_start";
	
	public static String CriticalLicenseViolations = "select time_period_start as period, sum(discovered_Count_License_Critical) as countA, sum(fixed_Count_License_Critical) as countB, sum(waived_Count_License_Critical) as countC from metric group by time_period_start";
	public static String SevereLicenseViolations = "select time_period_start as period, sum(discovered_Count_License_Severe) as countA, sum(fixed_Count_License_Severe) as countB, sum(waived_Count_License_Severe) as countC from metric group by time_period_start";
	
	public static String OpenSecurityViolations = "select time_period_start as period, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Critical) as countA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE) as countB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) as countC from metric group by time_period_start";
	public static String OpenLicenseViolations = "select time_period_start as period, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Critical) as countA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as countB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as countC from metric group by time_period_start";

	public static String MostCriticalApplications = "select application_name as period, sum(DISCOVERED_COUNT_SECURITY_CRITICAL) as countA, sum(DISCOVERED_COUNT_SECURITY_SEVERE) as countB, sum(DISCOVERED_COUNT_SECURITY_MODERATE) as countC from metric group by application_name order by 2 desc limit 100";
	public static String MostScannedApplications = "select application_name as period, sum (evaluation_count) as count from metric group by application_name order by 2 desc limit 100";

	public static String MTTR = "select TIME_PERIOD_START as period, isnull(sum(MTTR_CRITICAL_THREAT)/(60*60*24*1000),0) as countA, isnull(sum(MTTR_SEVERE_THREAT)/(60*60*24*1000),0) as countB, isnull(sum(MTTR_MODERATE_THREAT)/(60*60*24*1000),0) as countC from metric group by TIME_PERIOD_START";
	
	public static String OrganisationsOpenViolations = "select  distinct organization_name as period, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL ) as countA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE ) as countB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE ) as countC from metric";
	public static String ScannedApplications = "select count(distinct application_name) from metric where evaluation_count > 0 ";
	
}


