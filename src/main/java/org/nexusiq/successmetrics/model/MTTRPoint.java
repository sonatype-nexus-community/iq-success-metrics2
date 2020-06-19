package org.nexusiq.successmetrics.model;

public class MTTRPoint {
	
	private String label;
    private float pointA;
    private float pointB;
    private float pointC;
    private float pointD;
    
    public MTTRPoint() {}
    
	public MTTRPoint(String label, float pointA, float pointB, float pointC, float pointD) {
		super();
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

	public void setPointA(float pointA) {
		this.pointA = pointA;
	}

	public float getPointB() {
		return pointB;
	}

	public void setPointB(float pointB) {
		this.pointB = pointB;
	}

	public float getPointC() {
		return pointC;
	}

	public void setPointC(float pointC) {
		this.pointC = pointC;
	}

	public float getPointD() {
		return pointD;
	}

	public void setPointD(float pointD) {
		this.pointD = pointD;
	}

	@Override
	public String toString() {
		return "MTTRPoints [label=" + label + ", pointA=" + pointA + ", pointB=" + pointB + ", pointC=" + pointC
				+ ", pointD=" + pointD + "]";
	}
}
