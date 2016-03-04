package io.bgroup.topline.model;

import org.springframework.jdbc.core.JdbcTemplate;

public interface JdbcInterface {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
    //void createTable(); //if table not exist - create, if exist skip
}
