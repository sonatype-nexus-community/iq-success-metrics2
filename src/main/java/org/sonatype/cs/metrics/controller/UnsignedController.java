package org.sonatype.cs.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.sonatype.cs.metrics.service.DataService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.SqlStatementPreviousPeriod;
import org.sonatype.cs.metrics.util.UtilService;

@Controller
public class UnsignedController {
    private static final Logger log = LoggerFactory.getLogger(UnsignedController.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private UtilService utilService;

    @GetMapping({ "/unsigned" })
    public String applications(Model model) throws ParseException {

        log.info("In UnsignedController");
        
        // Current Period

        String latestTimePeriod = utilService.latestPeriod();
        String timePeriod = utilService.getTimePeriod();
		
        model.addAttribute("timePeriod", timePeriod);
        model.addAttribute("latestTimePeriod", latestTimePeriod);

        List<DbRow> applicationsOnboarded = dataService.runSql(SqlStatement.ApplicationsOnboarded);
        List<DbRow> numberOfScans = dataService.runSql(SqlStatement.NumberOfScans);
        List<DbRow> numberOfApplicationsScanned = dataService.runSql(SqlStatement.NumberOfApplicationsScanned);
        List<Mttr> mttr = dataService.runSqlMttr(SqlStatement.MTTR);

		model.addAttribute("applicationsOnboarded", applicationsOnboarded);
		model.addAttribute("numberOfScans", numberOfScans);
		model.addAttribute("numberOfApplicationsScanned", numberOfApplicationsScanned);
        model.addAttribute("mttr", mttr);

        model.addAttribute("applicationsOnboardedAvg", sumAndAverageApplicationsOnboarded(applicationsOnboarded));
		model.addAttribute("numberOfScansAvg", sumAndAveragePointA(numberOfScans));
		model.addAttribute("numberOfApplicationsScannedAvg", sumAndAveragePointA(numberOfApplicationsScanned));

        DbRow discoveredSecurityViolationsTotals = dataService.runSql(SqlStatement.DiscoveredSecurityViolationsTotals).get(0);
        DbRow openSecurityViolationsTotals = dataService.runSql(SqlStatement.OpenSecurityViolationsTotals).get(0);
        DbRow fixedSecurityViolationsTotals = dataService.runSql(SqlStatement.FixedSecurityViolationsTotals).get(0);
        DbRow waivedSecurityViolationsTotals = dataService.runSql(SqlStatement.WaivedSecurityViolationsTotals).get(0);

        model.addAttribute("discoveredSecurityViolationsTotals", discoveredSecurityViolationsTotals);
        model.addAttribute("openSecurityViolationsTotals", openSecurityViolationsTotals);
        model.addAttribute("fixedSecurityViolationsTotals", fixedSecurityViolationsTotals);
        model.addAttribute("waivedSecurityViolationsTotals", waivedSecurityViolationsTotals);

        DbRow discoveredLicenseViolationsTotals = dataService.runSql(SqlStatement.DiscoveredLicenseViolationsTotals).get(0);
        DbRow openLicenseViolationsTotals = dataService.runSql(SqlStatement.OpenLicenseViolationsTotals).get(0);
        DbRow fixedLicenseViolationsTotals = dataService.runSql(SqlStatement.FixedLicenseViolationsTotals).get(0);
        DbRow waivedLicenseViolationsTotals = dataService.runSql(SqlStatement.WaivedLicenseViolationsTotals).get(0);

        model.addAttribute("discoveredLicenseViolationsTotals", discoveredLicenseViolationsTotals);
        model.addAttribute("openLicenseViolationsTotals", openLicenseViolationsTotals);
        model.addAttribute("fixedLicenseViolationsTotals", fixedLicenseViolationsTotals);
        model.addAttribute("waivedLicenseViolationsTotals", waivedLicenseViolationsTotals);

        int discoveredSecurityTotal = discoveredSecurityViolationsTotals.getPointA()+discoveredSecurityViolationsTotals.getPointB()+discoveredSecurityViolationsTotals.getPointC();
        int fixedSecurityTotal = fixedSecurityViolationsTotals.getPointA()+fixedSecurityViolationsTotals.getPointB()+fixedSecurityViolationsTotals.getPointC();
        int waivedSecurityTotal = waivedSecurityViolationsTotals.getPointA()+waivedSecurityViolationsTotals.getPointB()+waivedSecurityViolationsTotals.getPointC();

        int discoveredLicenseTotal = discoveredLicenseViolationsTotals.getPointA()+discoveredLicenseViolationsTotals.getPointB()+discoveredLicenseViolationsTotals.getPointC();
        int fixedLicenseTotal = fixedLicenseViolationsTotals.getPointA()+fixedLicenseViolationsTotals.getPointB()+fixedLicenseViolationsTotals.getPointC();
        int waivedLicenseTotal = waivedLicenseViolationsTotals.getPointA()+waivedLicenseViolationsTotals.getPointB()+waivedLicenseViolationsTotals.getPointC();

        int fixedWaived = fixedSecurityTotal+waivedSecurityTotal+fixedLicenseTotal+waivedLicenseTotal;
        int discovered = discoveredSecurityTotal+discoveredLicenseTotal;

        float fixRate = (((float)(fixedWaived)/discovered) * 100);

        model.addAttribute("fixRate", String.format("%.0f", fixRate));

        model.addAttribute("mttrAvg", this.MttrAvg("current"));

        String applicationOpenViolations = SqlStatement.ApplicationsOpenViolations + " where time_period_start = '" + latestTimePeriod + "' group by application_name" + " order by 2 desc, 3 desc";
        List<DbRow> aov = dataService.runSql(applicationOpenViolations);

        model.addAttribute("mostCriticalApplicationCount", aov.get(0).getPointA());
        model.addAttribute("leastCriticalApplicationCount", aov.get(aov.size()-1).getPointA());

        model.addAttribute("openCriticalViolationsAvg", this.sumAndAveragePointA(aov)[1]);

        List<DbRow> securityViolations = dataService.runSql(SqlStatement.SecurityViolations);
        List<DbRow> discoveredSecurityViolations = dataService.runSql(SqlStatement.DiscoveredSecurityViolations);
        List<DbRow> openSecurityViolations = dataService.runSql(SqlStatement.OpenSecurityViolations);
        List<DbRow> fixedSecurityViolations = dataService.runSql(SqlStatement.FixedSecurityViolations);
        List<DbRow> waivedSecurityViolations = dataService.runSql(SqlStatement.WaivedSecurityViolations);

        model.addAttribute("securityViolations", securityViolations);
        model.addAttribute("discoveredSecurityViolations", discoveredSecurityViolations);
        model.addAttribute("openSecurityViolations", openSecurityViolations);
		model.addAttribute("fixedSecurityViolations", fixedSecurityViolations);
        model.addAttribute("waivedSecurityViolations", waivedSecurityViolations);
        
        List<DbRow> licenseViolations = dataService.runSql(SqlStatement.LicenseViolations);
        List<DbRow> discoveredLicenseViolations = dataService.runSql(SqlStatement.DiscoveredLicenseViolations);
        List<DbRow> openLicenseViolations = dataService.runSql(SqlStatement.OpenLicenseViolations);
        List<DbRow> fixedLicenseViolations = dataService.runSql(SqlStatement.FixedLicenseViolations);
        List<DbRow> waivedLicenseViolations = dataService.runSql(SqlStatement.WaivedLicenseViolations);

        model.addAttribute("LicenseViolations", licenseViolations);
        model.addAttribute("discoveredLicenseViolations", discoveredLicenseViolations);
        model.addAttribute("openLicenseViolations", openLicenseViolations);
		model.addAttribute("fixedLicenseViolations", fixedLicenseViolations);
		model.addAttribute("waivedLicenseViolations", waivedLicenseViolations);
		
		// Previous Period

        String pplatestTimePeriod = utilService.getPreviousPeriod();
        int ppPeriod = utilService.getPreviousPeriodRange();
        
        String endStr;
        
        if (ppPeriod < 2) {
        	endStr = ")";
        }
        else {
        	endStr = "s)";
        }
        
        String ppPeriodStr = "(" + pplatestTimePeriod + "/" + ppPeriod + " " + timePeriod + endStr;		
        model.addAttribute("ppPeriodStr", ppPeriodStr);

        List<DbRow> ppapplicationsOnboarded = dataService.runSql(SqlStatementPreviousPeriod.ApplicationsOnboarded);
        List<DbRow> ppnumberOfScans = dataService.runSql(SqlStatementPreviousPeriod.NumberOfScans);
        List<DbRow> ppnumberOfApplicationsScanned = dataService.runSql(SqlStatementPreviousPeriod.NumberOfApplicationsScanned);
        List<Mttr> ppmttr = dataService.runSqlMttr(SqlStatementPreviousPeriod.MTTR);

		model.addAttribute("ppapplicationsOnboarded", ppapplicationsOnboarded);
		model.addAttribute("ppnumberOfScans", ppnumberOfScans);
		model.addAttribute("ppnumberOfApplicationsScanned", ppnumberOfApplicationsScanned);
        model.addAttribute("ppmttr", ppmttr);

        model.addAttribute("ppapplicationsOnboardedAvg", sumAndAverageApplicationsOnboarded(ppapplicationsOnboarded));
		model.addAttribute("ppnumberOfScansAvg", sumAndAveragePointA(ppnumberOfScans));
		model.addAttribute("ppnumberOfApplicationsScannedAvg", sumAndAveragePointA(ppnumberOfApplicationsScanned));

        DbRow ppdiscoveredSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredSecurityViolationsTotals).get(0);
        DbRow ppopenSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.OpenSecurityViolationsTotals).get(0);
        DbRow ppfixedSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.FixedSecurityViolationsTotals).get(0);
        DbRow ppwaivedSecurityViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.WaivedSecurityViolationsTotals).get(0);

        model.addAttribute("ppdiscoveredSecurityViolationsTotals", ppdiscoveredSecurityViolationsTotals);
        model.addAttribute("ppopenSecurityViolationsTotals", ppopenSecurityViolationsTotals);
        model.addAttribute("ppfixedSecurityViolationsTotals", ppfixedSecurityViolationsTotals);
        model.addAttribute("ppwaivedSecurityViolationsTotals", ppwaivedSecurityViolationsTotals);

        DbRow ppdiscoveredLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredLicenseViolationsTotals).get(0);
        DbRow ppopenLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.OpenLicenseViolationsTotals).get(0);
        DbRow ppfixedLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.FixedLicenseViolationsTotals).get(0);
        DbRow ppwaivedLicenseViolationsTotals = dataService.runSql(SqlStatementPreviousPeriod.WaivedLicenseViolationsTotals).get(0);

        model.addAttribute("ppdiscoveredLicenseViolationsTotals", ppdiscoveredLicenseViolationsTotals);
        model.addAttribute("ppopenLicenseViolationsTotals", ppopenLicenseViolationsTotals);
        model.addAttribute("ppfixedLicenseViolationsTotals", ppfixedLicenseViolationsTotals);
        model.addAttribute("ppwaivedLicenseViolationsTotals", ppwaivedLicenseViolationsTotals);

        int ppdiscoveredSecurityTotal = ppdiscoveredSecurityViolationsTotals.getPointA()+ppdiscoveredSecurityViolationsTotals.getPointB()+ppdiscoveredSecurityViolationsTotals.getPointC();
        int ppfixedSecurityTotal = ppfixedSecurityViolationsTotals.getPointA()+ppfixedSecurityViolationsTotals.getPointB()+ppfixedSecurityViolationsTotals.getPointC();
        int ppwaivedSecurityTotal = ppwaivedSecurityViolationsTotals.getPointA()+ppwaivedSecurityViolationsTotals.getPointB()+ppwaivedSecurityViolationsTotals.getPointC();

        int ppdiscoveredLicenseTotal = ppdiscoveredLicenseViolationsTotals.getPointA()+ppdiscoveredLicenseViolationsTotals.getPointB()+ppdiscoveredLicenseViolationsTotals.getPointC();
        int ppfixedLicenseTotal = ppfixedLicenseViolationsTotals.getPointA()+ppfixedLicenseViolationsTotals.getPointB()+ppfixedLicenseViolationsTotals.getPointC();
        int ppwaivedLicenseTotal = ppwaivedLicenseViolationsTotals.getPointA()+ppwaivedLicenseViolationsTotals.getPointB()+ppwaivedLicenseViolationsTotals.getPointC();

        int ppfixedWaived = ppfixedSecurityTotal+ppwaivedSecurityTotal+ppfixedLicenseTotal+ppwaivedLicenseTotal;
        int ppdiscovered = ppdiscoveredSecurityTotal+ppdiscoveredLicenseTotal;

        float ppfixRate = (((float)(ppfixedWaived)/ppdiscovered) * 100);

        model.addAttribute("ppfixRate", String.format("%.0f", ppfixRate));

        model.addAttribute("ppmttrAvg", this.MttrAvg("previous"));

        String ppapplicationOpenViolations = SqlStatementPreviousPeriod.ApplicationsOpenViolations + " where time_period_start = '" + pplatestTimePeriod + "' group by application_name" + " order by 2 desc, 3 desc";
        List<DbRow> ppaov = dataService.runSql(ppapplicationOpenViolations);

        model.addAttribute("ppmostCriticalApplicationCount", ppaov.get(0).getPointA());
        model.addAttribute("ppleastCriticalApplicationCount", ppaov.get(ppaov.size()-1).getPointA());

        model.addAttribute("ppopenCriticalViolationsAvg", this.sumAndAveragePointA(ppaov)[1]);

        List<DbRow> ppsecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.SecurityViolations);
        List<DbRow> ppdiscoveredSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredSecurityViolations);
        List<DbRow> ppopenSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.OpenSecurityViolations);
        List<DbRow> ppfixedSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.FixedSecurityViolations);
        List<DbRow> ppwaivedSecurityViolations = dataService.runSql(SqlStatementPreviousPeriod.WaivedSecurityViolations);

        model.addAttribute("ppsecurityViolations", ppsecurityViolations);
        model.addAttribute("ppdiscoveredSecurityViolations", ppdiscoveredSecurityViolations);
        model.addAttribute("ppopenSecurityViolations", ppopenSecurityViolations);
		model.addAttribute("ppfixedSecurityViolations", ppfixedSecurityViolations);
        model.addAttribute("ppwaivedSecurityViolations", ppwaivedSecurityViolations);
        
        List<DbRow> pplicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.LicenseViolations);
        List<DbRow> ppdiscoveredLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.DiscoveredLicenseViolations);
        List<DbRow> ppopenLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.OpenLicenseViolations);
        List<DbRow> ppfixedLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.FixedLicenseViolations);
        List<DbRow> ppwaivedLicenseViolations = dataService.runSql(SqlStatementPreviousPeriod.WaivedLicenseViolations);

        model.addAttribute("ppLicenseViolations", pplicenseViolations);
        model.addAttribute("ppdiscoveredLicenseViolations", ppdiscoveredLicenseViolations);
        model.addAttribute("ppopenLicenseViolations", ppopenLicenseViolations);
		model.addAttribute("ppfixedLicenseViolations", ppfixedLicenseViolations);
		model.addAttribute("ppwaivedLicenseViolations", ppwaivedLicenseViolations);

        return "reportUnsigned";
    }

    private int[] sumAndAverageApplicationsOnboarded(List<DbRow> dataList) {
		
		int countLabels = dataList.size();
		int numberOfApplications = (int) dataList.get(dataList.size() - 1).getPointA();
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
