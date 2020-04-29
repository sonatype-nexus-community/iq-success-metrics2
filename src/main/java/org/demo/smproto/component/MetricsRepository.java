package org.demo.smproto.component;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.demo.smproto.model.DataPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MetricsRepository {
	
	@Autowired 
    private NamedParameterJdbcTemplate jdbcTemplate;
	
    public DataPoint countApplications(){  
    	
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("customerId", 1);
        
        String sql = "select time_period_start as period, count(application_id) as count " +
        			 " from metric " +
        			 " group by time_period_start";
        
        return (DataPoint)jdbcTemplate.queryForObject(
            sql, parameters, BeanPropertyRowMapper.newInstance(DataPoint.class));  
    }
    
    public class DataPointMapper implements RowMapper<DataPoint> {
        @Override
        public DataPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DataPoint point = new DataPoint();
            point.setPeriod(rs.getString("period"));
            point.setCount(rs.getInt("count"));
            return point;
        }
    }

}

