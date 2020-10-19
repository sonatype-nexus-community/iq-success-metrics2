package org.sonatype.cs.metrics.service;

import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlService {

    @Autowired 
    private JdbcTemplate jtm;

    public void executeSql(String stmt) {
      jtm.execute(stmt);
    }
    
    public List<DbRow> executeSql2(String stmt) {
      return jtm.query(stmt, new BeanPropertyRowMapper<>(DbRow.class));  
    }

    public List<Mttr> executeSqlMttr(String stmt) {
      return jtm.query(stmt, new BeanPropertyRowMapper<>(Mttr.class));  
    }
    
}
