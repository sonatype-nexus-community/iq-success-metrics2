package org.sonatype.cs.metrics.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.sonatype.cs.metrics.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryDataService {

	@Autowired
	private DataService dataService;

	@Autowired
	private UtilService utilService;

	public Map<String, Object> getPeriodData() throws ParseException {
		Map<String, Object> model = new HashMap<>();

		String timePeriod = utilService.getTimePeriod();

		String latestTimePeriod = utilService.getLatestPeriod();
		String pplatestTimePeriod = utilService.getPreviousPeriod();

		int ppPeriod = utilService.getPreviousPeriodRange();

		String endStr = "";

		if (ppPeriod > 1) {
			endStr = "s";
		}

		String ppPeriodStr = "/" + ppPeriod + " " + timePeriod + endStr;

		String[] previousPeriodDateRange = utilService.getDateRangeForPeriod("previous");
		String[] currentPeriodDateRange = utilService.getDateRangeForPeriod("current");

		model.put("startPeriod", currentPeriodDateRange[0]);

		model.put("currentPeriodDateRange", "(" + currentPeriodDateRange[0] + " - " + currentPeriodDateRange[1] + ")");
		model.put("previousPeriodDateRange",
				"(" + previousPeriodDateRange[0] + " - " + previousPeriodDateRange[1] + ppPeriodStr + ")");

		model.put("timePeriod", timePeriod);
		model.put("latestTimePeriod", latestTimePeriod);
		model.put("pplatestTimePeriod", pplatestTimePeriod);

		return model;
	}

	public Map<String, Object> getApplicationData(String periodDateRange) {
		Map<String, Object> model = new HashMap<>();

		List<DbRow> applicationsOnboarded = dataService.runSql(SqlStatement.ApplicationsOnboarded);
		int numberOfApplicationsStartPeriod = getNumberofApplicationsStartPeriod(periodDateRange);

		List<DbRow> numberOfScans = dataService.runSql(SqlStatement.NumberOfScans);
		List<DbRow> numberOfApplicationsScanned = dataService.runSql(SqlStatement.NumberOfApplicationsScanned);
		List<Mttr> mttr = dataService.runSqlMttr(SqlStatement.MTTR);

		model.put("applicationsOnboarded", applicationsOnboarded);
		model.put("numberOfScans", numberOfScans);
		model.put("numberOfApplicationsScanned", numberOfApplicationsScanned);
		model.put("mttr", mttr);

		model.put("applicationsOnboardedAvg",
				sumAndAverageApplicationsOnboarded(applicationsOnboarded, numberOfApplicationsStartPeriod));
		model.put("numberOfScansAvg", sumAndAveragePointA(numberOfScans));
		model.put("numberOfApplicationsScannedAvg", sumAndAveragePointA(numberOfApplicationsScanned));

		return model;
	}

	public Map<String, Object> getSecurityViolationsTotals() {
		Map<String, Object> model = new HashMap<>();

		DbRow discoveredSecurityViolationsTotals = dataService.runSql(SqlStatement.DiscoveredSecurityViolationsTotals)
				.get(0);
		DbRow openSecurityViolationsTotals = dataService.runSql(SqlStatement.OpenSecurityViolationsTotals).get(0);
		DbRow fixedSecurityViolationsTotals = dataService.runSql(SqlStatement.FixedSecurityViolationsTotals).get(0);
		DbRow waivedSecurityViolationsTotals = dataService.runSql(SqlStatement.WaivedSecurityViolationsTotals).get(0);

		model.put("discoveredSecurityViolationsTotals", discoveredSecurityViolationsTotals);
		model.put("openSecurityViolationsTotals", openSecurityViolationsTotals);
		model.put("fixedSecurityViolationsTotals", fixedSecurityViolationsTotals);
		model.put("waivedSecurityViolationsTotals", waivedSecurityViolationsTotals);

		int discoveredSecurityTotal = discoveredSecurityViolationsTotals.getPointA()
				+ discoveredSecurityViolationsTotals.getPointB() + discoveredSecurityViolationsTotals.getPointC();
		int fixedSecurityTotal = fixedSecurityViolationsTotals.getPointA() + fixedSecurityViolationsTotals.getPointB()
				+ fixedSecurityViolationsTotals.getPointC();
		int waivedSecurityTotal = waivedSecurityViolationsTotals.getPointA() + waivedSecurityViolationsTotals.getPointB()
				+ waivedSecurityViolationsTotals.getPointC();

		model.put("discoveredSecurityTotal", discoveredSecurityTotal);
		model.put("fixedSecurityTotal", fixedSecurityTotal);
		model.put("waivedSecurityTotal", waivedSecurityTotal);

		return model;
	}

	public Map<String, Object> getLicenseViolationsTotals() {
		Map<String, Object> model = new HashMap<>();

		DbRow discoveredLicenseViolationsTotals = dataService.runSql(SqlStatement.DiscoveredLicenseViolationsTotals).get(0);
		DbRow openLicenseViolationsTotals = dataService.runSql(SqlStatement.OpenLicenseViolationsTotals).get(0);
		DbRow fixedLicenseViolationsTotals = dataService.runSql(SqlStatement.FixedLicenseViolationsTotals).get(0);
		DbRow waivedLicenseViolationsTotals = dataService.runSql(SqlStatement.WaivedLicenseViolationsTotals).get(0);

		model.put("discoveredLicenseViolationsTotals", discoveredLicenseViolationsTotals);
		model.put("openLicenseViolationsTotals", openLicenseViolationsTotals);
		model.put("fixedLicenseViolationsTotals", fixedLicenseViolationsTotals);
		model.put("waivedLicenseViolationsTotals", waivedLicenseViolationsTotals);

		int discoveredLicenseTotal = discoveredLicenseViolationsTotals.getPointA()
				+ discoveredLicenseViolationsTotals.getPointB() + discoveredLicenseViolationsTotals.getPointC();
		int fixedLicenseTotal = fixedLicenseViolationsTotals.getPointA() + fixedLicenseViolationsTotals.getPointB()
				+ fixedLicenseViolationsTotals.getPointC();
		int waivedLicenseTotal = waivedLicenseViolationsTotals.getPointA() + waivedLicenseViolationsTotals.getPointB()
				+ waivedLicenseViolationsTotals.getPointC();

		model.put("discoveredLicenseTotal", discoveredLicenseTotal);
		model.put("fixedLicenseTotal", fixedLicenseTotal);
		model.put("waivedLicenseTotal", waivedLicenseTotal);

		return model;
	}

	public Map<String, Object> getSecurityLicenseTotals() {
		Map<String, Object> model = new HashMap<>();

		Map<String, Object> secModel = this.getSecurityViolationsTotals();
		Map<String, Object> licModel = this.getLicenseViolationsTotals();

		int dst = (int) secModel.get("discoveredSecurityTotal");
		int fst = (int) secModel.get("fixedSecurityTotal");
		int wst = (int) secModel.get("waivedSecurityTotal");

		int dlt = (int) licModel.get("discoveredLicenseTotal");
		int flt = (int) licModel.get("fixedLicenseTotal");
		int wlt = (int) licModel.get("waivedLicenseTotal");

		int fixedWaived = fst + wst + flt + wlt;
		int discovered = dst + dlt;

		float fixRate = (((float) (fixedWaived) / discovered) * 100);

		model.put("fixRate", String.format("%.0f", fixRate));
		model.put("mttrAvg", this.MttrAvg("current"));

		return model;
	}

	public Map<String, Object> getViolationsData(String latestTimePeriod) {
		Map<String, Object> model = new HashMap<>();

		String applicationOpenViolations = SqlStatement.ApplicationsOpenViolations + " where time_period_start = '"
				+ latestTimePeriod + "' group by application_name" + " order by 2 desc, 3 desc";

		List<DbRow> aov = dataService.runSql(applicationOpenViolations);

		model.put("mostCriticalApplicationCount", aov.get(0).getPointA());
		model.put("leastCriticalApplicationCount", aov.get(aov.size() - 1).getPointA());

		model.put("openCriticalViolationsAvg", this.sumAndAveragePointA(aov)[1]);

		List<DbRow> securityViolations = dataService.runSql(SqlStatement.SecurityViolations);
		List<DbRow> discoveredSecurityViolations = dataService.runSql(SqlStatement.DiscoveredSecurityViolations);
		List<DbRow> openSecurityViolations = dataService.runSql(SqlStatement.OpenSecurityViolations);
		List<DbRow> fixedSecurityViolations = dataService.runSql(SqlStatement.FixedSecurityViolations);
		List<DbRow> waivedSecurityViolations = dataService.runSql(SqlStatement.WaivedSecurityViolations);

		model.put("securityViolations", securityViolations);
		model.put("discoveredSecurityViolations", discoveredSecurityViolations);
		model.put("openSecurityViolations", openSecurityViolations);
		model.put("fixedSecurityViolations", fixedSecurityViolations);
		model.put("waivedSecurityViolations", waivedSecurityViolations);

		List<DbRow> licenseViolations = dataService.runSql(SqlStatement.LicenseViolations);
		List<DbRow> discoveredLicenseViolations = dataService.runSql(SqlStatement.DiscoveredLicenseViolations);
		List<DbRow> openLicenseViolations = dataService.runSql(SqlStatement.OpenLicenseViolations);
		List<DbRow> fixedLicenseViolations = dataService.runSql(SqlStatement.FixedLicenseViolations);
		List<DbRow> waivedLicenseViolations = dataService.runSql(SqlStatement.WaivedLicenseViolations);

		model.put("LicenseViolations", licenseViolations);
		model.put("discoveredLicenseViolations", discoveredLicenseViolations);
		model.put("openLicenseViolations", openLicenseViolations);
		model.put("fixedLicenseViolations", fixedLicenseViolations);
		model.put("waivedLicenseViolations", waivedLicenseViolations);

		List<DbRow> riskRatio = dataService.runSql(SqlStatement.RiskRatio);
		model.put("riskRatio", riskRatio);

		return model;
	}

	/* */

	private int getNumberofApplicationsStartPeriod(String timePeriod) {
		String sqlStmt = "select 'Number of Apps' as label, count(application_Id) as pointA from metric where time_period_start = '"
				+ timePeriod + "'";
		List<DbRow> rows = dataService.runSql(sqlStmt);
		int numberOfApplications = rows.get(0).getPointA();
		return numberOfApplications;
	}

	private int[] sumAndAverageApplicationsOnboarded(List<DbRow> dataList, int numberOfApplicationsStartPeriod) {

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

	private int[] sumAndAverageApplicationsOnboarded2(List<DbRow> dataList, int numberOfApplicationsStartPeriod) {

		int countLabels = dataList.size();
		int numberOfApplications = (int) dataList.get(dataList.size() - 1).getPointA();
		numberOfApplications -= numberOfApplicationsStartPeriod;
		int dataAverage = numberOfApplications / countLabels;

		int total = numberOfApplications;
		int avg = dataAverage;

		int[] values = new int[] { total, avg };
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
		int avg = sumData / countLabels;

		int[] values = new int[] { total, avg };
		return values;
	}

	private String[] MttrAvg(String period) {
		List<Float> pointA = new ArrayList<>();
		List<Float> pointB = new ArrayList<>();
		List<Float> pointC = new ArrayList<>();

		String sqlStmt;

		if (period == "current") {
			sqlStmt = SqlStatement.MTTR2;
		} else {
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

		String[] values = new String[] { mttrCriticalAvg, mttrSevereAvg, mttrModerateAvg };
		return values;
	}

	private List<Mttr> getMttr(String sqlStmt) {

		List<Mttr> mttr = new ArrayList<Mttr>();

		List<Mttr> points = dataService.runSqlMttr(sqlStmt);

		for (Mttr dp : points) {
			Mttr cp = new Mttr();
			cp.setLabel(dp.getLabel());
			cp.setPointA(dp.getPointA() / 86400000);
			cp.setPointB(dp.getPointB() / 86400000);
			cp.setPointC(dp.getPointC() / 86400000);

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

		return sumData / countPoints;
	}

}
