package org.demo.smproto.model;

public class DataPointMulti {
	
	private String period;
    private int countA;
    private int countB;
    private int countC;
    
    public DataPointMulti() {}

	public DataPointMulti(String period, int countA, int countB, int countC) {
		super();
		this.period = period;
		this.countA = countA;
		this.countB = countB;
		this.countC = countC;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getCountA() {
		return countA;
	}

	public void setCountA(int countA) {
		this.countA = countA;
	}

	public int getCountB() {
		return countB;
	}

	public void setCountB(int countB) {
		this.countB = countB;
	}

	public int getCountC() {
		return countC;
	}

	public void setCountC(int countC) {
		this.countC = countC;
	}

	@Override
	public String toString() {
		return "DataPointMulti [period=" + period + ", countA=" + countA + ", countB=" + countB + ", countC=" + countC
				+ "]";
	}
	
}
