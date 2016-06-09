package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
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

    public boolean getUpdateResult(String sql, Object[] args) {
        int result = jdbcTemplateMvc.update(
                sql,
                args);
        return true;
    }

    public List<Map<String, Object>> getSelectResult(String sql, Object[] args) {
        return jdbcTemplateMvc.queryForList(sql, args);
    }
}
