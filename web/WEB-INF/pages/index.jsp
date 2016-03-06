<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ page import="io.bgroup.topline.model.SiteUser" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SiteUser user = (SiteUser) session.getAttribute("dbUserName");
    String userName = "";
    if (user != null) {
        userName = user.getName();
    }
    else {
        userName = (String)request.getAttribute("dbUserName");;
    }

%>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<%
    //if (userName == null || userName.equals("")) {

%>
<sec:authorize access="!hasRole('ROLE_LOGIN')">
<div class="section">
    <h2>Авторизация</h2>
    <form action="/login" method="POST">
        <input type="text" class="text-input" id="username" name="username" placeholder="User name" required autofocus/>
        <br>
        <input type="password" class="text-input" id="password" name="password" placeholder="Password" required/><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="button raised bg-blue-500 color-white">Вход</button>
    </form>
</div>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_LOGIN')">
<%
//} else {
%>
<div class="section">
    <h2>Здравствуйте <%=userName%>
    </h2>
    <form action="logout" method="POST">
        <button class="button raised bg-blue-500 color-white">Выход</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</sec:authorize>
<%
  //  }
%>

<%@ include file="footer.jsp" %>
