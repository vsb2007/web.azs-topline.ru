package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Role {

    private String id;
    private String Name;

    @Autowired
    private DbModel dbMvc;

    public static ArrayList<Role> getUrlList(SiteUser siteUser) {
        String sql = null;
        ResultSet resultSet = null;

        if (siteUser.getName().equals("admin")) {
            sql = "select * from user_roles";
        }
        ArrayList<Role> rolesUrlsList = getUserRolesFromDbSelect(sql);
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    Role role = new Role();

                    rolesUrlsList.add(role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //return null;
        }
        return rolesUrlsList;
    }

    private static ArrayList<Role> getUserRolesFromDbSelect(String sql) {
        ArrayList<Role> rolesList = null;
        

        return rolesList;
    }
}
