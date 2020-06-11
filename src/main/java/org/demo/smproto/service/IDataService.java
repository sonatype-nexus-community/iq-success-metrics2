package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.MTTRPoint;
import org.demo.smproto.model.PolicyViolation;


public interface IDataService {

	List<DataPoint> executeSQL(String sqlStatement);

	List<DataPoint> getDataPoints(List<DataPoint> runSQLStatement);

	List<MTTRPoint> getMTTRPoints(List<MTTRPoint> runSQLStatement);

	List<PolicyViolation> executeSQL2(String policyViolationsAge90);

	List<MTTRPoint> executeSQL3(String policyViolationsAge90);

	List<PolicyViolation> getPolicyViolationPoints(List<PolicyViolation> runSQLStatement);
	
	String latestPeriod();
	
}
