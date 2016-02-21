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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by VSB on 09.02.2016.
 */
@WebServlet(name = "UserRed", urlPatterns = {"/userred"})
public class UserRed extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SiteUser siteUser = (SiteUser) session.getAttribute("dbUserName");
        if (siteUser == null || !siteUser.getName().equals("admin")) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        request.setCharacterEncoding("UTF-8");
        String userFindValue = request.getParameter("user-find-label");
        String userRedFormValue = request.getParameter("red_form");
        String userDelValue = request.getParameter("user-delete-id-label");

        System.out.println();
        if (userFindValue != null && userFindValue.equals("1")) {
            SiteUser redUser = findUser(request);

            if (redUser != null) {
                request.setAttribute("reduser", redUser);
            } else {
                request.setAttribute("error", "Пользователь не найден");
            }
        } else if (userRedFormValue != null && userRedFormValue.equals("1")) {

            SiteUser redUser = findUser(request);
            updateUser(redUser, request);
            redUser = findUser(request);
            request.setAttribute("reduser", redUser);
            request.setAttribute("updateMessage", "Изменения сохранены");
        }
        request.getRequestDispatcher("/ssi/userred.jsp").forward(request, response);
    }

    private void updateUser(SiteUser redUser, HttpServletRequest request) {
        String userNameFromForm = request.getParameter("user-name-label");
        String userPasswordFromForm = request.getParameter("user-password-label");
        String userFioFromForm = request.getParameter("user-fio-label");
        String userPhoneFromForm = request.getParameter("user-phone-label");
        String userEmailFromForm = request.getParameter("user-email-label");
        String userIdFromForm = request.getParameter("user-red-id-label");
        String userActiveFlagFromForm = request.getParameter("user-active-flag");

        DbToplineWeb db = new DbToplineWeb();
        String sql;

        if (userNameFromForm != null
                && !redUser.getName().equals(userNameFromForm)
                && !userNameFromForm.equals("")) {
            sql = "update users set user_name='" + userNameFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                request.setAttribute("error", "Ошибка обновления имени");
                return;
            }

        }

        if (userPasswordFromForm != null && !userPasswordFromForm.equals("")) {
            sql = "update users set user_password=md5('" + userPasswordFromForm + "') where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                request.setAttribute("error", "Ошибка обновления пароля");
                return;
            }
        }

        if (userFioFromForm != null) {
            sql = "update users set user_fio='" + userFioFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                request.setAttribute("error", "Ошибка обновления ФИО");
                return;
            }
        }

        if (userPhoneFromForm != null) {
            sql = "update users set user_phone='" + userPhoneFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                request.setAttribute("error", "Ошибка обновления телефона");
                return;
            }
        }

        if (userEmailFromForm != null)

        {
            sql = "update users set user_email='" + userEmailFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                request.setAttribute("error", "Ошибка обновления email");
                return;
            }
        }

        if (userActiveFlagFromForm != null)

        {
            if (userActiveFlagFromForm.equals("0")) {
                sql = "update users set user_is_block='" + userActiveFlagFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = db.getInsertResult(sql);
                if (flag) {
                    request.setAttribute("error", "Ошибка обновления блокировки");
                    return;
                }
            }
        }

        if (userActiveFlagFromForm == null || !userActiveFlagFromForm.equals("0")) {
            sql = "update users set user_is_block='1' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                request.setAttribute("error", "Ошибка обновления блокировки");
                return;
            }
        }

    }

    private SiteUser findUser(HttpServletRequest request) {
        String userNameFromButtonValue = request.getParameter("buttonuserred");
        DbToplineWeb db = new DbToplineWeb();
        String sql;
        if (userNameFromButtonValue != null && !userNameFromButtonValue.equals("")) {
            sql = "select * from users where user_name='" + userNameFromButtonValue + "'";
        } else {
            String userIdFromForm = request.getParameter("user-red-id-label");
            sql = "select * from users where id_user='" + userIdFromForm + "'";
        }
        ResultSet resultSet = null;
        resultSet = db.getSelectResult(sql);
        SiteUser redUser = null;
        try {
            if (resultSet != null && resultSet.next()) {
                redUser = new SiteUser(resultSet.getString(SiteUser.usersTableField.user_name.toString()));
               /* if (redUser != null) {
                    redUser.setId(Integer.parseInt(resultSet.getString(SiteUser.usersTableField.id_user.toString())));
                    redUser.setEmail(resultSet.getString(SiteUser.usersTableField.user_email.toString()));
                    redUser.setFio(resultSet.getString(SiteUser.usersTableField.user_fio.toString()));
                    redUser.setPhone(resultSet.getString(SiteUser.usersTableField.user_phone.toString()));
                    redUser.setIsBlock(resultSet.getString(SiteUser.usersTableField.user_is_block.toString()));
                }*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return redUser;
    }
}
