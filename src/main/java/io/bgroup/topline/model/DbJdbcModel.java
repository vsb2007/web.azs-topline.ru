package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(propagation = Propagation.MANDATORY)
    public boolean getUpdateResult(String sql, ArrayList<Object> args) {
        //jdbcTemplateMvc.update(sql, args.toArray());

        try {
            jdbcTemplateMvc.update(sql, args.toArray());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
        private boolean getUpdateResult(String sql) {
            try {
                jdbcTemplateMvc.update(sql);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    */
    public /*synchronized*/ List<Map<String, Object>> getSelectResult(String sql, ArrayList<Object> args) {
        if (args != null && args.size() > 0)
            return jdbcTemplateMvc.queryForList(sql, args.toArray());
        else return getSelectResult(sql);
    }

    private List<Map<String, Object>> getSelectResult(String sql) {
        return jdbcTemplateMvc.queryForList(sql);
    }
}
