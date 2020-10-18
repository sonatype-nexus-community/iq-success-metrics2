package org.sonatype.cs.nxmetrics.util;

public class SqlStatement {

	public static final String ApplicationsOnboarded = "select time_period_start as label, " +
														"count(application_id) as pointA " +
														"from metric " +
														"group by time_period_start " +
														"order by 1 asc";

	public static final String MostScannedApplications = "select application_name as label, " +
														"sum (evaluation_count) as pointA " +
														"from metric " +
														"group by application_name " +
														"order by 2 desc";

	public static String NumberOfScans = "select time_period_start as label, " +
											"sum(evaluation_count) as pointA " +
											"from metric " +
											"group by time_period_start";

	public static String NumberOfApplicationsScanned = "select time_period_start as label, " +
														"count(application_id) as pointA " +
														"from metric " +
														"where evaluation_count > 0 " +
														"group by time_period_start";

	public static String OpenSecurityViolations = "select time_period_start as label, " +
													"sum(open_count_at_time_period_end_security_critical) as pointA, " +
													"sum(open_count_at_time_period_end_security_severe) as pointB, " +
													"sum(open_count_at_time_period_end_security_moderate) as pointC " +
													"from metric " +
													"group by time_period_start";

	public static final String DiscoveredSecurityViolations = "select time_period_start as label, " +
														"sum(discovered_count_security_critical) as pointA, " +
														"sum(discovered_count_security_severe) as pointB, " +
														"sum(discovered_count_security_moderate) as pointC " +
														"from metric " +
														"group by time_period_start";
	
	public static final String FixedSecurityViolations = "select time_period_start as label, " +
													"sum(fixed_count_security_critical) as pointA, " +
													"sum(fixed_count_security_severe) as pointB, " +
													"sum(fixed_count_security_moderate) as pointC " +
													"from metric " +
													"group by time_period_start";

	public static final String WaivedSecurityViolations = "select time_period_start as label, " +
													"sum(waived_count_security_critical) as pointA, " +
													"sum(waived_count_security_severe) as pointB, " +
													"sum(waived_count_security_moderate) as pointC " +
													"from metric " + 
													"group by time_period_start";

	
	public static String OpenLicenseViolations = "select time_period_start as label, " +
													"sum(open_count_at_time_period_end_license_critical) as pointA, " +
													"sum(open_count_at_time_period_end_license_severe) as pointB, " +
													"sum(open_count_at_time_period_end_license_moderate) as pointC " +
													"from metric " +
													"group by time_period_start";
													
	public static final String DiscoveredLicenseViolations = "select time_period_start as label, " +
 																"sum(discovered_count_license_critical) as pointA, " +
																"sum(discovered_count_license_severe) as pointB, " +
																"sum(discovered_count_license_moderate) as pointC " +
																"from metric " +
																"group by time_period_start";

	public static final String FixedLicenseViolations = "select time_period_start as label, " +
														"sum(fixed_count_license_critical) as pointA, " +
														"sum(fixed_count_license_severe) as pointB, " +
														"sum(fixed_count_license_moderate) as pointC " +
														"from metric " +
														"group by time_period_start";

	public static final String WaivedLicenseViolations = "select time_period_start as label, " +
														"sum(waived_count_license_critical) as pointA, " +
														"sum(waived_count_license_severe) as pointB, " +
														"sum(waived_count_license_moderate) as pointC " +
														"from metric " +
														"group by time_period_start";

	public static String MTTR = "select time_period_start as label, \n" + 
			 					"ifnull(avg(case when ifnull(mttr_critical_threat,0) <>0 then ifnull(mttr_critical_threat,0) else null end)/86400000,0) as pointA,  \n" + 
			 					"ifnull(avg(case when ifnull(mttr_severe_threat,0) <> 0 then ifnull(mttr_severe_threat,0) else null end)/86400000,0) as pointB,  \n" + 
			 					"ifnull(avg(case when ifnull(mttr_moderate_threat,0) <> 0 then ifnull(mttr_moderate_threat,0) else null end)/86400000,0)  as pointC \n" + 
								 "from metric " +
								 "group by time_period_start";

	public static String MTTR2 = "select time_period_start as label, " + 
								 "ifnull(mttr_critical_threat,0) as pointA,  " + 
								 "ifnull(mttr_severe_threat,0) as pointB,  " + 
								 "ifnull(mttr_moderate_threat,0)  as pointC " + 
								 "from metric";
	
	public static String SecurityViolations = "select time_period_start as label, " +
													"(sum(discovered_count_security_critical)+sum(discovered_count_security_severe)+sum(discovered_count_security_moderate)) as pointA, " +
													"(sum(open_count_at_time_period_end_security_critical)+sum(open_count_at_time_period_end_security_severe)+sum(open_count_at_time_period_end_security_moderate)) as pointB, " +
													"(sum(fixed_count_security_critical)+sum(fixed_count_security_severe)+sum(fixed_count_security_moderate)) as pointC, " +
													"(sum(waived_count_security_critical)+sum(waived_count_security_severe)+sum(waived_count_security_moderate)) as pointD " +
													"from metric " +
													"group by time_period_start";

	public static String LicenseViolations = "select time_period_start as label, " +
													"(sum(discovered_count_license_critical)+sum(discovered_count_license_severe)+sum(discovered_count_license_moderate)) as pointA, " +
													"(sum(open_count_at_time_period_end_license_critical)+sum(open_count_at_time_period_end_license_severe)+sum(open_count_at_time_period_end_license_moderate)) as pointB, " +
													"(sum(fixed_count_license_critical)+sum(fixed_count_license_severe)+sum(fixed_count_license_moderate)) as pointC, " +
													"(sum(waived_count_license_critical)+sum(waived_count_license_severe)+sum(waived_count_license_moderate)) as pointD " +
													"from metric " +
													"group by time_period_start";

	public static String LatestTimePeriodStart = "select distinct time_period_start as label, " +
													"0 as pointA " +
													"from metric " +
													"order by 1 desc limit 1";
	
	public static String TimePeriods = "select distinct time_period_start as label " +
										"from metric " +
										" order by 1";

	public static String ApplicationsOpenViolations = "select  distinct application_name as label, " + 
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE)   + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE)as pointB, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC, " +
										"from metric";

	public static String OrganisationsOpenViolations = "select  distinct organization_name as label, " + 
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE)   + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC " +
										"from metric";

	public static String DiscoveredSecurityViolationsTotals = "select sum(discovered_count_security_critical) as pointA, "  +
																"sum(discovered_count_security_severe) as pointB, " +
																"sum(discovered_count_security_moderate) as pointC " +
																"from metric";

	public static String OpenSecurityViolationsTotals = "select sum(open_count_at_time_period_end_security_critical) as pointA, "  +
																"sum(open_count_at_time_period_end_security_severe) as pointB, " +
																"sum(open_count_at_time_period_end_security_moderate) as pointC " +
																"from metric";

	public static String FixedSecurityViolationsTotals = "select sum(fixed_count_security_critical) as pointA, "  +
																"sum(fixed_count_security_severe) as pointB, " +
																"sum(fixed_count_security_moderate) as pointC " +
																"from metric";
													
	public static String WaivedSecurityViolationsTotals = "select sum(waived_count_security_critical) as pointA, "  +
																"sum(waived_count_security_severe) as pointB, " +
																"sum(waived_count_security_moderate) as pointC " +
																"from metric";

	public static String DiscoveredLicenseViolationsTotals = "select sum(discovered_count_license_critical) as pointA, "  +
                                                                "sum(discovered_count_license_severe) as pointB, " +
                                                                "sum(discovered_count_license_moderate) as pointC " +
                                                                "from metric";

    public static String OpenLicenseViolationsTotals = "select sum(open_count_at_time_period_end_license_critical) as pointA, "  +
                                                                "sum(open_count_at_time_period_end_license_severe) as pointB, " +
                                                                "sum(open_count_at_time_period_end_license_moderate) as pointC " +
                                                                "from metric";

    public static String FixedLicenseViolationsTotals = "select sum(fixed_count_license_critical) as pointA, "  +
                                                                "sum(fixed_count_license_severe) as pointB, " +
                                                                "sum(fixed_count_license_moderate) as pointC " +
                                                                "from metric";
                                                    

    public static String WaivedLicenseViolationsTotals = "select sum(waived_count_license_critical) as pointA, "  +
                                                                "sum(waived_count_license_severe) as pointB, " +
                                                                "sum(waived_count_license_moderate) as pointC " +
																"from metric";
																
	public static String ApplicationsSecurityRemediation = "select APPLICATION_NAME as label, " + 
																"sum(DISCOVERED_COUNT_SECURITY_CRITICAL) + sum(DISCOVERED_COUNT_SECURITY_SEVERE) + sum(DISCOVERED_COUNT_SECURITY_MODERATE)as pointA, " + 
																"sum(FIXED_COUNT_SECURITY_CRITICAL) + sum(FIXED_COUNT_SECURITY_SEVERE) + sum(FIXED_COUNT_SECURITY_MODERATE) as pointB, " +
																"sum(WAIVED_COUNT_SECURITY_CRITICAL) + sum(WAIVED_COUNT_SECURITY_SEVERE) + sum(WAIVED_COUNT_SECURITY_MODERATE) as pointC " +
																"from METRIC group by APPLICATION_NAME order by 2 desc";
	
	public static String ApplicationsLicenseRemediation = "select APPLICATION_NAME as label, " +
													            "sum(DISCOVERED_COUNT_LICENSE_CRITICAL) + sum(DISCOVERED_COUNT_LICENSE_SEVERE) + sum(DISCOVERED_COUNT_LICENSE_MODERATE)as pointA, " +  
													            "sum(FIXED_COUNT_LICENSE_CRITICAL) + sum(FIXED_COUNT_LICENSE_SEVERE) + sum(FIXED_COUNT_LICENSE_MODERATE) as pointB, " +
													            "sum(WAIVED_COUNT_LICENSE_CRITICAL) + sum(WAIVED_COUNT_LICENSE_SEVERE) + sum(WAIVED_COUNT_LICENSE_MODERATE) as pointC " +
													            "from METRIC group by APPLICATION_NAME order by 2 desc";
	
    public static final String MetricsTable = "DROP TABLE IF EXISTS METRIC; " +
			"CREATE TABLE METRIC (" +
			"id INT default null, " + 
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
			" open_count_at_time_period_end_security_Critical INT DEFAULT NULL," +
			" open_count_at_time_period_end_security_Moderate INT DEFAULT NULL," +
			" open_count_at_time_period_end_security_Severe INT DEFAULT NULL," +
			" organization_Name VARCHAR(250) DEFAULT NULL," +
			" time_Period_Start VARCHAR(250) DEFAULT NULL," +
			" waived_Count_License_Critical INT DEFAULT NULL," +
			" waived_Count_License_Moderate INT DEFAULT NULL," +
			" waived_Count_License_Severe INT DEFAULT NULL," +
			" waived_Count_Security_Critical INT DEFAULT NULL," +
			" waived_Count_Security_Moderate INT DEFAULT NULL," +
			" waived_Count_Security_Severe INT DEFAULT NULL)" +
			"AS SELECT " +
			0 + ", " +
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

	public static final String PolicyViolationsTables = "DROP TABLE IF EXISTS POLICY_VIOLATION;" + 
			"CREATE TABLE POLICY_VIOLATION (" + 
			"  policy_name VARCHAR(250) NOT NULL," + 
			"  application_name VARCHAR(250) NOT NULL," + 
			"  open_time VARCHAR(250) DEFAULT NULL," + 
			"  component VARCHAR(250) DEFAULT NULL," +
			"  stage VARCHAR(250) DEFAULT NULL) " +
			" AS SELECT policyname, applicationname, parsedatetime(opentime, 'yyyy-MM-dd', 'en'), component, stage FROM CSVREAD ";
	
	public static final String ApplicationEvaluationsTable = "DROP TABLE IF EXISTS APPLICATION_EVALUATION;" + 
			"CREATE TABLE APPLICATION_EVALUATION (" + 
			"  application_name VARCHAR(250) NOT NULL," + 
			"  evaluation_date VARCHAR(250) DEFAULT NULL," + 
			"  stage VARCHAR(250) DEFAULT NULL) " +
			" AS SELECT applicationname, parsedatetime(evaluationdate, 'yyyy-MM-dd', 'en'), stage FROM CSVREAD ";
 
	public static final String ComponentsQuarantineTables = "DROP TABLE IF EXISTS COMPONENT_QUARANTINE;" + 
			"CREATE TABLE COMPONENT_QUARANTINE (" + 
			"  repository VARCHAR(250) NOT NULL," +
			"  format VARCHAR(250) NOT NULL," + 
			"  packageUrl VARCHAR(250) NOT NULL," + 
			"  quarantineTime VARCHAR(250) DEFAULT NULL," + 
			"  policyName VARCHAR(250) DEFAULT NULL," +
			"  threatLevel VARCHAR(250) DEFAULT NULL) " +
			" AS SELECT repository, format, packageUrl, parsedatetime(quarantineTime, 'yyyy-MM-dd', 'en'), policyName, threatLevel FROM CSVREAD ";

}
