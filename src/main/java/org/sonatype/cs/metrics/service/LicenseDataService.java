package org.sonatype.cs.metrics.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LicenseDataService {
	
	@Autowired
	private DbService dbService;
	
	
	public Map<String, Object> getLicenseViolations() {
		Map<String, Object> model = new HashMap<>();
		
		List<DbRow> licenseViolations = dbService.runSql(SqlStatement.LicenseViolations);
		List<DbRow> discoveredLicenseViolations = dbService.runSql(SqlStatement.DiscoveredLicenseViolations);
		List<DbRow> openLicenseViolations = dbService.runSql(SqlStatement.OpenLicenseViolations);
		List<DbRow> fixedLicenseViolations = dbService.runSql(SqlStatement.FixedLicenseViolations);
		List<DbRow> waivedLicenseViolations = dbService.runSql(SqlStatement.WaivedLicenseViolations);

		model.put("LicenseViolations", licenseViolations);
		model.put("discoveredLicenseViolations", discoveredLicenseViolations);
		model.put("openLicenseViolations", openLicenseViolations);
		model.put("fixedLicenseViolations", fixedLicenseViolations);
		model.put("waivedLicenseViolations", waivedLicenseViolations);
		
		DbRow discoveredLicenseViolationsTotal = dbService.runSql(SqlStatements.DiscoveredLicenseViolationsTotal).get(0);
		DbRow openLicenseViolationsTotal = dbService.runSql(SqlStatements.OpenLicenseViolationsTotal).get(0);
		DbRow fixedLicenseViolationsTotal = dbService.runSql(SqlStatements.FixedLicenseViolationsTotal).get(0);
		DbRow waivedLicenseViolationsTotal = dbService.runSql(SqlStatements.WaivedLicenseViolationsTotal).get(0);
		
		model.put("discoveredLicenseViolationsTotal", discoveredLicenseViolationsTotal);
		model.put("openLicenseViolationsTotal", openLicenseViolationsTotal);
		model.put("fixedLicenseViolationsTotal", fixedLicenseViolationsTotal);
		model.put("waivedLicenseViolationsTotal", waivedLicenseViolationsTotal);
		
		int discoveredLicenseTotal = discoveredLicenseViolationsTotal.getPointA() + discoveredLicenseViolationsTotal.getPointB() + discoveredLicenseViolationsTotal.getPointC();
		int fixedLicenseTotal = fixedLicenseViolationsTotal.getPointA() + fixedLicenseViolationsTotal.getPointB() + fixedLicenseViolationsTotal.getPointC();
		int waivedLicenseTotal = waivedLicenseViolationsTotal.getPointA() + waivedLicenseViolationsTotal.getPointB() + waivedLicenseViolationsTotal.getPointC();
		
		model.put("discoveredLicenseTotal", discoveredLicenseTotal);
		model.put("fixedLicenseTotal", fixedLicenseTotal);
		model.put("waivedLicenseTotal", waivedLicenseTotal);

		int discoveredLicenseCriticalTotal = discoveredLicenseViolationsTotal.getPointA();
		int fixedLicenseCriticalTotal = fixedLicenseViolationsTotal.getPointA();
		int waivededLicenseCriticalTotal = waivedLicenseViolationsTotal.getPointA();
		
		model.put("discoveredLicenseCriticalTotal", discoveredLicenseCriticalTotal);
		model.put("fixedLicenseCriticalTotal", fixedLicenseCriticalTotal);
		model.put("waivededLicenseCriticalTotal", waivededLicenseCriticalTotal);

		return model;
	}

}
