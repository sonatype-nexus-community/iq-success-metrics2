package org.sonatype.cs.metrics.model;

public class DbRowStr {

    private String pointA;
    private String pointB;
    private String pointC;
    private String pointD;
    private String pointE;
    private String pointF;
    private String pointG;
    private String pointH;


    public DbRowStr(){}

    public DbRowStr(String pointA, String pointB, String pointC, String pointD, String pointE, String pointF, String pointG, String pointH) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
        this.pointD = pointD;
        this.pointE = pointE;
        this.pointF = pointF;
        this.pointG = pointG;
        this.pointG = pointH;
    }

    public String getPointH() {
		return pointH;
	}

	public void setPointH(String pointH) {
		this.pointH = pointH;
	}

	public String getPointA() {
        return pointA;
    }

    public void setPointA(String pointA) {
        this.pointA = pointA;
    }

    public String getPointB() {
        return pointB;
    }

    public void setPointB(String pointB) {
        this.pointB = pointB;
    }

    public String getPointC() {
        return pointC;
    }

    public void setPointC(String pointC) {
        this.pointC = pointC;
    }

    public String getPointD() {
        return pointD;
    }

    public void setPointD(String pointD) {
        this.pointD = pointD;
    }

    public String getPointE() {
        return pointE;
    }

    public void setPointE(String pointE) {
        this.pointE = pointE;
    }

    public String getPointF() {
        return pointF;
    }

    public void setPointF(String pointF) {
        this.pointF = pointF;
    }

    public String getPointG() {
        return pointG;
    }

    public void setPointG(String pointG) {
        this.pointG = pointG;
    }

    @Override
    public String toString() {
        return "DbRowStr [pointA=" + pointA + ", pointB=" + pointB + ", pointC=" + pointC + ", pointD=" + pointD
                + ", pointE=" + pointE + ", pointF=" + pointF + "]";
    }
    
}
