<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="io.bgroup.topline.model.SiteUser" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_USERS')">
    <div class="section">
        <h2>Список пользователей</h2>
        <ul class="list">
            <c:forEach
                    items="${listUsers}"
                    var="siteUser">
                <li ripple>
                    <sec:authorize access="hasRole('ROLE_USERS_RED')">
                        <form action="usersred" method="post">
                            <input type="hidden" id="user-find-label" value="1" name="user-find-label">
                            <input value="${siteUser.getName()}" name="buttonuserred" id="buttonuserred" type="hidden">
                            <c:if test="${siteUser.getIsEnable().equals(\"true\")}">
                            <button class="button raised color-white bg-blue-500" type="submit" style="width: 15em;">
                                </c:if>
                                <c:if test="${!siteUser.getIsEnable().equals(\"true\")}">
                                <button class="button raised color-white bg-grey-500" type="submit"
                                        style="width: 15em;">
                                    </c:if>
		                    <span class="item-text">${siteUser.getName()}
			                    <span class="secondary-text">${siteUser.getFio()}</span>
		                    </span>
                                </button>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </sec:authorize>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="section">
        <sec:authorize access="hasRole('ROLE_USERS_ADD')">
            <form action="usersadd" method="post">
                <input type="text" class="text-input border-green-500" placeholder="User name" required name="username"
                       id="username"> <br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="button raised bg-blue-500 color-white">Добавить пользователя</button>
            </form>
            <p class="text">${errorAddUser}</p>
        </sec:authorize>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
