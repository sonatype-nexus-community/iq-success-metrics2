package org.nexusiq.successmetrics.service;

import java.util.List;

import org.nexusiq.successmetrics.model.ApplicationEvaluation;
import org.nexusiq.successmetrics.model.DataPoint;
import org.nexusiq.successmetrics.model.MTTRPoint;
import org.nexusiq.successmetrics.model.PolicyViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SQLService {
	
	private static final Logger log = LoggerFactory.getLogger(SQLService.class);

	@Autowired 
    private JdbcTemplate jtm;
	
	public List<DataPoint> executeSQLMetrics(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(DataPoint.class));  
	}
	
	public List<PolicyViolation> executeSQLPolicyViolation(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(PolicyViolation.class));  
	}
	
	public List<ApplicationEvaluation> executeSQLApplicationEvaluation(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(ApplicationEvaluation.class));  
	}
	
	public List<MTTRPoint> executeSQLMTTR(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(MTTRPoint.class));  
	}
	
	public String AddWhereClauseOrgOpenViolations(String sql, String time_period_start, String group_by ) {
		return sql + " where time_period_start = '" + time_period_start + "' group by " + group_by + " order by 2 desc, 3 desc";
	}
	
	public String AddWhereClauseAppOpenViolations(String sql, String time_period_start, String group_by ) {
		return sql + " where time_period_start = '" + time_period_start + "' group by " + group_by + " order by 2 desc, 3 desc";
	}
	
	public void LoadPolicyViolations(String csvFile) {
		String stmt = SQLStatement.PolicyViolationTables + "('" + csvFile + "')";	
		jtm.execute(stmt);
	}
	
	public void LoadSuccessMetrics(String csvFile) {
		String stmt = SQLStatement.MetricsTable + "('" + csvFile + "')";	
		jtm.execute(stmt);
	}
	
	public void LoadApplicationEvaluations(String csvFile) {
		String stmt = SQLStatement.ApplicationEvaluationsTable + "('" + csvFile + "')";	
		jtm.execute(stmt);
	}
}
