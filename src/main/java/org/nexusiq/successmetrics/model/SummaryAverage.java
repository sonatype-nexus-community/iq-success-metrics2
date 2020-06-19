package org.nexusiq.successmetrics.model;

public class SummaryAverage {
	
	public int total;
	public int avg;
	
	public SummaryAverage() {}
	
	public SummaryAverage(int numberOfApplications, int avg) {
		this.total = numberOfApplications;
		this.avg = avg;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getAvg() {
		return avg;
	}
	
	public void setAvg(int avg) {
		this.avg = avg;
	}
}