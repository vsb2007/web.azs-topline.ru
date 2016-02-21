package com.web.azstopline.servlets;

import com.web.azstopline.dbconnection.DbToplineWeb;
import com.web.azstopline.models.LinkUrl;
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
import java.util.ArrayList;

/**
 * Created by VSB on 13.02.2016.
 */
@WebServlet(name = "PermissionsAccessLinks", urlPatterns = {"/permissions"})
public class PermissionsAccessLinks extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SiteUser siteUser = (SiteUser) session.getAttribute("dbUserName");
        if (siteUser == null || !siteUser.getName().equals("admin")) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        request.setCharacterEncoding("UTF-8");
        String addLinkUrlForm = request.getParameter("addLinkUrlForm");
        if (addLinkUrlForm != null && addLinkUrlForm.equals("1")) {
            funcAddLinkUrl(request);
        }

        ArrayList<LinkUrl> linkUrls = LinkUrl.getUrlList(siteUser);
        request.setAttribute("linkUrlList", linkUrls);
        request.getRequestDispatcher("/ssi/links.jsp").forward(request, response);
    }

    private void funcAddLinkUrl(HttpServletRequest request) {
        String linkUrl = request.getParameter("linkUrl");
        String linkDescription = request.getParameter("linkDescription");
        if (linkUrl == null || linkUrl.equals("") || linkDescription == null || linkDescription.equals("")) {
            request.setAttribute("errorAddUrl", "Ошибка 000 добавления URL");
            return;
        }

        DbToplineWeb db = new DbToplineWeb();
        String sql;
        sql = "insert into permissions_links (link_url, link_descriptions,link_is_block)" +
                "values ('" + linkUrl + "','" + linkDescription + "',0)";
        boolean flag = db.getInsertResult(sql);
        if (flag) {
            request.setAttribute("error", "Ошибка обновления блокировки");
            request.setAttribute("errorAddUrl", "Ошибка 001 добавления URL:");
            return;
        }
        request.setAttribute("addLinkUrlMessage", "URL добавлен");
    }
}

