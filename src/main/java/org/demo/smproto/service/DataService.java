package org.demo.smproto.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class DataService implements IDataService {

	@Autowired 
    private JdbcTemplate jtm;

	public class DataPointMapper implements RowMapper<DataPoint> {
        @Override
        public DataPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DataPoint point = new DataPoint();
            point.setPeriod(rs.getString("period"));
            point.setCount(rs.getInt("count"));
            return point;
        }
    }
	
	@Override
	public List<DataPoint> countOnBoardedApplications() {
    	
        String sql = "select time_period_start as period, count(application_id) as count " +
        			 " from metric " +
        			 " group by time_period_start";
        
        return jtm.query(
            sql, new BeanPropertyRowMapper<>(DataPoint.class));  
    }
    
    
	@Override
	public List<DataPoint> countScannedApplications() {
		
		String sql = "select time_period_start as period, count(evaluation_count) as count from metric group by time_period_start";
   
		return jtm.query(
				sql, new BeanPropertyRowMapper<>(DataPoint.class));  
	}
}
