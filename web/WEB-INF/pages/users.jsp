<%@ page import="io.bgroup.topline.model.SiteUser" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_USERS')">
    <%
        ArrayList<SiteUser> listUsers = (ArrayList) request.getAttribute("listUsers");
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
                <sec:authorize access="hasRole('ROLE_USERSRED')">
                <form action="/userred" method="post">
                    </sec:authorize>
                    <input type="hidden" id="user-find-label" value="1" name="user-find-label">
                    <input value="<%=siteUser.getName()%>" name="buttonuserred" id="buttonuserred" type="hidden">
                    <%
                        if (siteUser.getIsEnable().equals("true")) {
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
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <sec:authorize access="hasRole('ROLE_USERSRED')">
                </form>
                </sec:authorize>
            </li>

            <%
                }
            %>
        </ul>
        <%
                }

            }
        %>
    </div>
    <div class="section">
        <sec:authorize access="hasRole('ROLE_USERSADD')">
        <form action="usersadd" method="post">
            <input type="text" class="text-input border-green-500" placeholder="User name" required name="username"
                   id="username"> <br>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
            </sec:authorize>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
