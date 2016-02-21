<%@ page import="com.web.azstopline.models.SiteUser" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.web.azstopline.models.LinkUrl" %><%--
  Created by IntelliJ IDEA.
  User: VSB
  Date: 13.02.2016
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SiteUser user = (SiteUser) session.getAttribute("dbUserName");
    String userName = "";
    if (user != null && user.getName().equals("admin")) {
        userName = user.getName();
    } else {
%>
<jsp:forward page="/login"/>

<%

    }
%>

<%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>

<%

    if (userName == null || !userName.equals("admin")) {

%>
<div class="section">
    <h2>Авторизация должна быть</h2>
</div>

<%
} else {
%>
<div class="section">
    <form action="/permissions" method="post">
        <input type="text" class="text-input border-green-500" placeholder="Link" required name="linkUrl"
               id="linkUrl"> &nbsp;&nbsp;
        <input type="text" class="text-input border-green-500" placeholder="Description" required name="linkDescription"
               id="linkDescription">
        <br>
        <input type="hidden" name="addLinkUrlForm" id="addLinkUrlForm" value="1">
        <button class="button raised bg-blue-500 color-white" id="addLinkUrlButton"
                name="addLinkUrlButton">Добавить URL
        </button>
    </form>
    <%
        String errorAddUrl = (String) request.getAttribute("errorAddUrl");
        if (errorAddUrl != null && !errorAddUrl.equals("")) {
    %>
    <p class="text"><%=errorAddUrl%>
    </p>
    <%
        }
        if (errorAddUrl == null) {
            String addLinkUrlMessage = (String) request.getAttribute("addLinkUrlMessage");
            if (addLinkUrlMessage != null) {
    %>
                <span class="secondary-text">
				    <label for="addLinkUrlButton" class="label color-green-500"><%=addLinkUrlMessage%>
                    </label>
			</span>
    <%
            }
        }
    %>
</div>

<div class="section">
    <h2>Доступные линки</h2>
    <%
        ArrayList<LinkUrl> linkUrls = (ArrayList<LinkUrl>) request.getAttribute("linkUrlList");
        if (linkUrls != null) {
            for (LinkUrl linkUrl : linkUrls) {
    %>
    <p class="text"><%=linkUrl.getId()%> <%=linkUrl.getUrl()%> <%=linkUrl.getDescription()%> <%=linkUrl.getIsBlock()%>
    </>

    <%
            }
        }
    %>
</div>
<%

    }
%>


<%@ include file="../footer.jsp" %>
