package io.bgroup.topline.model;


import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RolesUrl {

    private String id;
    private String url;
    private String description;
    private String isBlock;

    @Autowired
    private DbModel dbMvc;

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIsBlock() {
        return isBlock;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static ArrayList<RolesUrl> getUrlList(SiteUser siteUser) {
        String sql = null;
        ResultSet resultSet = null;

        if (siteUser.getName().equals("admin")) {
            sql = "select * from permissions_links";
        }
        ArrayList<RolesUrl> rolesUrlsList = null;
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    RolesUrl rolesUrl = new RolesUrl();

                    rolesUrlsList.add(rolesUrl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //return null;
        }
        return rolesUrlsList;
    }
}
