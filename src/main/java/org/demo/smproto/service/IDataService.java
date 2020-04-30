package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.DataPointMulti;


public interface IDataService {

	List<DataPoint> countOnBoardedApplications();
	List<DataPoint> countScannedApplications();
	List<DataPointMulti> countSecurityCriticals();
	
}
