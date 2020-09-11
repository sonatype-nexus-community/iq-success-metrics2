package org.nexusiq.successmetrics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import com.opencsv.bean.CsvBindByName;


@Entity
@Table(name = "metrics")
public class Metric {
	
	@Id	
	//@GeneratedValue
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	
	@CsvBindByName
	private String applicationId;
	@CsvBindByName
	private String applicationPublicId;
	@CsvBindByName
	private String applicationName;
	@CsvBindByName
	private String organizationName;

	@CsvBindByName
	private String timePeriodStart;
	
	@CsvBindByName
	private int evaluationCount;
	
	@CsvBindByName
	@Column(name = "mttrModerateThreat", updatable = false, nullable = true)
	private Float mttrModerateThreat;
	
	@CsvBindByName
	@Column(name = "mttrSevereThreat", updatable = false, nullable = true)
	private Float mttrSevereThreat;
	
	@CsvBindByName
	@Column(name = "mttrCriticalThreat", updatable = false, nullable = true)
	private Float mttrCriticalThreat;

	@CsvBindByName
	private int discoveredCountSecurityModerate;
	@CsvBindByName
	private int discoveredCountSecuritySevere;
	@CsvBindByName
	private int discoveredCountSecurityCritical;
	@CsvBindByName
	private int discoveredCountLicenseModerate;
	@CsvBindByName
	private int discoveredCountLicenseSevere;
	@CsvBindByName
	private int discoveredCountLicenseCritical;

	@CsvBindByName
	private int fixedCountSecurityModerate;
	@CsvBindByName
	private int fixedCountSecuritySevere;
	@CsvBindByName
	private int fixedCountSecurityCritical;
	@CsvBindByName
	private int fixedCountLicenseModerate;
	@CsvBindByName
	private int fixedCountLicenseSevere;
	@CsvBindByName
	private int fixedCountLicenseCritical;

	@CsvBindByName
	private int waivedCountSecurityModerate;
	@CsvBindByName
	private int waivedCountSecuritySevere;
	@CsvBindByName
	private int waivedCountSecurityCritical;
	@CsvBindByName
	private int waivedCountLicenseModerate;	
	@CsvBindByName
	private int waivedCountLicenseSevere;
	@CsvBindByName
	private int waivedCountLicenseCritical;
	
	@CsvBindByName
	private int openCountAtTimePeriodEndSecurityModerate;
	@CsvBindByName
	private int openCountAtTimePeriodEndSecuritySevere;
	@CsvBindByName
	private int openCountAtTimePeriodEndSecurityCritical;
	@CsvBindByName
	private int openCountAtTimePeriodEndLicenseModerate;
	@CsvBindByName
	private int openCountAtTimePeriodEndLicenseSevere;
	@CsvBindByName
	private int openCountAtTimePeriodEndLicenseCritical;
	
	
	public Metric() {}
	
	public Metric (
			String applicationId, String applicationPublicId, String applicationName, String organizationId, String organizationName, String timePeriodStart, 
			int evaluationCount, 
			Float mttrLowThreat, Float mttrModerateThreat, Float mttrSevereThreat, Float mttrCriticalThreat, 
			
			int discoveredCountSecurityLow, int discoveredCountSecurityModerate, int discoveredCountSecuritySevere, int discoveredCountSecurityCritical, 
			int discoveredCountLicenseLow, int discoveredCountLicenseModerate, int discoveredCountLicenseSevere, int discoveredCountLicenseCritical, 
			int discoveredCountQualityLow, int discoveredCountQualityModerate, int discoveredCountQualitySevere, int discoveredCountQualityCritical, 
			int discoveredCountOtherLow, int discoveredCountOtherModerate, int discoveredCountOtherSevere, int discoveredCountOtherCritical, 
			
			int fixedCountSecurityLow, int fixedCountSecurityModerate, int fixedCountSecuritySevere, int fixedCountSecurityCritical, 
			int fixedCountLicenseLow, int fixedCountLicenseModerate, int fixedCountLicenseSevere, int fixedCountLicenseCritical, 
			int fixedCountQualityLow, int fixedCountQualityModerate, int fixedCountQualitySevere, int fixedCountQualityCritical, 
			int fixedCountOtherLow, int fixedCountOtherModerate, int fixedCountOtherSevere, int fixedCountOtherCritical, 
			
			int waivedCountSecurityLow, int waivedCountSecurityModerate, int waivedCountSecuritySevere, int waivedCountSecurityCritical, 
			int waivedCountLicenseLow, int waivedCountLicenseModerate, int waivedCountLicenseSevere, int waivedCountLicenseCritical, 
			int waivedCountQualityLow, int waivedCountQualityModerate, int waivedCountQualitySevere, int waivedCountQualityCritical, 
			int waivedCountOtherLow, int waivedCountOtherModerate, int waivedCountOtherSevere, int waivedCountOtherCritical, 
			
			int openCountAtTimePeriodEndSecurityLow, int openCountAtTimePeriodEndSecurityModerate, int openCountAtTimePeriodEndSecuritySevere, int openCountAtTimePeriodEndSecurityCritical, 
			int openCountAtTimePeriodEndLicenseLow, int openCountAtTimePeriodEndLicenseModerate, int openCountAtTimePeriodEndLicenseSevere, int openCountAtTimePeriodEndLicenseCritical, 
			int openCountAtTimePeriodEndQualityLow, int openCountAtTimePeriodEndQualityModerate, int openCountAtTimePeriodEndQualitySevere, int openCountAtTimePeriodEndQualityCritical, 
			int openCountAtTimePeriodEndOtherLow, int openCountAtTimePeriodEndOtherModerate, int openCountAtTimePeriodEndOtherSevere, int openCountAtTimePeriodEndOtherCritical
		) {
		
		this.applicationId = applicationId;
		this.applicationPublicId = applicationPublicId;
		this.applicationName = applicationName;
 		this.organizationName = organizationName;
		
		this.timePeriodStart = timePeriodStart;
		
		this.evaluationCount = evaluationCount;
		
 		this.mttrModerateThreat = mttrModerateThreat;
		this.mttrSevereThreat = mttrSevereThreat;
		this.mttrCriticalThreat = mttrCriticalThreat;
		
 		this.discoveredCountSecurityModerate = discoveredCountSecurityModerate;
		this.discoveredCountSecuritySevere = discoveredCountSecuritySevere;
		this.discoveredCountSecurityCritical = discoveredCountSecurityCritical;
 		this.discoveredCountLicenseModerate = discoveredCountLicenseModerate;
		this.discoveredCountLicenseSevere = discoveredCountLicenseSevere;
		this.discoveredCountLicenseCritical = discoveredCountLicenseCritical;
		
 		this.fixedCountSecurityModerate = fixedCountSecurityModerate;
		this.fixedCountSecuritySevere = fixedCountSecuritySevere;
		this.fixedCountSecurityCritical = fixedCountSecurityCritical;
 		this.fixedCountLicenseModerate = fixedCountLicenseModerate;
		this.fixedCountLicenseSevere = fixedCountLicenseSevere;
		this.fixedCountLicenseCritical = fixedCountLicenseCritical;
		
 		this.waivedCountSecurityModerate = waivedCountSecurityModerate;
		this.waivedCountSecuritySevere = waivedCountSecuritySevere;
		this.waivedCountSecurityCritical = waivedCountSecurityCritical;
 		this.waivedCountLicenseModerate = waivedCountLicenseModerate;
		this.waivedCountLicenseSevere = waivedCountLicenseSevere;
		this.waivedCountLicenseCritical = waivedCountLicenseCritical;
		
 		this.openCountAtTimePeriodEndSecurityModerate = openCountAtTimePeriodEndSecurityModerate;
		this.openCountAtTimePeriodEndSecuritySevere = openCountAtTimePeriodEndSecuritySevere;
		this.openCountAtTimePeriodEndSecurityCritical = openCountAtTimePeriodEndSecurityCritical;
 		this.openCountAtTimePeriodEndLicenseModerate = openCountAtTimePeriodEndLicenseModerate;
		this.openCountAtTimePeriodEndLicenseSevere = openCountAtTimePeriodEndLicenseSevere;
		this.openCountAtTimePeriodEndLicenseCritical = openCountAtTimePeriodEndLicenseCritical;

	}

	public Metric(Metric m) {
		this.applicationId = m.getApplicationId();
		this.applicationPublicId = m.getApplicationPublicId();
		this.applicationName = m.getApplicationName();
		this.organizationName = m.getOrganizationName();
		
		this.timePeriodStart = m.getTimePeriodStart();
		
		this.evaluationCount = m.getEvaluationCount();
		
		this.mttrModerateThreat = m.mttrModerateThreat;
		this.mttrSevereThreat = m.mttrSevereThreat;
		this.mttrCriticalThreat = m.mttrCriticalThreat;
		
		this.discoveredCountSecurityModerate = m.discoveredCountSecurityModerate;
		this.discoveredCountSecuritySevere = m.discoveredCountSecuritySevere;
		this.discoveredCountSecurityCritical = m.discoveredCountSecurityCritical;
		this.discoveredCountLicenseModerate = m.discoveredCountLicenseModerate;
		this.discoveredCountLicenseSevere = m.discoveredCountLicenseSevere;
		this.discoveredCountLicenseCritical = m.discoveredCountLicenseCritical;
		
		this.fixedCountSecurityModerate = m.fixedCountSecurityModerate;
		this.fixedCountSecuritySevere = m.fixedCountSecuritySevere;
		this.fixedCountSecurityCritical = m.fixedCountSecurityCritical;
		this.fixedCountLicenseModerate = m.fixedCountLicenseModerate;
		this.fixedCountLicenseSevere = m.fixedCountLicenseSevere;
		this.fixedCountLicenseCritical = m.fixedCountLicenseCritical;
		
		this.waivedCountSecurityModerate = m.waivedCountSecurityModerate;
		this.waivedCountSecuritySevere = m.waivedCountSecuritySevere;
		this.waivedCountSecurityCritical = m.waivedCountSecurityCritical;
 		this.waivedCountLicenseModerate = m.waivedCountLicenseModerate;
		this.waivedCountLicenseSevere = m.waivedCountLicenseSevere;
		this.waivedCountLicenseCritical = m.waivedCountLicenseCritical;
	 
 		this.openCountAtTimePeriodEndSecurityModerate = m.openCountAtTimePeriodEndSecurityModerate;
		this.openCountAtTimePeriodEndSecuritySevere = m.openCountAtTimePeriodEndSecuritySevere;
		this.openCountAtTimePeriodEndSecurityCritical = m.openCountAtTimePeriodEndSecurityCritical;
 		this.openCountAtTimePeriodEndLicenseModerate = m.openCountAtTimePeriodEndLicenseModerate;
		this.openCountAtTimePeriodEndLicenseSevere = m.openCountAtTimePeriodEndLicenseSevere;
		this.openCountAtTimePeriodEndLicenseCritical = m.openCountAtTimePeriodEndLicenseCritical;
	}

	@Override
	public String toString() {
		return "Metric [applicationId=" + applicationId + ", applicationPublicId=" + applicationPublicId
				+ ", applicationName=" + applicationName + ", organizationName="
				+ organizationName + ", timePeriodStart=" + timePeriodStart + ", evaluationCount=" + evaluationCount
				+ "]";
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationPublicId() {
		return applicationPublicId;
	}

	public void setApplicationPublicId(String applicationPublicId) {
		this.applicationPublicId = applicationPublicId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTimePeriodStart() {
		return timePeriodStart;
	}

	public void setTimePeriodStart(String timePeriodStart) {
		this.timePeriodStart = timePeriodStart;
	}

	public int getEvaluationCount() {
		return evaluationCount;
	}

	public void setEvaluationCount(int evaluationCount) {
		this.evaluationCount = evaluationCount;
	}

	public Float getMttrModerateThreat() {
		return mttrModerateThreat;
	}

	public void setMttrModerateThreat(Float mttrModerateThreat) {
		this.mttrModerateThreat = mttrModerateThreat;
	}

	public Float getMttrSevereThreat() {
		return mttrSevereThreat;
	}

	public void setMttrSevereThreat(Float mttrSevereThreat) {
		this.mttrSevereThreat = mttrSevereThreat;
	}

	public Float getMttrCriticalThreat() {
		return mttrCriticalThreat;
	}

	public void setMttrCriticalThreat(Float mttrCriticalThreat) {
		this.mttrCriticalThreat = mttrCriticalThreat;
	}

	public int getDiscoveredCountSecurityModerate() {
		return discoveredCountSecurityModerate;
	}

	public void setDiscoveredCountSecurityModerate(int discoveredCountSecurityModerate) {
		this.discoveredCountSecurityModerate = discoveredCountSecurityModerate;
	}

	public int getDiscoveredCountSecuritySevere() {
		return discoveredCountSecuritySevere;
	}

	public void setDiscoveredCountSecuritySevere(int discoveredCountSecuritySevere) {
		this.discoveredCountSecuritySevere = discoveredCountSecuritySevere;
	}

	public int getDiscoveredCountSecurityCritical() {
		return discoveredCountSecurityCritical;
	}

	public void setDiscoveredCountSecurityCritical(int discoveredCountSecurityCritical) {
		this.discoveredCountSecurityCritical = discoveredCountSecurityCritical;
	}

	public int getDiscoveredCountLicenseModerate() {
		return discoveredCountLicenseModerate;
	}

	public void setDiscoveredCountLicenseModerate(int discoveredCountLicenseModerate) {
		this.discoveredCountLicenseModerate = discoveredCountLicenseModerate;
	}

	public int getDiscoveredCountLicenseSevere() {
		return discoveredCountLicenseSevere;
	}

	public void setDiscoveredCountLicenseSevere(int discoveredCountLicenseSevere) {
		this.discoveredCountLicenseSevere = discoveredCountLicenseSevere;
	}

	public int getDiscoveredCountLicenseCritical() {
		return discoveredCountLicenseCritical;
	}

	public void setDiscoveredCountLicenseCritical(int discoveredCountLicenseCritical) {
		this.discoveredCountLicenseCritical = discoveredCountLicenseCritical;
	}

	public int getFixedCountSecurityModerate() {
		return fixedCountSecurityModerate;
	}

	public void setFixedCountSecurityModerate(int fixedCountSecurityModerate) {
		this.fixedCountSecurityModerate = fixedCountSecurityModerate;
	}

	public int getFixedCountSecuritySevere() {
		return fixedCountSecuritySevere;
	}

	public void setFixedCountSecuritySevere(int fixedCountSecuritySevere) {
		this.fixedCountSecuritySevere = fixedCountSecuritySevere;
	}

	public int getFixedCountSecurityCritical() {
		return fixedCountSecurityCritical;
	}

	public void setFixedCountSecurityCritical(int fixedCountSecurityCritical) {
		this.fixedCountSecurityCritical = fixedCountSecurityCritical;
	}

	public int getFixedCountLicenseModerate() {
		return fixedCountLicenseModerate;
	}

	public void setFixedCountLicenseModerate(int fixedCountLicenseModerate) {
		this.fixedCountLicenseModerate = fixedCountLicenseModerate;
	}

	public int getFixedCountLicenseSevere() {
		return fixedCountLicenseSevere;
	}

	public void setFixedCountLicenseSevere(int fixedCountLicenseSevere) {
		this.fixedCountLicenseSevere = fixedCountLicenseSevere;
	}

	public int getFixedCountLicenseCritical() {
		return fixedCountLicenseCritical;
	}

	public void setFixedCountLicenseCritical(int fixedCountLicenseCritical) {
		this.fixedCountLicenseCritical = fixedCountLicenseCritical;
	}

	public int getWaivedCountSecurityModerate() {
		return waivedCountSecurityModerate;
	}

	public void setWaivedCountSecurityModerate(int waivedCountSecurityModerate) {
		this.waivedCountSecurityModerate = waivedCountSecurityModerate;
	}

	public int getWaivedCountSecuritySevere() {
		return waivedCountSecuritySevere;
	}

	public void setWaivedCountSecuritySevere(int waivedCountSecuritySevere) {
		this.waivedCountSecuritySevere = waivedCountSecuritySevere;
	}

	public int getWaivedCountSecurityCritical() {
		return waivedCountSecurityCritical;
	}

	public void setWaivedCountSecurityCritical(int waivedCountSecurityCritical) {
		this.waivedCountSecurityCritical = waivedCountSecurityCritical;
	}

	public int getWaivedCountLicenseModerate() {
		return waivedCountLicenseModerate;
	}

	public void setWaivedCountLicenseModerate(int waivedCountLicenseModerate) {
		this.waivedCountLicenseModerate = waivedCountLicenseModerate;
	}

	public int getWaivedCountLicenseSevere() {
		return waivedCountLicenseSevere;
	}

	public void setWaivedCountLicenseSevere(int waivedCountLicenseSevere) {
		this.waivedCountLicenseSevere = waivedCountLicenseSevere;
	}

	public int getWaivedCountLicenseCritical() {
		return waivedCountLicenseCritical;
	}

	public void setWaivedCountLicenseCritical(int waivedCountLicenseCritical) {
		this.waivedCountLicenseCritical = waivedCountLicenseCritical;
	}

	public int getOpenCountAtTimePeriodEndSecurityModerate() {
		return openCountAtTimePeriodEndSecurityModerate;
	}

	public void setOpenCountAtTimePeriodEndSecurityModerate(int openCountAtTimePeriodEndSecurityModerate) {
		this.openCountAtTimePeriodEndSecurityModerate = openCountAtTimePeriodEndSecurityModerate;
	}

	public int getOpenCountAtTimePeriodEndSecuritySevere() {
		return openCountAtTimePeriodEndSecuritySevere;
	}

	public void setOpenCountAtTimePeriodEndSecuritySevere(int openCountAtTimePeriodEndSecuritySevere) {
		this.openCountAtTimePeriodEndSecuritySevere = openCountAtTimePeriodEndSecuritySevere;
	}

	public int getOpenCountAtTimePeriodEndSecurityCritical() {
		return openCountAtTimePeriodEndSecurityCritical;
	}

	public void setOpenCountAtTimePeriodEndSecurityCritical(int openCountAtTimePeriodEndSecurityCritical) {
		this.openCountAtTimePeriodEndSecurityCritical = openCountAtTimePeriodEndSecurityCritical;
	}

	public int getOpenCountAtTimePeriodEndLicenseModerate() {
		return openCountAtTimePeriodEndLicenseModerate;
	}

	public void setOpenCountAtTimePeriodEndLicenseModerate(int openCountAtTimePeriodEndLicenseModerate) {
		this.openCountAtTimePeriodEndLicenseModerate = openCountAtTimePeriodEndLicenseModerate;
	}

	public int getOpenCountAtTimePeriodEndLicenseSevere() {
		return openCountAtTimePeriodEndLicenseSevere;
	}

	public void setOpenCountAtTimePeriodEndLicenseSevere(int openCountAtTimePeriodEndLicenseSevere) {
		this.openCountAtTimePeriodEndLicenseSevere = openCountAtTimePeriodEndLicenseSevere;
	}

	public int getOpenCountAtTimePeriodEndLicenseCritical() {
		return openCountAtTimePeriodEndLicenseCritical;
	}

	public void setOpenCountAtTimePeriodEndLicenseCritical(int openCountAtTimePeriodEndLicenseCritical) {
		this.openCountAtTimePeriodEndLicenseCritical = openCountAtTimePeriodEndLicenseCritical;
	}

	

	
	
	
}