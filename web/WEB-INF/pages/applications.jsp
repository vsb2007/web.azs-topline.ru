<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_APP_LIST')">
    <div class="section">
        <sec:authorize access="hasRole('ROLE_APP_CREATE')">
            <form action="appcreate" method="post">
                <input type="text" class="text-input border-green-500" placeholder="User name" required name="username"
                       id="username"> <br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="button raised bg-blue-500 color-white">Добавить пользователя</button>
            </form>
        </sec:authorize>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
