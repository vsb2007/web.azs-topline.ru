package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbModelSafe {

    @Autowired
    DataSource dataSourceMvc;

    private Connection connection;

    private String error;

    public DbModelSafe() {
        try {
            connection = dataSourceMvc.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResultSet getSelectResultPS(String sql, ArrayList<Object> arrayList) {
        if (sql == null || sql.equals("") || arrayList == null || arrayList.size() == 0) return null;
        ResultSet list = null;
        try {
            PreparedStatement preparedStatement = prepareStatementFunc(connection, sql, arrayList);
            if (preparedStatement != null)
                list = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean getInsertResult(String sql, ArrayList<Object> arrayList) {
        try {
            //sorry for next line :-)
            if (connection == null) return false;
            PreparedStatement preparedStatement = prepareStatementFunc(connection, sql, arrayList);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PreparedStatement prepareStatementFunc(Connection connection, String sql, ArrayList<Object> arrayList) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (preparedStatement == null) return null;
        for (int i = 1; i < arrayList.size() + 1; i++) {
            try {
                preparedStatement.setString(i, (String) arrayList.get(i - 1).toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return preparedStatement;
    }
}
