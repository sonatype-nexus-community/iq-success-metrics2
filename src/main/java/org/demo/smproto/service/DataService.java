package org.demo.smproto.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.DataPoint1;
import org.demo.smproto.model.DataPoint3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class DataService implements IDataService {

	@Autowired 
    private JdbcTemplate jtm;
	
	public List<DataPoint> runSQLStatement(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(DataPoint.class));  
	}

	public List<DataPoint3> runSQLStatementDP3(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(DataPoint3.class));  
	}
	
	public List<DataPoint1> runSQLStatementDP1(String sqlStatement) {
		return jtm.query(sqlStatement, new BeanPropertyRowMapper<>(DataPoint1.class));  
	}
	
	public class DataPointMapper implements RowMapper<DataPoint1> {
        @Override
        public DataPoint1 mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DataPoint1 point = new DataPoint1();
            point.setPeriod(rs.getString("period"));
            point.setCount(rs.getInt("count"));
            return point;
        }
    }
	
	public class DataMapper implements RowMapper<DataPoint> {
        @Override
        public DataPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DataPoint point = new DataPoint();
            point.setLabel(rs.getString("label"));
            point.setPointA(rs.getInt("pointA"));
            point.setPointB(rs.getInt("pointB"));
            point.setPointC(rs.getInt("pointC"));
            point.setPointD(rs.getInt("pointD"));
            return point;
        }
    }
	
	public class DataPointMultiMapper implements RowMapper<DataPoint3> {
        @Override
        public DataPoint3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DataPoint3 point = new DataPoint3();
            point.setPeriod(rs.getString("period"));
            point.setCountA(rs.getInt("countA"));
            point.setCountB(rs.getInt("countB"));
            point.setCountC(rs.getInt("countC"));
            return point;
        }
    }

}
