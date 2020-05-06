package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.DataPoint1;
import org.demo.smproto.model.DataPoint3;


public interface IDataService {

	List<DataPoint> runSQLStatement(String sqlStatement);
	List<DataPoint1> runSQLStatementDP1(String sqlStatement);
	List<DataPoint3> runSQLStatementDP3(String sqlStatement);
	
}
