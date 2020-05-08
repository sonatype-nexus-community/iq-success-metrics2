package org.demo.smproto.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.model.DataPoint;
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

}
