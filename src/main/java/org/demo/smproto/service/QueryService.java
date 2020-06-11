package org.demo.smproto.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.MTTRPoint;
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
	
	public List<DataPoint> getOrganisationsOpenViolations(){
		String latestTimePeriod = dataService.latestPeriod();
		return dataService.getDataPoints(dataService.executeSQL(calculator.AddWhereClauseOrgOpenViolations(SQLStatement.OrganisationsOpenSecurityViolations, latestTimePeriod, "ORGANIZATION_NAME")));
	}
	
	public List<DataPoint> getApplicationsOpenViolations(){
		String latestTimePeriod = dataService.latestPeriod();
		return dataService.getDataPoints(dataService.executeSQL(calculator.AddWhereClauseAppOpenViolations(SQLStatement.ApplicationsOpenSecurityViolations, latestTimePeriod, "APPLICATION_NAME")));
	}
	
//	public List<DataPoint> getApplicationSecurityViolations(){
//		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationSecurityViolations));
//	}
//	
//	public List<DataPoint> getApplicationLicenseViolations(){
//		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationLicenseViolations));
//	}
	
	public List<DataPoint> getApplicationViolations(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationViolations));
	}
	
	public List<DataPoint> getMostScannedApplications(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.MostScannedApplications));
	}
	
	public List<MTTRPoint> getMTTR(){
		return dataService.getMTTRPoints(dataService.executeSQL3(SQLStatement.MTTR));
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
		String latestTimePeriod = dataService.latestPeriod();
		return dataService.getDataPoints(dataService.executeSQL(calculator.AddWhereClauseAppOpenViolations(SQLStatement.OpenSecurityViolations, latestTimePeriod, "APPLICATION_NAME")));
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
		String latestTimePeriod = dataService.latestPeriod();
		return dataService.getDataPoints(dataService.executeSQL(calculator.AddWhereClauseAppOpenViolations(SQLStatement.OpenLicenseViolations, latestTimePeriod, "APPLICATION_NAME")));
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
	
	public List<DataPoint> getApplicationsSecurityCriticalStatus(){
		return dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationsSecurityCriticalStatus));
	}
}
