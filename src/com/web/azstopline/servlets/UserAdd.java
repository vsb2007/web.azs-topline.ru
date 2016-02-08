package com.web.azstopline.servlets;

import com.web.azstopline.dbconnection.DbToplineWeb;
import com.web.azstopline.models.SiteUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by VSB on 08.02.2016.
 */
@WebServlet(name = "UserAdd", urlPatterns = {"/usersadd"})
public class UserAdd extends HttpServlet {
    private enum usersFields {id_users, users_name}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SiteUser siteUser = (SiteUser) session.getAttribute("dbUserName");
        if (siteUser == null || !siteUser.getName().equals("admin")) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        String userNameFromFormUserAdd = (String) request.getAttribute("useradd");

        DbToplineWeb db = new DbToplineWeb();
        Connection connection = db.getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from users where user_name='" + userNameFromFormUserAdd + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet != null && resultSet.next()) {
                String nameFromDb = resultSet.getString(usersFields.users_name.toString());
                if (!resultSet.next()) {
                    //siteUser = new SiteUser(nameFromDb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
