package org.demo.smproto.model;

public class DataPoint {
	
	private String label;
    private float pointA;
    private int pointB;
    private int pointC;
    private int pointD;
    
    public DataPoint() {}
    
	public DataPoint(String label, int pointA, int pointB, int pointC, int pointD) {
		this.label = label;
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
		this.pointD = pointD;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getPointA() {
		return pointA;
	}

	public void setPointA(float f) {
		this.pointA = f;
	}

	public int getPointB() {
		return pointB;
	}

	public void setPointB(int pointB) {
		this.pointB = pointB;
	}

	public int getPointC() {
		return pointC;
	}

	public void setPointC(int pointC) {
		this.pointC = pointC;
	}

	public int getPointD() {
		return pointD;
	}

	public void setPointD(int pointD) {
		this.pointD = pointD;
	}

	@Override
	public String toString() {
		return "DataPoint [label=" + label + ", pointA=" + pointA + ", pointB=" + pointB + ", pointC=" + pointC
				+ ", pointD=" + pointD + "]";
	}

}
