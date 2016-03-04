<%@ page import="io.bgroup.topline.model.SiteUser" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SiteUser user = (SiteUser) session.getAttribute("dbUserName");
    String userName = "";
    if (user != null) {
        userName = user.getName();
    }

%>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<%
    if (userName == null || userName.equals("")) {
%>
<div class="section">
    <h2>Авторизация</h2>
    <form action="login" method="POST">
        <input type="text" class="text-input" id="username" name="username" placeholder="User name" required autofocus/>
        <br>
        <input type="password" class="text-input" id="password" name="password" placeholder="Password" required/><br>
        <button class="button raised bg-blue-500 color-white">Вход</button>
    </form>
</div>
<%
} else {
%>
<div class="section">
    <h2>Здравствуйте <%=userName%>
    </h2>
    <form action="logout" method="POST">
        <button class="button raised bg-blue-500 color-white">Выход</button>
    </form>
</div>
<%
    }
%>

<%@ include file="footer.jsp" %>