package com.web.azstopline.dbconnection;

import java.sql.*;

/**
 * Created by VSB on 20.01.2016.
 */
public class DbToplineWeb {
    private Connection connection;
    private String host = "192.168.19.43";
    private String portNumber = "3306";
    private String databaseName = "toplineweb";
    private String url = "jdbc:mysql://" + host + ":" + portNumber + "/" + databaseName;
    private String userName = "toplinewebuser";
    private String password = "toplinewebpassword";
    private String error;
    private Statement statement;
    //private PreparedStatement preparedStatement;


    public DbToplineWeb() {

    }

    public String getUrl() {
        return url;
    }

    public Connection getConnection() {
        connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, userName, password);
            //statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return connection;
    }

    public Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
