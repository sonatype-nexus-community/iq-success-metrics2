package org.sonatype.cs.metrics.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityDataService {
    private static final Logger log = LoggerFactory.getLogger(SecurityDataService.class);

	@Autowired
	private DbService dbService;
	
	public Map<String, Object> getSecurityViolations() {
		Map<String, Object> model = new HashMap<>();
		
		List<DbRow> securityViolations = dbService.runSql(SqlStatements.SecurityViolations);
		List<DbRow> discoveredSecurityViolations = dbService.runSql(SqlStatements.DiscoveredSecurityViolations);
		List<DbRow> openSecurityViolations = dbService.runSql(SqlStatements.OpenSecurityViolations);
		List<DbRow> fixedSecurityViolations = dbService.runSql(SqlStatements.FixedSecurityViolations);
		List<DbRow> waivedSecurityViolations = dbService.runSql(SqlStatements.WaivedSecurityViolations);

		model.put("securityViolationsChart", securityViolations);
		model.put("discoveredSecurityViolationsChart", discoveredSecurityViolations);
		model.put("openSecurityViolationsChart", openSecurityViolations);
		model.put("fixedSecurityViolationsChart", fixedSecurityViolations);
		model.put("waivedSecurityViolationsChart", waivedSecurityViolations);
		
		DbRow discoveredSecurityViolationsTotal = dbService.runSql(SqlStatements.DiscoveredSecurityViolationsTotal).get(0);
		DbRow openSecurityViolationsTotal = dbService.runSql(SqlStatements.OpenSecurityViolationsTotal).get(0);
		DbRow fixedSecurityViolationsTotal = dbService.runSql(SqlStatements.FixedSecurityViolationsTotal).get(0);
		DbRow waivedSecurityViolationsTotal = dbService.runSql(SqlStatements.WaivedSecurityViolationsTotal).get(0);
		
		model.put("discoveredSecurityViolationsTotal", discoveredSecurityViolationsTotal);
		model.put("openSecurityViolationsTotal", openSecurityViolationsTotal);
		model.put("fixedSecurityViolationsTotal", fixedSecurityViolationsTotal);
		model.put("waivedSecurityViolationsTotal", waivedSecurityViolationsTotal);
		
		int discoveredSecurityTotal = discoveredSecurityViolationsTotal.getPointA() + discoveredSecurityViolationsTotal.getPointB() + discoveredSecurityViolationsTotal.getPointC();
		int fixedSecurityTotal = fixedSecurityViolationsTotal.getPointA() + fixedSecurityViolationsTotal.getPointB() + fixedSecurityViolationsTotal.getPointC();
		int waivedSecurityTotal = waivedSecurityViolationsTotal.getPointA() + waivedSecurityViolationsTotal.getPointB() + waivedSecurityViolationsTotal.getPointC();
		
		model.put("discoveredSecurityTotal", discoveredSecurityTotal);
		model.put("fixedSecurityTotal", fixedSecurityTotal);
		model.put("waivedSecurityTotal", waivedSecurityTotal);
		
		int discoveredSecurityCriticalTotal = discoveredSecurityViolationsTotal.getPointA();
		int fixedSecurityCriticalTotal = fixedSecurityViolationsTotal.getPointA();
		int waivededSecurityCriticalTotal = waivedSecurityViolationsTotal.getPointA();
		
		model.put("discoveredSecurityCriticalTotal", discoveredSecurityCriticalTotal);
		model.put("fixedSecurityCriticalTotal", fixedSecurityCriticalTotal);
		model.put("waivededSecurityCriticalTotal", waivededSecurityCriticalTotal);

		return model;
	}
	
}
