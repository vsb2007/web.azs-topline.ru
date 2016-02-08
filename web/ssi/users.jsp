<%@ page import="com.web.azstopline.models.SiteUser" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: VSB
  Date: 08.02.2016
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SiteUser user = (SiteUser) session.getAttribute("dbUserName");
    String userName = "";
    if (user != null) {
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

    if (userName == null || userName.equals("")) {

%>
<div class="section">
    <h2>Авторизация должны быть</h2>
</div>
<%

} else {
    ArrayList<SiteUser> listUsers = (ArrayList) request.getAttribute("listusers");

%>
<div class="section">
    <h2>Список пользователей</h2>
    <%
        if (listUsers == null || listUsers.size() == 0) {
    %>
    Список пуст<br>

    <%
    } else {
    %>

    <%
        }
    %>
    </div>
    <div class="section">

    <form action="useradd" method="post">
        <input type="text" class="text-input" placeholder="User name" required name="useradd"
               id="useradd"> <br>
        <button class="button raised bg-blue-500 color-white">Добавить пользователя</button>
    </form>
</div>
<%

    }

%>

<%@ include file="../footer.jsp" %>
