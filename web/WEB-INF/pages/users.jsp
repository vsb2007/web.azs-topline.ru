<%@ page import="io.bgroup.toplineweb.model.SiteUser" %>
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

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<%

    if (userName == null || userName.equals("")) {

%>
<div class="section">
    <h2>Авторизация должна быть</h2>
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
        if (listUsers.size() > 0) {
    %>
    <ul class="list">
        <%
            for (SiteUser siteUser : listUsers) {

        %>

        <li ripple>
            <form action="/userred" method="post">
                <input type="hidden" id="user-find-label" value="1" name="user-find-label">
                <input value="<%=siteUser.getName()%>" name="buttonuserred" id="buttonuserred" type="hidden">
                <%
                    if (siteUser.getIsBlock().equals("0")) {
                %>
                <button class="button raised color-white bg-blue-500" type="submit" style="width: 15em;">
                        <%
                    }
                    else {
                %>
                    <button class="button raised color-white bg-grey-500" type="submit" style="width: 15em;">
                        <%
                    }
                        %>
		<span class="item-text">
			<%=siteUser.getName()%>
			<span class="secondary-text">
				<%
                    if (siteUser.getFio() != null) {
                %>
                    <%=siteUser.getFio()%>
                <%
                    }
                %>
			</span>
		</span>
                    </button>
            </form>
        </li>

        <%
            }
        %>
    </ul>
    <%

        }
    %>

    <%
        }
    %>
</div>
<div class="section">

    <form action="usersadd" method="post">
        <input type="text" class="text-input border-green-500" placeholder="User name" required name="username"
               id="username"> <br>
        <button class="button raised bg-blue-500 color-white">Добавить пользователя</button>
    </form>
    <%
        String errorAddUser = (String) request.getAttribute("errorAddUser");
        if (errorAddUser != null && !errorAddUser.equals("")) {
    %>
    <p class="text"><%=errorAddUser%>
            <%
        }
    %>
</div>
<%

    }

%>

<%@ include file="footer.jsp" %>
