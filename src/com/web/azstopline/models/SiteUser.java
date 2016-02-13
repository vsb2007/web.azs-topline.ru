package com.web.azstopline.models;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by VSB on 19.01.2016.
 */
public class SiteUser {
    private String name = "";
    private String fio = "";
    private String phone = "";
    private String email = "";
    private int id;
    private ArrayList<String> operationList;

    public enum usersTableField {
        id_user
        , user_name
        , user_password
        , user_fio
        , user_phone
        , user_email
        , user_is_block
        , user_is_delete
    }

    public SiteUser(String name) {
        this.name = name;
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
}
