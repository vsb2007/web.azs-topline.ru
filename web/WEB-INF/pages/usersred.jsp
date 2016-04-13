<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="io.bgroup.topline.model.SiteUser" %>
<%@ page import="java.util.ArrayList" %>


<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_USERS_RED')">
    <div class="section">
        <h2>Пользователь ${userRed.getName()}
        </h2>
        <form action="usersred" method="get" accept-charset="UTF-8">
            <ul class="list">
                <li ripple>
    <span class="item-text">

    <input type="text" class="text-input border-green-500" placeholder="Имя пользователя"
           value="${userRed.getName()}"
           id="user-name-label" name="user-name-label"
    >
    <span class="secondary-text">
    <label for="user-name-label">Имя пользователя</label>
    </span>
    </span>
                </li>
                <li ripple>
    <span class="item-text">
    <input type="password" class="text-input border-green-500" placeholder="******"
           value=""
           id="user-password-label" name="user-password-label"
    >
    <span class="secondary-text">
    <label for="user-password-label" class="label">Пароль</label>
    </span>
    </span>
                </li>
                <li ripple>
    <span class="item-text">
    <input type="text" class="text-input border-green-500" placeholder="Ф.И.О."
           value="${userRed.getFio()}"
           id="user-fio-label" name="user-fio-label"
    >
    <span class="secondary-text">
    <label for="user-fio-label" class="label">ФИО</label>
    </span>
    </span>
                </li>
                <li ripple>
    <span class="item-text">
    <input type="tel" class="text-input border-green-500" placeholder="Телефон"
           value="${userRed.getPhone()}"
           id="user-phone-label" name="user-phone-label"
    >
    <span class="secondary-text">
    <label for="user-phone-label" class="label">Телефон</label>
    </span>
    </span>
                </li>
                <li ripple>
    <span class="item-text">
    <input type="email" class="text-input border-green-500" placeholder="E-mail"
           value="${userRed.getEmail()}"
           id="user-email-label" name="user-email-label"
    >
    <span class="secondary-text">
    <label for="user-email-label" class="label">E-Mail</label>
    </span>
    </span>
                </li>
                <li ripple>
                    <span class="item-text">
                    <select class="dropdown-menu" id="postId" name="postId" onchange="checkCompany(this)">
                        <option value="-1">Сущность</option>
                        <c:forEach items="${postList}" var="post">
                            <c:if test="${post.getIdPost() == userRed.getPost().getIdPost()}">
                                <option value="${post.getIdPost()}" selected>${post.getPostName()}</option>
                            </c:if>
                            <c:if test="${post.getIdPost() != userRed.getPost().getIdPost()}">
                                <option value="${post.getIdPost()}">${post.getPostName()}</option>
                            </c:if>
                        </c:forEach>
                    </select><br>

                    <span class="secondary-text">
                        <label for="postId" class="label">Сущность</label>
                    </span></span>
                </li>
                <div id="divCompany">&nbsp;
                    <c:if test="${userRed.getCompanyUnit()!=NULL}">
                        <li ripple>
                        <span class="item-text">
                            <select class="dropdown-menu" id="companyId" name="companyId"
                                    onchange="getCompanyUnits(this)">
                                <option value="-1">Выбрете организацию</option>
                        <c:forEach items="${companyList}" var="company">
                            <c:if test="${company.getIdCompany() == userRed.getCompanyUnit().getCompany().getIdCompany()}">
                                <option value="${company.getIdCompany()}" selected>${company.getCompanyName()}</option>
                            </c:if>
                            <c:if test="${company.getIdCompany() != userRed.getCompanyUnit().getCompany().getIdCompany()}">
                                <option value="${company.getIdCompany()}">${company.getCompanyName()}</option>
                            </c:if>
                        </c:forEach>
                                </select><br>
                            <span class="secondary-text">
                                <label for="companyId" class="label">Организация</label>
                            </span>
                        </span>
                        </li>
                    </c:if>
                </div>
                <div id="divCompanyAndUnits">
                    <c:if test="${userRed.getCompanyUnit()!=NULL}">
                        <li ripple>
                        <span class="item-text">
                            <select class="dropdown-menu" id="companyUnitId" name="companyUnitId">
                                <option value="-1">Выбрете подразделение</option>
                                <c:forEach
                                        items="${companyUnitList}"
                                        var="companyUnit">
                                    <c:if test="${companyUnit.getIdCompanyUnit() == userRed.getCompanyUnit().getIdCompanyUnit()}">
                                <option value="${companyUnit.getIdCompanyUnit()}"
                                        selected>${companyUnit.getCompanyUnitName()}</option>
                                    </c:if>
                                    <c:if test="${companyUnit.getIdCompanyUnit() != userRed.getCompanyUnit().getIdCompanyUnit()}">
                                <option value="${companyUnit.getIdCompanyUnit()}">${companyUnit.getCompanyUnitName()}
                                </option>
                                    </c:if>
                                </c:forEach>
                                </select><br>
                            <span class="secondary-text">
                                <label for="companyUnitId" class="label">Подразделение</label>
                            </span>
                        </span>
                        </li>
                    </c:if>
                </div>
                <li ripple>
    <span class="item-text">
    <div class="switch">
        <c:if test="${userRed.getIsEnable().equals(\"true\")}">
            <input type="checkbox" id="user-active-flag" name="user-active-flag" value="0" checked/>
        </c:if>
        <c:if test="${!userRed.getIsEnable().equals(\"true\")}">

            <input type="checkbox" id="user-active-flag" name="user-active-flag" value="0"/>
        </c:if>
        <label for="user-active-flag">&nbsp;</label>
        <span class="secondary-text">
        <label for="user-active-flag" class="label">Активен</label>
        </span>
    </div>

    </span>
                </li>
            </ul>
            <br>
            <input type="hidden" id="red_form" value="1" name="red_form">
            <input type="hidden" id="user-red-id-label" name="user-red-id-label" value="${userRed.getId()}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
            <button class="button raised color-white bg-blue-500" type="submit" id="updateButton" name="updateButton">
                Сохранить
            </button>
        </form>
    <span class="secondary-text">
    <label for="updateButton" class="label color-green-500">${userRed.getError()}
    </label>
    </span>

    </div>
    <!-- <div class="section">
    <form action="/usersred" method="post">
    <input type="hidden" id="delete_form" value="1" name="delete_form">
    <input type="hidden" id="user-delete-id-label" name="user-delete-id-label" value="${userRed.getId()}">
    <button class="button raised color-white bg-red-500" type="submit">Удалить</button>
    </form>
    </div>
    -->
    <script src="js/usersRed.js"></script>
</sec:authorize>
<sec:authorize access="!hasRole('ROLE_USERS_RED')">
    Нужна авторизация
</sec:authorize>
<%@ include file="footer.jsp" %>
