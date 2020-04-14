package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import sqlService.SqlService;

import javax.sql.DataSource;

public class Jdbc {
    protected JdbcTemplate jdbcTemplate;
    @Autowired protected SqlService sqlService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
