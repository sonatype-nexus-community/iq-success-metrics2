package org.sonatype.cs.metrics.util;

public class SqlStatementPreviousPeriod {

	public static final String ApplicationsOnboarded = "select time_period_start as label, " +
														"count(application_id) as pointA " +
														"from metric_pp " +
														"group by time_period_start " +
														"order by 1 asc";

	public static final String MostScannedApplications = "select application_name as label, " +
														"sum (evaluation_count) as pointA " +
														"from metric_pp " +
														"group by application_name " +
														"order by 2 desc";

	public static String NumberOfScans = "select time_period_start as label, " +
											"sum(evaluation_count) as pointA " +
											"from metric_pp " +
											"group by time_period_start";

	public static String NumberOfApplicationsScanned = "select time_period_start as label, " +
														"count(application_id) as pointA " +
														"from metric_pp " +
														"where evaluation_count > 0 " +
														"group by time_period_start";

	public static final String DiscoveredSecurityViolations = "select time_period_start as label, " +
														"sum(discovered_count_security_critical) as pointA, " +
														"sum(discovered_count_security_severe) as pointB, " +
														"sum(discovered_count_security_moderate) as pointC " +
														"from metric_pp " +
														"group by time_period_start";

	public static final String OpenSecurityViolations = "select time_period_start as label, " +
														"sum(open_count_at_time_period_end_security_critical) as pointA, " +
														"sum(open_count_at_time_period_end_security_severe) as pointB, " +
														"sum(open_count_at_time_period_end_security_moderate) as pointC " +
														"from metric_pp " +
														"group by time_period_start";
	
	public static final String FixedSecurityViolations = "select time_period_start as label, " +
													"sum(fixed_count_security_critical) as pointA, " +
													"sum(fixed_count_security_severe) as pointB, " +
													"sum(fixed_count_security_moderate) as pointC " +
													"from metric_pp " +
													"group by time_period_start";

	public static final String WaivedSecurityViolations = "select time_period_start as label, " +
													"sum(waived_count_security_critical) as pointA, " +
													"sum(waived_count_security_severe) as pointB, " +
													"sum(waived_count_security_moderate) as pointC " +
													"from metric_pp " + 
													"group by time_period_start";
													
	public static final String DiscoveredLicenseViolations = "select time_period_start as label, " +
 																"sum(discovered_count_license_critical) as pointA, " +
																"sum(discovered_count_license_severe) as pointB, " +
																"sum(discovered_count_license_moderate) as pointC " +
																"from metric_pp " +
																"group by time_period_start";

	public static final String OpenLicenseViolations = "select time_period_start as label, " +
																"sum(open_count_at_time_period_end_license_critical) as pointA, " +
																"sum(open_count_at_time_period_end_license_severe) as pointB, " +
																"sum(open_count_at_time_period_end_license_moderate) as pointC " +
																"from metric_pp " +
																"group by time_period_start";

	public static final String FixedLicenseViolations = "select time_period_start as label, " +
														"sum(fixed_count_license_critical) as pointA, " +
														"sum(fixed_count_license_severe) as pointB, " +
														"sum(fixed_count_license_moderate) as pointC " +
														"from metric_pp " +
														"group by time_period_start";

	public static final String WaivedLicenseViolations = "select time_period_start as label, " +
														"sum(waived_count_license_critical) as pointA, " +
														"sum(waived_count_license_severe) as pointB, " +
														"sum(waived_count_license_moderate) as pointC " +
														"from metric_pp " +
														"group by time_period_start";

	public static String MTTR = "select time_period_start as label, \n" + 
			 					"ifnull(avg(case when ifnull(mttr_critical_threat,0) <>0 then ifnull(mttr_critical_threat,0) else null end)/86400000,0) as pointA,  \n" + 
			 					"ifnull(avg(case when ifnull(mttr_severe_threat,0) <> 0 then ifnull(mttr_severe_threat,0) else null end)/86400000,0) as pointB,  \n" + 
			 					"ifnull(avg(case when ifnull(mttr_moderate_threat,0) <> 0 then ifnull(mttr_moderate_threat,0) else null end)/86400000,0)  as pointC \n" + 
								 "from metric_pp " +
								 "group by time_period_start";

	public static String MTTR2 = "select time_period_start as label, " + 
								 "ifnull(mttr_critical_threat,0) as pointA,  " + 
								 "ifnull(mttr_severe_threat,0) as pointB,  " + 
								 "ifnull(mttr_moderate_threat,0)  as pointC " + 
								 "from metric_pp";
	
	public static String SecurityViolations = "select time_period_start as label, " +
													"(sum(discovered_count_security_critical)+sum(discovered_count_security_severe)+sum(discovered_count_security_moderate)) as pointA, " +
													"(sum(open_count_at_time_period_end_security_critical)+sum(open_count_at_time_period_end_security_severe)+sum(open_count_at_time_period_end_security_moderate)) as pointB, " +
													"(sum(fixed_count_security_critical)+sum(fixed_count_security_severe)+sum(fixed_count_security_moderate)) as pointC, " +
													"(sum(waived_count_security_critical)+sum(waived_count_security_severe)+sum(waived_count_security_moderate)) as pointD " +
													"from metric_pp " +
													"group by time_period_start";

	public static String LicenseViolations = "select time_period_start as label, " +
													"(sum(discovered_count_license_critical)+sum(discovered_count_license_severe)+sum(discovered_count_license_moderate)) as pointA, " +
													"(sum(open_count_at_time_period_end_license_critical)+sum(open_count_at_time_period_end_license_severe)+sum(open_count_at_time_period_end_license_moderate)) as pointB, " +
													"(sum(fixed_count_license_critical)+sum(fixed_count_license_severe)+sum(fixed_count_license_moderate)) as pointC, " +
													"(sum(waived_count_license_critical)+sum(waived_count_license_severe)+sum(waived_count_license_moderate)) as pointD " +
													"from metric_pp " +
													"group by time_period_start";

	public static String LatestTimePeriodStart = "select distinct time_period_start as label, " +
													"0 as pointA " +
													"from metric_pp " +
													"order by 1 desc limit 1";
	
	public static String TimePeriods = "select distinct time_period_start as label " +
										"from metric_pp " +
										" order by 1";

	public static String ApplicationsOpenViolations = "select  distinct application_name as label, " + 
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE)   + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE)as pointB, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC, " +
										"from metric_pp";

	public static String OrganisationsOpenViolations = "select  distinct organization_name as label, " + 
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL) as pointA, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_SEVERE)   + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_SEVERE) as pointB, " +
										"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_MODERATE) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_MODERATE) as pointC " +
										"from metric_pp";

	public static String DiscoveredSecurityViolationsTotals = "select sum(discovered_count_security_critical) as pointA, "  +
																"sum(discovered_count_security_severe) as pointB, " +
																"sum(discovered_count_security_moderate) as pointC " +
																"from metric_pp";

	public static String OpenSecurityViolationsTotals = "select time_period_start  as label, sum(open_count_at_time_period_end_security_critical) as pointA, "  +
																"sum(open_count_at_time_period_end_security_severe) as pointB, " +
																"sum(open_count_at_time_period_end_security_moderate) as pointC " +
																"from metric_pp group by time_period_start order by 1 desc";

	public static String FixedSecurityViolationsTotals = "select sum(fixed_count_security_critical) as pointA, "  +
																"sum(fixed_count_security_severe) as pointB, " +
																"sum(fixed_count_security_moderate) as pointC " +
																"from metric_pp";
													
	public static String WaivedSecurityViolationsTotals = "select sum(waived_count_security_critical) as pointA, "  +
																"sum(waived_count_security_severe) as pointB, " +
																"sum(waived_count_security_moderate) as pointC " +
																"from metric_pp";

	public static String DiscoveredLicenseViolationsTotals = "select sum(discovered_count_license_critical) as pointA, "  +
                                                                "sum(discovered_count_license_severe) as pointB, " +
                                                                "sum(discovered_count_license_moderate) as pointC " +
                                                                "from metric_pp";

    public static String OpenLicenseViolationsTotals = "select time_period_start  as label, sum(open_count_at_time_period_end_license_critical) as pointA, "  +
                                                                "sum(open_count_at_time_period_end_license_severe) as pointB, " +
                                                                "sum(open_count_at_time_period_end_license_moderate) as pointC " +
                                                                "from metric_pp group by time_period_start order by 1 desc";

    public static String FixedLicenseViolationsTotals = "select sum(fixed_count_license_critical) as pointA, "  +
                                                                "sum(fixed_count_license_severe) as pointB, " +
                                                                "sum(fixed_count_license_moderate) as pointC " +
                                                                "from metric_pp";
                                                    

    public static String WaivedLicenseViolationsTotals = "select sum(waived_count_license_critical) as pointA, "  +
                                                                "sum(waived_count_license_severe) as pointB, " +
                                                                "sum(waived_count_license_moderate) as pointC " +
																"from metric_pp";
																
	public static String ApplicationsSecurityRemediation = "select APPLICATION_NAME as label, " + 
																"sum(DISCOVERED_COUNT_SECURITY_CRITICAL) + sum(DISCOVERED_COUNT_SECURITY_SEVERE) + sum(DISCOVERED_COUNT_SECURITY_MODERATE)as pointA, " + 
																"sum(FIXED_COUNT_SECURITY_CRITICAL) + sum(FIXED_COUNT_SECURITY_SEVERE) + sum(FIXED_COUNT_SECURITY_MODERATE) as pointB, " +
																"sum(WAIVED_COUNT_SECURITY_CRITICAL) + sum(WAIVED_COUNT_SECURITY_SEVERE) + sum(WAIVED_COUNT_SECURITY_MODERATE) as pointC " +
																"from metric_pp group by APPLICATION_NAME order by 2 desc";
	
	public static String ApplicationsLicenseRemediation = "select APPLICATION_NAME as label, " +
													            "sum(DISCOVERED_COUNT_LICENSE_CRITICAL) + sum(DISCOVERED_COUNT_LICENSE_SEVERE) + sum(DISCOVERED_COUNT_LICENSE_MODERATE)as pointA, " +  
													            "sum(FIXED_COUNT_LICENSE_CRITICAL) + sum(FIXED_COUNT_LICENSE_SEVERE) + sum(FIXED_COUNT_LICENSE_MODERATE) as pointB, " +
													            "sum(WAIVED_COUNT_LICENSE_CRITICAL) + sum(WAIVED_COUNT_LICENSE_SEVERE) + sum(WAIVED_COUNT_LICENSE_MODERATE) as pointC " +
													            "from metric_pp group by APPLICATION_NAME order by 2 desc";
	
	public static String RiskRatio = "select time_period_start as label, " +
			"sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL)/count(time_period_start) as pointA " +
			"from metric_pp " +
			"group by time_period_start " +
			"order by 1";
			public static final String RiskRatioInsights =
			"select application_name as label, (sum(OPEN_COUNT_AT_TIME_PERIOD_END_SECURITY_CRITICAL) + sum(OPEN_COUNT_AT_TIME_PERIOD_END_LICENSE_CRITICAL)) as pointA " +
			"from metric_pp group by application_name order by 1";

}

