package com.web.azstopline.models;

import com.web.azstopline.dbconnection.DbToplineWeb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by VSB on 13.02.2016.
 */
public class LinkUrl {
    public enum permissionsLinksTableField {
        id_link, link_url, link_descriptions, link_is_block
    }

    private String id;
    private String url;
    private String description;
    private String isBlock;

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

    public static ArrayList<LinkUrl> getUrlList(SiteUser siteUser) {
        Statement statement;
        statement = new DbToplineWeb().getStatement();
        String sql = null;
        ResultSet resultSet = null;

        if (siteUser.getName().equals("admin")) {
            sql = "select * from permissions_links";
        }
        ArrayList<LinkUrl> linkUrls=null;
        try {
            resultSet = statement.executeQuery(sql);
            linkUrls = new ArrayList<>(resultSet.getFetchSize());
            if (resultSet != null) {
                while (resultSet.next()) {
                    LinkUrl linkUrl = new LinkUrl();
                    linkUrl.setUrl(resultSet.getString(permissionsLinksTableField.link_url.toString()));
                    linkUrl.setDescription(resultSet.getString(permissionsLinksTableField.link_descriptions.toString()));
                    linkUrl.setId(resultSet.getString(permissionsLinksTableField.id_link.toString()));
                    linkUrl.setIsBlock(resultSet.getString(permissionsLinksTableField.link_is_block.toString()));
                    linkUrls.add(linkUrl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //return null;
        }
        return linkUrls;
    }
}
