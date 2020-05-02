package org.demo.smproto.service;

public class SQLStatement {
	
	public static String ApplicationsOnboarded = "select time_period_start as period, count(application_id) as count from metric group by time_period_start";
	public static String ApplicationsScanned = "select time_period_start as period, sum(evaluation_count) as count from metric group by time_period_start";
	public static String ApplicationScans = "select time_period_start as period, count(application_id) as count from metric where evaluation_count > 0 group by time_period_start";
	public static String CriticalViolations = "select time_period_start as period, sum(discovered_Count_Security_Critical) as countA, sum(fixed_Count_Security_Critical) as countB, sum(waived_Count_Security_Critical) as countC from metric group by time_period_start";
	public static String MostCriticalApplications = "select application_name as period, sum(DISCOVERED_COUNT_SECURITY_CRITICAL)  as count from metric group by application_name order by 2 desc limit 100";

}
