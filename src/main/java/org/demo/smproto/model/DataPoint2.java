package org.demo.smproto.model;

public class DataPoint2 {
	
	private int countA;
    private int countB;
    
    public DataPoint2() {}
    
	public DataPoint2(int countA, int countB) {
		super();
		this.countA = countA;
		this.countB = countB;
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

	@Override
	public String toString() {
		return "DataPoint2 [countA=" + countA + ", countB=" + countB + "]";
	}
    
    

}
