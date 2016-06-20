package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbJdbcModel {

    @Autowired
    private JdbcTemplate jdbcTemplateMvc;

    private String error;

    public DbJdbcModel() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean getUpdateResult(String sql, ArrayList<Object> args) {
        try {
            jdbcTemplateMvc.update(sql, args.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getUpdateResult(String sql) {
        try {
            jdbcTemplateMvc.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Map<String, Object>> getSelectResult(String sql, ArrayList<Object> args) {
        return jdbcTemplateMvc.queryForList(sql, args.toArray());
    }

    public List<Map<String, Object>> getSelectResult(String sql) {
        return jdbcTemplateMvc.queryForList(sql);
    }
}