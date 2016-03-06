package io.bgroup.topline.servlets;

import io.bgroup.topline.model.DbToplineWeb;
import io.bgroup.topline.model.SiteUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

/**
 * Created by VSB on 19.01.2016.
 */
//@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login {
    private enum usersAdminFields {id_admin_users, admin_name}

    public SiteUser getUser(HttpServletRequest request) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DbToplineWeb db = new DbToplineWeb();
        String sql = "";
        if (username.equals("admin")) {
            sql = "select * from admin_users WHERE admin_name = '" + username +
                    "' AND admin_password = MD5('" + password + "')";
        } else {
            sql = "select * from users WHERE user_name = '" + username +
                    "' AND user_password = MD5('" + password + "') AND user_is_delete=0";
        }
        ResultSet resultSet = null;
        SiteUser siteUser = null;
        resultSet = db.getSelectResult(sql);
        try {
            if (resultSet != null && resultSet.next()) {
                String nameFromDb = null;
                if (username.equals("admin")) {
                    nameFromDb = resultSet.getString(usersAdminFields.admin_name.toString());
                } else {
                    nameFromDb = resultSet.getString(SiteUser.usersTableField.user_name.toString());
                }
                if (!resultSet.next())
                    siteUser = new SiteUser().findSiteUser(nameFromDb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (siteUser != null) {
            request.setAttribute("user", siteUser);
            HttpSession session = request.getSession();
            session.setAttribute("dbUserName", siteUser);
        } else {
            // some code
        }
        return siteUser;
    }
}
