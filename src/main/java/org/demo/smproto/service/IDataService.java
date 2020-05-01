package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.DataPointMulti;


public interface IDataService {

	List<DataPoint> countOnBoardedApplications();
	List<DataPoint> countTotalScans();
	List<DataPointMulti> countSecurityCriticals();
	List<DataPoint> countApplicationsScanned();
	List<DataPoint> runSQLStatement(String statement);
	
}
