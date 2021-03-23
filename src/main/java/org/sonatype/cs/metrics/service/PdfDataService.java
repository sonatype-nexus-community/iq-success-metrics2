package org.sonatype.cs.metrics.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.SqlStatementPreviousPeriod;
import org.sonatype.cs.metrics.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

@Service
public class PdfDataService {

  @Autowired
  private DataService dataService;

  @Autowired
  private UtilService utilService;

  public Context setSummaryData() throws ParseException {

    Context model = new Context();

    String latestTimePeriod = utilService.getLatestPeriod();
    String timePeriod = utilService.getTimePeriod();
    String[] currentPeriodDateRange = utilService.getDateRangeForPeriod("current");
      
    model.setVariable("timePeriod", timePeriod);
    model.setVariable("latestTimePeriod", latestTimePeriod);
	model.setVariable("currentPeriodDateRange", "(" + currentPeriodDateRange[0] + " - " + currentPeriodDateRange[1] + ")");

    List<DbRow> applicationsOnboarded = dataService.runSql(SqlStatement.ApplicationsOnboarded);
    int numberOfApplicationsStartPeriod = getNumberofApplicationsStartPeriod(currentPeriodDateRange[0]);

    List<DbRow> numberOfScans = dataService.runSql(SqlStatement.NumberOfScans);
    List<DbRow> numberOfApplicationsScanned = dataService.runSql(SqlStatement.NumberOfApplicationsScanned);
    List<Mttr> mttr = dataService.runSqlMttr(SqlStatement.MTTR);

    model.setVariable("applicationsOnboarded", applicationsOnboarded);
    model.setVariable("numberOfScans", numberOfScans);
    model.setVariable("numberOfApplicationsScanned", numberOfApplicationsScanned);
    model.setVariable("mttr", mttr);

    model.setVariable("applicationsOnboardedAvg", sumAndAverageApplicationsOnboarded(applicationsOnboarded, numberOfApplicationsStartPeriod));

		model.setVariable("numberOfScansAvg", sumAndAveragePointA(numberOfScans));
		model.setVariable("numberOfApplicationsScannedAvg", sumAndAveragePointA(numberOfApplicationsScanned));

        DbRow discoveredSecurityViolationsTotals = dataService.runSql(SqlStatement.DiscoveredSecurityViolationsTotals).get(0);
        DbRow openSecurityViolationsTotals = dataService.runSql(SqlStatement.OpenSecurityViolationsTotals).get(0);
        DbRow fixedSecurityViolationsTotals = dataService.runSql(SqlStatement.FixedSecurityViolationsTotals).get(0);
        DbRow waivedSecurityViolationsTotals = dataService.runSql(SqlStatement.WaivedSecurityViolationsTotals).get(0);

        model.setVariable("discoveredSecurityViolationsTotals", discoveredSecurityViolationsTotals);
        model.setVariable("openSecurityViolationsTotals", openSecurityViolationsTotals);
        model.setVariable("fixedSecurityViolationsTotals", fixedSecurityViolationsTotals);
        model.setVariable("waivedSecurityViolationsTotals", waivedSecurityViolationsTotals);

        DbRow discoveredLicenseViolationsTotals = dataService.runSql(SqlStatement.DiscoveredLicenseViolationsTotals).get(0);
        DbRow openLicenseViolationsTotals = dataService.runSql(SqlStatement.OpenLicenseViolationsTotals).get(0);
        DbRow fixedLicenseViolationsTotals = dataService.runSql(SqlStatement.FixedLicenseViolationsTotals).get(0);
        DbRow waivedLicenseViolationsTotals = dataService.runSql(SqlStatement.WaivedLicenseViolationsTotals).get(0);

        model.setVariable("discoveredLicenseViolationsTotals", discoveredLicenseViolationsTotals);
        model.setVariable("openLicenseViolationsTotals", openLicenseViolationsTotals);
        model.setVariable("fixedLicenseViolationsTotals", fixedLicenseViolationsTotals);
        model.setVariable("waivedLicenseViolationsTotals", waivedLicenseViolationsTotals);

        int discoveredSecurityTotal = discoveredSecurityViolationsTotals.getPointA()+discoveredSecurityViolationsTotals.getPointB()+discoveredSecurityViolationsTotals.getPointC();
        int fixedSecurityTotal = fixedSecurityViolationsTotals.getPointA()+fixedSecurityViolationsTotals.getPointB()+fixedSecurityViolationsTotals.getPointC();
        int waivedSecurityTotal = waivedSecurityViolationsTotals.getPointA()+waivedSecurityViolationsTotals.getPointB()+waivedSecurityViolationsTotals.getPointC();

        int discoveredLicenseTotal = discoveredLicenseViolationsTotals.getPointA()+discoveredLicenseViolationsTotals.getPointB()+discoveredLicenseViolationsTotals.getPointC();
        int fixedLicenseTotal = fixedLicenseViolationsTotals.getPointA()+fixedLicenseViolationsTotals.getPointB()+fixedLicenseViolationsTotals.getPointC();
        int waivedLicenseTotal = waivedLicenseViolationsTotals.getPointA()+waivedLicenseViolationsTotals.getPointB()+waivedLicenseViolationsTotals.getPointC();

        int fixedWaived = fixedSecurityTotal+waivedSecurityTotal+fixedLicenseTotal+waivedLicenseTotal;
        int discovered = discoveredSecurityTotal+discoveredLicenseTotal;

        float fixRate = (((float)(fixedWaived)/discovered) * 100);

        model.setVariable("fixRate", String.format("%.0f", fixRate));

        model.setVariable("mttrAvg", this.MttrAvg("current"));

        String applicationOpenViolations = SqlStatement.ApplicationsOpenViolations + " where time_period_start = '" + latestTimePeriod + "' group by application_name" + " order by 2 desc, 3 desc";
        List<DbRow> aov = dataService.runSql(applicationOpenViolations);

        model.setVariable("mostCriticalApplicationCount", aov.get(0).getPointA());
        model.setVariable("leastCriticalApplicationCount", aov.get(aov.size()-1).getPointA());

        model.setVariable("openCriticalViolationsAvg", this.sumAndAveragePointA(aov)[1]);

        List<DbRow> securityViolations = dataService.runSql(SqlStatement.SecurityViolations);
        List<DbRow> discoveredSecurityViolations = dataService.runSql(SqlStatement.DiscoveredSecurityViolations);
        List<DbRow> openSecurityViolations = dataService.runSql(SqlStatement.OpenSecurityViolations);
        List<DbRow> fixedSecurityViolations = dataService.runSql(SqlStatement.FixedSecurityViolations);
        List<DbRow> waivedSecurityViolations = dataService.runSql(SqlStatement.WaivedSecurityViolations);

        model.setVariable("securityViolations", securityViolations);
        model.setVariable("discoveredSecurityViolations", discoveredSecurityViolations);
        model.setVariable("openSecurityViolations", openSecurityViolations);
		model.setVariable("fixedSecurityViolations", fixedSecurityViolations);
        model.setVariable("waivedSecurityViolations", waivedSecurityViolations);
        
        List<DbRow> licenseViolations = dataService.runSql(SqlStatement.LicenseViolations);
        List<DbRow> discoveredLicenseViolations = dataService.runSql(SqlStatement.DiscoveredLicenseViolations);
        List<DbRow> openLicenseViolations = dataService.runSql(SqlStatement.OpenLicenseViolations);
        List<DbRow> fixedLicenseViolations = dataService.runSql(SqlStatement.FixedLicenseViolations);
        List<DbRow> waivedLicenseViolations = dataService.runSql(SqlStatement.WaivedLicenseViolations);

        model.setVariable("LicenseViolations", licenseViolations);
        model.setVariable("discoveredLicenseViolations", discoveredLicenseViolations);
        model.setVariable("openLicenseViolations", openLicenseViolations);
		model.setVariable("fixedLicenseViolations", fixedLicenseViolations);
		model.setVariable("waivedLicenseViolations", waivedLicenseViolations);
		
		// Previous Period

        String pplatestTimePeriod = utilService.getPreviousPeriod();
        int ppPeriod = utilService.getPreviousPeriodRange();
        String[] previousPeriodDateRange = utilService.getDateRangeForPeriod("previous");

        
        String endStr = "";
        
        if (ppPeriod > 1) {
        	endStr = "s";
        }
        
        String ppPeriodStr = "/" + ppPeriod + " " + timePeriod + endStr;		
        model.setVariable("ppPeriodStr", ppPeriodStr);
		model.setVariable("previousPeriodDateRange", "(" + previousPeriodDateRange[0] + " - " + previousPeriodDateRange[1] + ppPeriodStr + ")");

        List<DbRow> ppapplicationsOnboarded = dataService.runSql(SqlStatementPreviousPeriod.ApplicationsOnboarded);
        List<DbRow> ppnumberOfScans = dataService.runSql(SqlStatementPreviousPeriod.NumberOfScans);
        List<DbRow> ppnumberOfApplicationsScanned = dataService.runSql(SqlStatementPreviousPeriod.NumberOfApplicationsScanned);
        List<Mttr> ppmttr = dataService.runSqlMttr(SqlStatementPreviousPeriod.MTTR);

		model.setVariable("ppapplicationsOnboarded", ppapplicationsOnboarded);
		model.setVariable("ppnumberOfScans", ppnumberOfScans);
		model.setVariable("ppnumberOfApplicationsScanned", ppnumberOfApplicationsScanned);
        model.setVariable("ppmttr", ppmttr);

        model.setVariable("ppapplicationsOnboardedAvg", sumAndAverageApplicationsOnboarded(ppapplicationsOnboarded, numberOfApplicationsStartPeriod));

		model.setVariable("ppnumberOfScansAvg", sumAndAveragePointA(ppnumberOfScans));
		model.setVariable("ppnumberOfApplicationsScannedAvg", sumAndAveragePointA(ppnumberOfApplicationsScanned));

        DbRow ppdiscoveredSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredSecurityViolationsTotals).get(0);
        DbRow ppopenSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.OpenSecurityViolationsTotals).get(0);
        DbRow ppfixedSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.FixedSecurityViolationsTotals).get(0);
        DbRow ppwaivedSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.WaivedSecurityViolationsTotals).get(0);

        model.setVariable("ppdiscoveredSecurityViolationsTotals", ppdiscoveredSecurityViolationsTotals);
        model.setVariable("ppopenSecurityViolationsTotals", ppopenSecurityViolationsTotals);
        model.setVariable("ppfixedSecurityViolationsTotals", ppfixedSecurityViolationsTotals);
        model.setVariable("ppwaivedSecurityViolationsTotals", ppwaivedSecurityViolationsTotals);

        DbRow ppdiscoveredLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredLicenseViolationsTotals).get(0);
        DbRow ppopenLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.OpenLicenseViolationsTotals).get(0);
        DbRow ppfixedLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.FixedLicenseViolationsTotals).get(0);
        DbRow ppwaivedLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.WaivedLicenseViolationsTotals).get(0);

        model.setVariable("ppdiscoveredLicenseViolationsTotals", ppdiscoveredLicenseViolationsTotals);
        model.setVariable("ppopenLicenseViolationsTotals", ppopenLicenseViolationsTotals);
        model.setVariable("ppfixedLicenseViolationsTotals", ppfixedLicenseViolationsTotals);
        model.setVariable("ppwaivedLicenseViolationsTotals", ppwaivedLicenseViolationsTotals);

        int ppdiscoveredSecurityTotal = ppdiscoveredSecurityViolationsTotals.getPointA()+ppdiscoveredSecurityViolationsTotals.getPointB()+ppdiscoveredSecurityViolationsTotals.getPointC();
        int ppfixedSecurityTotal = ppfixedSecurityViolationsTotals.getPointA()+ppfixedSecurityViolationsTotals.getPointB()+ppfixedSecurityViolationsTotals.getPointC();
        int ppwaivedSecurityTotal = ppwaivedSecurityViolationsTotals.getPointA()+ppwaivedSecurityViolationsTotals.getPointB()+ppwaivedSecurityViolationsTotals.getPointC();

        int ppdiscoveredLicenseTotal = ppdiscoveredLicenseViolationsTotals.getPointA()+ppdiscoveredLicenseViolationsTotals.getPointB()+ppdiscoveredLicenseViolationsTotals.getPointC();
        int ppfixedLicenseTotal = ppfixedLicenseViolationsTotals.getPointA()+ppfixedLicenseViolationsTotals.getPointB()+ppfixedLicenseViolationsTotals.getPointC();
        int ppwaivedLicenseTotal = ppwaivedLicenseViolationsTotals.getPointA()+ppwaivedLicenseViolationsTotals.getPointB()+ppwaivedLicenseViolationsTotals.getPointC();

        int ppfixedWaived = ppfixedSecurityTotal+ppwaivedSecurityTotal+ppfixedLicenseTotal+ppwaivedLicenseTotal;
        int ppdiscovered = ppdiscoveredSecurityTotal+ppdiscoveredLicenseTotal;

        float ppfixRate = (((float)(ppfixedWaived)/ppdiscovered) * 100);

        model.setVariable("ppfixRate", String.format("%.0f", ppfixRate));

        model.setVariable("ppmttrAvg", this.MttrAvg("previous"));

        String ppapplicationOpenViolations = SqlStatementPreviousPeriod.ApplicationsOpenViolations + " where time_period_start = '" + pplatestTimePeriod + "' group by application_name" + " order by 2 desc, 3 desc";
        List<DbRow> ppaov = dataService.runSql(ppapplicationOpenViolations);

        model.setVariable("ppmostCriticalApplicationCount", ppaov.get(0).getPointA());
        model.setVariable("ppleastCriticalApplicationCount", ppaov.get(ppaov.size()-1).getPointA());

        model.setVariable("ppopenCriticalViolationsAvg", this.sumAndAveragePointA(ppaov)[1]);

        List<DbRow> ppsecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.SecurityViolations);
        List<DbRow> ppdiscoveredSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredSecurityViolations);
        List<DbRow> ppopenSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.OpenSecurityViolations);
        List<DbRow> ppfixedSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.FixedSecurityViolations);
        List<DbRow> ppwaivedSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.WaivedSecurityViolations);

        model.setVariable("ppsecurityViolations", ppsecurityViolations);
        model.setVariable("ppdiscoveredSecurityViolations", ppdiscoveredSecurityViolations);
        model.setVariable("ppopenSecurityViolations", ppopenSecurityViolations);
		model.setVariable("ppfixedSecurityViolations", ppfixedSecurityViolations);
        model.setVariable("ppwaivedSecurityViolations", ppwaivedSecurityViolations);
        
        List<DbRow> pplicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.LicenseViolations);
        List<DbRow> ppdiscoveredLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredLicenseViolations);
        List<DbRow> ppopenLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.OpenLicenseViolations);
        List<DbRow> ppfixedLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.FixedLicenseViolations);
        List<DbRow> ppwaivedLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.WaivedLicenseViolations);

        model.setVariable("ppLicenseViolations", pplicenseViolations);
        model.setVariable("ppdiscoveredLicenseViolations", ppdiscoveredLicenseViolations);
        model.setVariable("ppopenLicenseViolations", ppopenLicenseViolations);
		model.setVariable("ppfixedLicenseViolations", ppfixedLicenseViolations);
		model.setVariable("ppwaivedLicenseViolations", ppwaivedLicenseViolations);

        return model;
    }

//    private int[] sumAndAverageApplicationsOnboarded(List<DbRow> dataList) {
//		
//		int countLabels = dataList.size();
//		int numberOfApplications = (int) dataList.get(dataList.size() - 1).getPointA();
//        int dataAverage = numberOfApplications/countLabels;
//        
//        int total = numberOfApplications;
//        int avg = dataAverage;
//
//        int[] values = new int[]{total, avg};
//		return values;
//    }
  
  private int getNumberofApplicationsStartPeriod(String timePeriod) {
		String sqlStmt = "select 'Number of Apps' as label, count(application_Id) as pointA from metric where time_period_start = '" + timePeriod +"'";
		List<DbRow> rows = dataService.runSql(sqlStmt);
		int numberOfApplications = rows.get(0).getPointA();
		return numberOfApplications;
	}
    
    private int[] sumAndAverageApplicationsOnboarded(List<DbRow> dataList, int numberOfApplicationsStartPeriod) {
		
		int countLabels = dataList.size();
		int numberOfApplications = (int) dataList.get(dataList.size() - 1).getPointA();
		numberOfApplications-=numberOfApplicationsStartPeriod;
        int dataAverage = numberOfApplications/countLabels;
        
        int total = numberOfApplications;
        int avg = dataAverage;

        int[] values = new int[]{total, avg};
		return values;
    }

    public int[] sumAndAveragePointA(List<DbRow> dataList) {
		
		int countLabels = 0;
		int sumData = 0;
		
		for (DbRow dp : dataList) {
			int count = (int) dp.getPointA();
			
			if (count > 0) {
				sumData += count;
				countLabels++;
			}
        }
        
        int total = sumData;
        int avg = sumData/countLabels;

        int[] values = new int[]{total, avg};
		return values;
    }

    private String[] MttrAvg(String period){
        List<Float> pointA = new ArrayList<>();	
	    List<Float> pointB = new ArrayList<>();	
	    List<Float> pointC = new ArrayList<>();
	    
	    String sqlStmt;
	    
	    if (period == "current") {
	    	sqlStmt = SqlStatement.MTTR2;
	    }
	    else {
	    	sqlStmt = SqlStatementPreviousPeriod.MTTR2;
	    }
	    
	    List<Mttr> mttrPoints = this.getMttr(sqlStmt);
	    
	    for (Mttr dp : mttrPoints) {
	    	pointA.add(dp.getPointA());
	    	pointB.add(dp.getPointB());
	    	pointC.add(dp.getPointC());
		}
        
        String mttrCriticalAvg = String.format("%.0f", this.averagePoint(pointA));
        String mttrSevereAvg = String.format("%.0f", this.averagePoint(pointB));
        String mttrModerateAvg = String.format("%.0f", this.averagePoint(pointC));

        String[] values = new String[]{mttrCriticalAvg, mttrSevereAvg, mttrModerateAvg};
        return values;
    }
    
private List<Mttr> getMttr(String sqlStmt) {
		
		List<Mttr> mttr = new ArrayList<Mttr>();
		
	    List<Mttr> points = dataService.runSqlMttr(sqlStmt);
	    
	    for (Mttr dp : points) {
	    	Mttr cp = new Mttr();
	        cp.setLabel(dp.getLabel());
	    	cp.setPointA(dp.getPointA()/86400000);
	    	cp.setPointB(dp.getPointB()/86400000);
	    	cp.setPointC(dp.getPointC()/86400000);

	    	mttr.add(cp);
	    }
	    
	    return mttr;
    }
    
    public Object averagePoint(List<Float> points) {
		int countPoints = 0;
		
		float sumData = 0;
		
		for (float dp : points) {
			
			if (dp > 0) {
				sumData += dp;
				countPoints++;
			}
		}
		
		return sumData/countPoints;
	}
    
}
