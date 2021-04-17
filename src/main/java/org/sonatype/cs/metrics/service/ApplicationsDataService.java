package org.sonatype.cs.metrics.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.sonatype.cs.metrics.util.HelperService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationsDataService {
	private static final Logger log = LoggerFactory.getLogger(ApplicationsDataService.class);
	
	@Autowired
	private DbService dbService;
	
	@Autowired
	private PeriodsDataService periodsDataService;
	
	@Autowired
	private HelperService helperService;
	
	
	public Map<String, Object> getApplicationData() {
		Map<String, Object> model = new HashMap<>();
		
		String timePeriod = periodsDataService.getEndPeriod();

		List<DbRow> applicationsOnboardedData = dbService.runSql(SqlStatements.ApplicationsOnboarded);
		int numberOfApplicationsInPeriod = getNumberOfApplicationsForPeriod(timePeriod);
		int[] applicationsOnboarded = this.getApplicationsOnboardedCountAndAvg(applicationsOnboardedData, numberOfApplicationsInPeriod);
		model.put("applicationsOnboardedChart", applicationsOnboardedData);
		model.put("applicationsOnboarded", applicationsOnboarded[0]);
		model.put("applicationsOnboardedAvg", applicationsOnboarded[1]);
		

		List<DbRow> numberOfScansData = dbService.runSql(SqlStatements.NumberOfScans);
		int[] numberOfScans = helperService.getPointsSumAndAverage(numberOfScansData);
		model.put("numberOfScansChart", numberOfScansData);
		model.put("numberOfScans", numberOfScans[0]);
		model.put("numberOfScansAvg", numberOfScans[1]);

		List<DbRow> numberOfScannedApplicationsData = dbService.runSql(SqlStatements.NumberOfScannedApplications);
		int[] numberOfScannedApplications = helperService.getPointsSumAndAverage(numberOfScannedApplicationsData);
		model.put("numberOfApplicationsScannedChart", numberOfScannedApplicationsData);
		model.put("numberOfApplicationsScanned", numberOfScannedApplications[0]);
		model.put("numberOfApplicationsScannedAvg", numberOfScannedApplications[1]);

		List<Mttr> mttr = dbService.runSqlMttr(SqlStatements.MTTR);
		model.put("mttrChart", mttr);
		
		String applicationOpenViolations = SqlStatements.ApplicationsOpenViolations + " where time_period_start = '" + timePeriod + "' group by application_name" + " order by 2 desc, 3 desc";
		List<DbRow> aov = dbService.runSql(applicationOpenViolations);

        String organisationOpenViolations = SqlStatements.OrganisationsOpenViolations + " where time_period_start = '" + timePeriod + "' group by organization_name" + " order by 2 desc, 3 desc";
        List<DbRow> oov = dbService.runSql(organisationOpenViolations);

		model.put("mostCriticalApplicationCount", aov.get(0).getPointA());
		model.put("leastCriticalApplicationCount", aov.get(aov.size() - 1).getPointA());
		
		model.put("openCriticalViolationsAvg", helperService.getPointsSumAndAverage(aov)[1]);
		
        model.put("mostCriticalApplicationName", aov.get(0).getLabel());
        model.put("mostCriticalApplicationCount", aov.get(0).getPointA());
        
        model.put("leastCriticalApplicationName", aov.get(aov.size()-1).getLabel());
        model.put("leastCriticalApplicationCount", aov.get(aov.size()-1).getPointA());
        
        model.put("applicationsSecurityRemediation", dbService.runSql(SqlStatement.ApplicationsSecurityRemediation));
        model.put("applicationsLicenseRemediation", dbService.runSql(SqlStatement.ApplicationsLicenseRemediation));	
        
		model.put("mostCriticalOrganisationsData", oov);
		model.put("mostCriticalApplicationsData", aov);
		
		model.put("mostScannedApplicationsData", dbService.runSql(SqlStatement.MostScannedApplications));
		
		List<DbRow> riskRatio = dbService.runSql(SqlStatements.RiskRatio);
		model.put("riskRatioChart", riskRatio);
		
		return model;
	}
	
	private int getNumberOfApplicationsForPeriod(String timePeriod) {
		String sqlStmt = "select 'Number of Apps' as label, count(application_Id) as pointA from metric where time_period_start = '" + timePeriod + "'";
		List<DbRow> rows = dbService.runSql(sqlStmt);
		int numberOfApplications = rows.get(0).getPointA();
		return numberOfApplications;
	}
	
	private int[] getApplicationsOnboardedCountAndAvg(List<DbRow> dataList, int numberOfApplicationsStartPeriod) {

		int numberOfPeriods = dataList.size();
		int numberOfApplications = (int) dataList.get(dataList.size() - 1).getPointA();
		numberOfApplications -= numberOfApplicationsStartPeriod;
		int dataAverage = numberOfApplications / numberOfPeriods;

		int total = numberOfApplications;
		int avg = dataAverage;

		if (avg < 1) {
			avg = 1;
		}

		int[] values = new int[] { total, avg };
		return values;
	}

}
