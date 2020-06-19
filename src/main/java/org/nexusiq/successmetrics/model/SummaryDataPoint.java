package org.nexusiq.successmetrics.model;

public class SummaryDataPoint {
	
	public String label;
	public int count;
	
	public SummaryDataPoint() {}
	
	public SummaryDataPoint(String label, int f) {
		this.label = label;
		this.count = f;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
