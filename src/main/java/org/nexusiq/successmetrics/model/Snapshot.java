package org.nexusiq.successmetrics.model;

import java.util.Arrays;

public class Snapshot {
	
	private String startPeriod;
	private String endPeriod;
	private String[] applicationName;
	
	public Snapshot() {}

	public String getStartPeriod() {
		return startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String[] getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String[] applicationName) {
		this.applicationName = applicationName;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	@Override
	public String toString() {
		return "Snapshot [startPeriod=" + startPeriod + ", endPeriod=" + endPeriod + ", applicationName="
				+ Arrays.toString(applicationName) + "]";
	}
	
}
