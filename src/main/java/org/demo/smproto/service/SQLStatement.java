package org.demo.smproto.service;

public class SQLStatement {
	
	public static String AppsMostCritical = "select application_name as period, sum(DISCOVERED_COUNT_SECURITY_CRITICAL)  as count from metric group by application_name order by 2 desc limit 100";

}
