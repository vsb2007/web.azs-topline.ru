package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;


public class DbToplineWeb {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String error;

    public DbToplineWeb() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Map<String, Object>> getSelectResult(String sql) {
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            setError("Ошибка: " + e);
            return null;
        }
        return list;
    }


    public boolean getInsertResult(String sql) {
        try {
            //потом переделать на хибернейт !!!
            return jdbcTemplate.getDataSource().getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
