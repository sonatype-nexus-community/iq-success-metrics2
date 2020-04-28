package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ApplicationsService implements IApplicationsService {
	
	@Autowired
    private JdbcTemplate jtm;
	
	public List<Applications> CountApplicationsByTimeStartPeriod() {
		String sql = "select time_period_start, count(application_id) from metric group by time_period_start";
        return jtm.query(sql, new BeanPropertyRowMapper<>(Applications.class));
	}

}
