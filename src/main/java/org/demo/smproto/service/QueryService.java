package org.demo.smproto.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.Metric;
import org.demo.smproto.model.PolicyViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
	
	private static final Logger log = LoggerFactory.getLogger(QueryService.class);

	@Autowired
	private IMetricsRepositoryService metricsService;
	
	@Autowired 
	private IDataService dataService;
	
	@Autowired 
	private CalculatorService calculator;

	private String latestPeriod;
	
	
	public List<Metric> getAllMetrics() {
		return metricsService.findByOrderByTimePeriodStartAsc();
	}
	
	public List<DataPoint> getApplicationsOnboarded() {
		return this.dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationsOnboarded));
	}

	public List<DataPoint> getNumberOfScans(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.NumberOfScans));
	}
	
	public List<DataPoint> getApplicationScans(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationScans));
	}

	public String getLatestTimePeriodStart(){
		latestPeriod = dataService.executeSQL(SQLStatement.LatestTimePeriodStart).get(0).getLabel();
		log.info("latest Period: " + latestPeriod);
		return latestPeriod;
	}
	
	public List<DataPoint> getOrganisationsOpenViolations(){
		return dataService.getDataPoints(dataService.executeSQL(calculator.AddWhereClause(SQLStatement.OrganisationsOpenViolations, latestPeriod, "ORGANIZATION_NAME")));
	}
	
	public List<DataPoint> getApplicationCriticalViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationCriticalViolations));
	}
	
	public List<DataPoint> getMostScannedApplications(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.MostScannedApplications));
	}
	
	public List<DataPoint> getMTTR(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.MTTR));
	}
	
	public List<DataPoint> getCriticalSecurityViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.CriticalSecurityViolations));
	}

	public List<DataPoint> getSevereSecurityViolations(){
		 return dataService.getDataPoints(dataService.executeSQL(SQLStatement.SevereSecurityViolations));
	}

	public List<DataPoint> getModerateSecurityViolations(){
		 return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ModerateSecurityViolations));
	}

	public List<DataPoint> getDiscoveredSecurityViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredSecurityViolations));
	}

	public List<DataPoint> getFixedSecurityViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedSecurityViolations));
	}

	public List<DataPoint> getWaivedSecurityViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedSecurityViolations));
	}

	public List<DataPoint> getOpenSecurityViolations(){
		 return dataService.getDataPoints(dataService.executeSQL(SQLStatement.OpenSecurityViolations));
	}
	
	public List<DataPoint> getCriticalLicenseViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.CriticalLicenseViolations));
	}

	public List<DataPoint> getSevereLicenseViolations(){
		 return dataService.getDataPoints(dataService.executeSQL(SQLStatement.SevereLicenseViolations));
	}

	public List<DataPoint> getModerateLicenseViolations(){
		 return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ModerateLicenseViolations));
	}

	public List<DataPoint> getDiscoveredLicenseViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredLicenseViolations));
	}

	public List<DataPoint> getFixedLicenseViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedLicenseViolations));
	}

	public List<DataPoint> getWaivedLicenseViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedLicenseViolations));
	}

	public List<DataPoint> getOpenLicenseViolations(){
		 return dataService.getDataPoints(dataService.executeSQL(SQLStatement.OpenLicenseViolations));
	}

	public String getTimePeriod() throws ParseException {
		List<DataPoint> timePeriods = dataService.getDataPoints(dataService.executeSQL(SQLStatement.TimePeriods));
		
		long oneWeek = 604800000;
		
		String timePeriodLabel = "Week";
		String firstTimePeriod;
		String secondTimePeriod;
		
		if (timePeriods.size() > 1) {
			firstTimePeriod = timePeriods.get(0).getLabel().toString();
			secondTimePeriod = timePeriods.get(1).getLabel().toString();

			long fp = this.convertDateStr(firstTimePeriod);
			long sp = this.convertDateStr(secondTimePeriod);
			
			long diff = sp - fp;

			if (diff <= oneWeek) {
				timePeriodLabel = "week";
			}
			else {
				timePeriodLabel = "month";

			}
		}
		else {
			timePeriodLabel = "week";
		}
		
		return timePeriodLabel;
	}
	
	private Long convertDateStr(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		long millis = date.getTime();
		return millis;
	}
	
	public List<PolicyViolation> getPolicyViolationsAge90(){
		return dataService.getPolicyViolationPoints(dataService.executeSQL2(SQLStatement.PolicyViolationsAge90));
	}
	
	public List<PolicyViolation> getPolicyViolationsAge60(){
		return dataService.getPolicyViolationPoints(dataService.executeSQL2(SQLStatement.PolicyViolationsAge60));
	}
	
	public List<PolicyViolation> getPolicyViolationsAge30(){
		return dataService.getPolicyViolationPoints(dataService.executeSQL2(SQLStatement.PolicyViolationsAge30));
	}
	
}
