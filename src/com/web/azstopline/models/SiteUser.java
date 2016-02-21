package com.web.azstopline.models;

import com.web.azstopline.dbconnection.DbToplineWeb;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by VSB on 19.01.2016.
 */
public class SiteUser {
    private String name = "";
    private String fio = "";
    private String phone = "";
    private String email = "";
    private String isBlock = "";
    private String isDelete = "";
    private int id;

    public enum usersTableField {
        id_user, user_name, user_password, user_fio, user_phone, user_email, user_is_block, user_is_delete
    }

    public SiteUser(String name) {
        this.name = name;
        DbToplineWeb db = new DbToplineWeb();
        String sql;
        ResultSet resultSet = null;
        if (this.name != null) {
            sql = "select * from users where user_name='" + this.name + "'";
            resultSet = db.getSelectResult(sql);
        }
        SiteUser redUser = null;
        try {
            if (resultSet != null && resultSet.next()) {
                this.id = Integer.parseInt(resultSet.getString(SiteUser.usersTableField.id_user.toString()));
                this.email = resultSet.getString(SiteUser.usersTableField.user_email.toString());
                this.fio = resultSet.getString(SiteUser.usersTableField.user_fio.toString());
                this.phone = resultSet.getString(SiteUser.usersTableField.user_phone.toString());
                this.isBlock = resultSet.getString(SiteUser.usersTableField.user_is_block.toString());
                this.isDelete = resultSet.getString(SiteUser.usersTableField.user_is_delete.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getFio() {
        return fio;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getIsBlock() {
        return isBlock;
    }

    public void setFio(String fio) {
        if (fio == null)
            this.fio = "";
        else
            this.fio = fio;
    }

    public void setPhone(String phone) {
        if (phone == null)
            this.phone = "";
        else
            this.phone = phone;
    }

    public void setEmail(String email) {
        if (email == null)
            this.email = "";
        else
            this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }

    public boolean getPermissionsAccess (String page){
        return true;
    }
}
