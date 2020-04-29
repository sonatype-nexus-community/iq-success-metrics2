package org.demo.smproto.model;

import org.springframework.context.annotation.Bean;

public class DataPoint {
	
	private String period;
    private int count;
    
	public DataPoint() {}

	public DataPoint(String period, int count) {
		this.period = period;
		this.count = count;
	}
	
	public String getPeriod() {
		return period;
	}
	
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "DataPoint [period=" + period + ", count=" + count + "]";
	}
}
