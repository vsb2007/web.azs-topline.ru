package io.bgroup.topline.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RolesUrl {

    private String id;
    private String url;
    private String description;
    private String isBlock;

    @Autowired
    private DbToplineWeb db;

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
        //DbToplineWeb db = new DbToplineWeb();
        String sql = null;
        ResultSet resultSet = null;

        if (siteUser.getName().equals("admin")) {
            sql = "select * from permissions_links";
        }
        ArrayList<RolesUrl> rolesUrlsList = null;
        try {
          //  resultSet = db.getSelectResult(sql);
            //linkUrls = new ArrayList<>(resultSet.getFetchSize());
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
