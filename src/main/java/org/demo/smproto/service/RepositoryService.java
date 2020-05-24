package org.demo.smproto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void LoadPolicyViolationsCsvFile(String csvFile) {
		String stmt = "CREATE TABLE POLICYVIOLATION AS SELECT * FROM CSVREAD('" + csvFile + "')";	
		jdbcTemplate.execute(stmt);
	}
	
	public void LoadSuccessMetricsCsvFile(String csvFile) {
		String stmt = "DROP TABLE IF EXISTS METRIC; CREATE TABLE METRIC AS SELECT * FROM CSVREAD('" + csvFile + "')";	
		jdbcTemplate.execute(stmt);
	}
}
