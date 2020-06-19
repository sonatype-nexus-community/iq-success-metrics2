package org.nexusiq.successmetrics.service;

import org.springframework.stereotype.Service;

@Service
public class SQLStatement {
	
	public static String ApplicationsOnboarded = "select time_period_start as label, count(application_id) as pointA from metrics group by time_period_start order by time_period_start";
	public static String NumberOfScans = "select time_period_start as label, sum(evaluation_count) as pointA from metrics group by time_period_start";
	public static String ApplicationScans = "select time_period_start as label, count(application_id) as pointA from metrics where evaluation_count > 0 group by time_period_start";
	public static String ApplicationViolations = "select application_name as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Critical)+sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Critical) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Severe)+sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Severe) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Moderate)+sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Moderate) as pointC from metrics group by application_name order by pointA desc, pointB desc";
	public static String MostScannedApplications = "select application_name as label, sum (evaluation_count) as pointA from metrics group by application_name order by 2 desc";

	public static String CriticalSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Critical) as pointA, sum(fixed_Count_Security_Critical) as pointB, sum(waived_Count_Security_Critical) as pointC from metrics group by time_period_start";
	public static String SevereSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Severe) as pointA, sum(fixed_Count_Security_Severe) as pointB, sum(waived_Count_Security_Severe) as pointC from metrics group by time_period_start";
	public static String ModerateSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Moderate) as pointA, sum(fixed_Count_Security_Moderate) as pointB, sum(waived_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String DiscoveredSecurityViolations = "select time_period_start as label, sum(discovered_Count_Security_Critical) as pointA, sum(discovered_Count_Security_Severe) as pointB, sum(discovered_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String FixedSecurityViolations = "select time_period_start as label, sum(fixed_Count_Security_Critical) as pointA, sum(fixed_Count_Security_Severe) as pointB, sum(fixed_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String WaivedSecurityViolations = "select time_period_start as label, sum(waived_Count_Security_Critical) as pointA, sum(waived_Count_Security_Severe) as pointB, sum(waived_Count_Security_Moderate) as pointC from metrics group by time_period_start";
	public static String OpenSecurityViolations = "select application_name as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Critical) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) as pointC from metrics";

	public static String CriticalLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Critical) as pointA, sum(fixed_Count_License_Critical) as pointB, sum(waived_Count_License_Critical) as pointC from metrics group by time_period_start";
	public static String SevereLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Severe) as pointA, sum(fixed_Count_License_Severe) as pointB, sum(waived_Count_License_Severe) as pointC from metrics group by time_period_start";
	public static String ModerateLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Moderate) as pointA, sum(fixed_Count_License_Moderate) as pointB, sum(waived_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String DiscoveredLicenseViolations = "select time_period_start as label, sum(discovered_Count_License_Critical) as pointA, sum(discovered_Count_License_Severe) as pointB, sum(discovered_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String FixedLicenseViolations = "select time_period_start as label, sum(fixed_Count_License_Critical) as pointA, sum(fixed_Count_License_Severe) as pointB, sum(fixed_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String WaivedLicenseViolations = "select time_period_start as label, sum(waived_Count_License_Critical) as pointA, sum(waived_Count_License_Severe) as pointB, sum(waived_Count_License_Moderate) as pointC from metrics group by time_period_start";
	public static String OpenLicenseViolations = "select time_period_start as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Critical) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC from metrics";

	public static String MTTR = "select TIME_PERIOD_START as label, \n" + 
			"			ifnull(avg(case when ifnull(MTTR_CRITICAL_THREAT,0) <>0 then ifnull(MTTR_CRITICAL_THREAT,0) else null end)/86400000,0) as pointA,  \n" + 
			"			ifnull(avg(case when ifnull(MTTR_SEVERE_THREAT,0) <> 0 then ifnull(MTTR_SEVERE_THREAT,0) else null end)/86400000,0) as pointB,  \n" + 
			"			ifnull(avg(case when ifnull(MTTR_MODERATE_THREAT,0) <> 0 then ifnull(MTTR_MODERATE_THREAT,0) else null end)/86400000,0)  as pointC \n" + 
			"			from metrics  group by time_period_start";
		
	//public static String OrganisationsOpenSecurityViolations = "select  distinct organization_name as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) as pointC from metrics";
	//public static String OrganisationsOpenLicenseViolations = "select  distinct organization_name as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC from metrics";
	public static String OrganisationsOpenViolations = "select  distinct organization_name as label, " + 
																	"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, " +
																	"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE)   + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, " +
																	"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC " +
																	"from metrics";

	//public static String ApplicationsOpenSecurityViolations = "select  distinct application_name as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) as pointC from metrics";
	//public static String ApplicationsOpenLicenseViolations = "select  distinct application_name as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC from metrics";
	public static String ApplicationsOpenViolations = "select  distinct application_name as label, " + 
	                                                     "sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, " +
	                                                     "sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE)   + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE)as pointB, " +
	                                                     "sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC, " +
	                                                     "from metrics";

	public static String LatestTimePeriodStart = "select distinct time_period_start as label, 0 as pointA from metrics order by 1 desc limit 1";
	
	public static String TimePeriods = "select distinct time_period_start as label from metrics order by 1";
	
	public static String OpenSecurityViolationsTrend = "select time_period_start as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Critical) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) as pointC from metrics group by time_period_start order by 1 asc";
	public static String OpenLicenseViolationsTrend = "select time_period_start as label, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Critical) as pointA, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC from metrics group by time_period_start order by 1 asc";

	public static String PolicyViolationsAge90 = "select * from POLICYVIOLATIONS where parsedatetime(open_time, 'yyyy-MM-dd', 'en') <= CURRENT_DATE - INTERVAL 90 DAY";
	public static String PolicyViolationsAge60 = "select * from POLICYVIOLATIONS where parsedatetime(open_time, 'yyyy-MM-dd', 'en') > CURRENT_DATE - INTERVAL 90 DAY and parsedatetime(open_time, 'yyyy-MM-dd', 'en') < CURRENT_DATE - INTERVAL 30 DAY";
	public static String PolicyViolationsAge30 = "select * from POLICYVIOLATIONS where parsedatetime(open_time, 'yyyy-MM-dd', 'en') > CURRENT_DATE - INTERVAL 30 DAY and parsedatetime(open_time, 'yyyy-MM-dd', 'en') < CURRENT_DATE - INTERVAL 7 DAY"; 
	public static String PolicyViolationsAge7 = "select * from POLICYVIOLATIONS where parsedatetime(open_time, 'yyyy-MM-dd', 'en') >= CURRENT_DATE - INTERVAL 7 DAY "; 

	public static String ApplicationEvaluationsAge90 = "select * from ApplicationEvaluations where parsedatetime(evaluation_date, 'yyyy-MM-dd', 'en') <= CURRENT_DATE - INTERVAL 90 DAY";
	public static String ApplicationEvaluationsAge60 = "select * from ApplicationEvaluations where parsedatetime(evaluation_date, 'yyyy-MM-dd', 'en') > CURRENT_DATE - INTERVAL 90 DAY and parsedatetime(evaluation_date, 'yyyy-MM-dd', 'en') < CURRENT_DATE - INTERVAL 30 DAY";
	public static String ApplicationEvaluationsAge30 = "select * from ApplicationEvaluations where parsedatetime(evaluation_date, 'yyyy-MM-dd', 'en') > CURRENT_DATE - INTERVAL 30 DAY and parsedatetime(evaluation_date, 'yyyy-MM-dd', 'en') < CURRENT_DATE - INTERVAL 7 DAY"; 
	public static String ApplicationEvaluationsAge7 = "select * from ApplicationEvaluations where parsedatetime(evaluation_date, 'yyyy-MM-dd', 'en') >= CURRENT_DATE - INTERVAL 7 DAY "; 
	
	
	public static String ApplicationsSecurityStatus = "select APPLICATION_NAME as label, " + 
																"sum(DISCOVERED_COUNT_SECURITY_CRITICAL) + sum(DISCOVERED_COUNT_SECURITY_SEVERE) + sum(DISCOVERED_COUNT_SECURITY_MODERATE)as pointA, " + 
																"sum(FIXED_COUNT_SECURITY_CRITICAL) + sum(FIXED_COUNT_SECURITY_SEVERE) + sum(FIXED_COUNT_SECURITY_MODERATE) as pointB, " +
																"sum(WAIVED_COUNT_SECURITY_CRITICAL) + sum(WAIVED_COUNT_SECURITY_SEVERE) + sum(WAIVED_COUNT_SECURITY_MODERATE) as pointC " +
																"from METRICS group by APPLICATION_NAME order by 2 desc";
	
	public static String ApplicationsLicenseStatus = "select APPLICATION_NAME as label, " +
													            "sum(DISCOVERED_COUNT_LICENSE_CRITICAL) + sum(DISCOVERED_COUNT_LICENSE_SEVERE) + sum(DISCOVERED_COUNT_LICENSE_MODERATE)as pointA, " +  
													            "sum(FIXED_COUNT_LICENSE_CRITICAL) + sum(FIXED_COUNT_LICENSE_SEVERE) + sum(FIXED_COUNT_LICENSE_MODERATE) as pointB, " +
													            "sum(WAIVED_COUNT_LICENSE_CRITICAL) + sum(WAIVED_COUNT_LICENSE_SEVERE) + sum(WAIVED_COUNT_LICENSE_MODERATE) as pointC " +
													            "from METRICS group by APPLICATION_NAME order by 2 desc";
	
	public static String SecurityViolations = "select time_period_start as label, \n" + 
													"sum(discovered_Count_Security_Critical) + sum(discovered_Count_Security_Severe)  + sum(discovered_Count_Security_Moderate) as pointA, \n" + 
													"sum(fixed_Count_Security_Critical) + sum(fixed_Count_Security_Severe) + sum(fixed_Count_Security_Moderate) as pointB, \n" + 
													"sum(waived_Count_Security_Critical) + sum(waived_Count_Security_Severe) + sum(waived_Count_Security_Moderate) as pointC, \n" + 
													"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_Critical) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) as pointD \n" + 
													"from metrics group by time_period_start\n" + 
													"order by 1 asc";
	
	public static String LicenseViolations = "select time_period_start as label, \n" + 
											      "sum(discovered_Count_License_Critical) + sum(discovered_Count_License_Severe)  + sum(discovered_Count_License_Moderate) as pointA, \n" +  
											      "sum(fixed_Count_License_Critical) + sum(fixed_Count_License_Severe) + sum(fixed_Count_License_Moderate) as pointB, \n" +  
											      "sum(waived_Count_License_Critical) + sum(waived_Count_License_Severe) + sum(waived_Count_License_Moderate) as pointC, \n" +  
											      "sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_Critical) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointD \n" +  
											      "from metrics group by time_period_start\n" +
											      "order by 1 asc";
	
}
