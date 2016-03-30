package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;
import java.util.Map;

/*
потом переделать на хибернейт !!!
 */

public class DbModel {

    @Autowired
    private JdbcTemplate jdbcTemplateMvc;

    public void setJdbcTemplateMvc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplateMvc = jdbcTemplate;
    }

    private String error;

    public DbModel() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Map<String, Object>> getSelectResult(String sql) {
        List<Map<String, Object>> list = null;
        try {
            list = jdbcTemplateMvc.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public boolean getInsertResult(String sql) {
        try {
            //sorry for next line :-)
            return jdbcTemplateMvc.getDataSource().getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
