package org.demo.smproto.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.demo.smproto.model.ApplicationEvaluation;
import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.MTTRPoint;
import org.demo.smproto.model.PolicyViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class DataService implements IDataService {

	@Autowired 
    private JdbcTemplate jtm;
	
	
	private static final Logger log = LoggerFactory.getLogger(DataService.class);

	public List<DataPoint> executeSQL(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(DataPoint.class));  
	}
	
	public List<PolicyViolation> executeSQL2(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(PolicyViolation.class));  
	}
	
	public List<ApplicationEvaluation> executeSQL4(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(ApplicationEvaluation.class));  
	}
	
	public List<MTTRPoint> executeSQL3(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(MTTRPoint.class));  
	}
	
	public class DataMapper implements RowMapper<DataPoint> {
        @Override
        public DataPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DataPoint point = new DataPoint();
            point.setLabel(rs.getString("label"));
            point.setPointA(rs.getFloat("pointA"));
            point.setPointB(rs.getInt("pointB"));
            point.setPointC(rs.getInt("pointC"));
            point.setPointD(rs.getInt("pointD"));
            return point;
        }
    }
	
	public List<DataPoint> getDataPoints(List<DataPoint> dataList){
		
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		for (DataPoint dp : dataList) {
			//log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	public List<MTTRPoint> getMTTRPoints(List<MTTRPoint> dataList){
		
		List<MTTRPoint> dataPoints = new ArrayList<MTTRPoint>();

		for (MTTRPoint dp : dataList) {
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	public List<PolicyViolation> getPolicyViolationPoints(List<PolicyViolation> dataList){
		
		List<PolicyViolation> dataPoints = new ArrayList<PolicyViolation>();

		for (PolicyViolation dp : dataList) {
			//log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	public List<ApplicationEvaluation> getApplicationEvaluationPoints(List<ApplicationEvaluation> dataList) {
		List<ApplicationEvaluation> dataPoints = new ArrayList<ApplicationEvaluation>();

		for (ApplicationEvaluation dp : dataList) {
			//log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}


	public String latestPeriod() {
	    String latestPeriod = executeSQL(SQLStatement.LatestTimePeriodStart).get(0).getLabel();
		return latestPeriod;
	}
	
	public String getTimePeriod() throws ParseException {
		List<DataPoint> timePeriods = executeSQL(SQLStatement.TimePeriods);
		
		long oneWeek = 604800000;
		
		String timePeriodLabel = "Week";
		String firstTimePeriod;
		String secondTimePeriod;
		
		if (timePeriods.size() > 1) {
			firstTimePeriod = timePeriods.get(0).getLabel().toString();
			secondTimePeriod = timePeriods.get(1).getLabel().toString();

			long fp = this.convertDateStr(firstTimePeriod);
			long sp = this.convertDateStr(secondTimePeriod);
			
			long diff = sp - fp;

			if (diff <= oneWeek) {
				timePeriodLabel = "week";
			}
			else {
				timePeriodLabel = "month";

			}
		}
		else {
			timePeriodLabel = "week";
		}
		
		return timePeriodLabel;
	}
	
	private Long convertDateStr(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		long millis = date.getTime();
		return millis;
	}

	
	
	
	//@Modifying
	//@Transactional
	//@Query(value = "drop table if exists POLICYVIOLATION; CREATE TABLE POLICYVIOLATION AS SELECT * FROM CSVREAD(?1)", nativeQuery = true)

//	void LoadPolicyViolationsDb(String fileName) {
//		
//		String sqlStmt = "CREATE TABLE POLICYVIOLATION AS SELECT * FROM CSVREAD(" + fileName + ")";
//		
//		jtm.execute(sqlStmt);
//		
	//}
}
