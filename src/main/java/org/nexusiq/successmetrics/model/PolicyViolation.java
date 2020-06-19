package org.nexusiq.successmetrics.model;

public class PolicyViolation {

	private String policyName;
	private String applicationName;
	private String openTime;
	private String component;
	
	public PolicyViolation() {}

	public PolicyViolation(String policyName, String applicationName, String openTime, String component) {
		this.policyName = policyName;
		this.applicationName = applicationName;
		this.openTime = openTime;
		this.component = component;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@Override
	public String toString() {
		return "PolicyViolation [policyName=" + policyName + ", applicationName=" + applicationName + ", openTime="
				+ openTime + ", component=" + component + "]";
	}
}
