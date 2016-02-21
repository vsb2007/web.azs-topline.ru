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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SiteUser siteUser = (SiteUser) session.getAttribute("dbUserName");
        if (siteUser == null || !siteUser.getName().equals("admin")) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        String userNameFromFormUserAdd = request.getParameter("username");

        DbToplineWeb db = new DbToplineWeb();

        String sql;
        sql = "select * from users where user_name='" + userNameFromFormUserAdd + "'";
        ResultSet resultSet = null;
        resultSet = db.getSelectResult(sql);

        try {
            if (resultSet != null && resultSet.next()) {
                request.setAttribute("errorAddUser","Пользователь существует");
                request.getRequestDispatcher("/users").forward(request, response);
            }
            if (resultSet != null && !resultSet.next()) {
                //String nameFromDb = resultSet.getString(usersFields.users_name.toString());
                sql = "INSERT INTO users (user_name) VALUES ('"+userNameFromFormUserAdd + "')";
                boolean flag = db.getInsertResult(sql);
                if (!flag)
                    request.setAttribute("errorAddUser","Пользователь добавлен");
                else
                    request.setAttribute("errorAddUser","Пользователь не добавлен, ошибка!!!");
                request.getRequestDispatcher("/users").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
