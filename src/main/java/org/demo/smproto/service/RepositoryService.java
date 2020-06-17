package org.demo.smproto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
		
	public void LoadPolicyViolationsCsvFile(String csvFile) {
		String stmt = SQLStatement.PolicyViolationTables + "('" + csvFile + "')";	
		jdbcTemplate.execute(stmt);
	}
	
	public void LoadSuccessMetricsCsvFile(String csvFile) {
		String stmt = SQLStatement.MetricsTable + "('" + csvFile + "')";	
		jdbcTemplate.execute(stmt);
	}
	
	public void LoadApplicationEvaluationsCsvFile(String csvFile) {
		String stmt = SQLStatement.ApplicationEvaluationsTable + "('" + csvFile + "')";	
		jdbcTemplate.execute(stmt);
	}
}
