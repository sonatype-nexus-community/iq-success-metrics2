package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.DataPoint;


public interface IDataService {

	List<DataPoint> executeSQL(String sqlStatement);

	List<DataPoint> getDataPoints(List<DataPoint> runSQLStatement);
	
}
