<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_COMPANY_RED')">
    <div class="section">
        <form action="companyUnitRed" method="get">
            <ul class="list">
                <li ripple>
                <span class="item-text">
                <input type="text" class="text-input border-green-500" placeholder="Имя организации"
                       value="${companyUnit.getCompanyUnitName()}"
                       id="companyUnitName" name="companyUnitName">
                    <input type="hidden" name="companyUnitId" id="companyUnitId" value="${companyUnit.getIdCompanyUnit()}">
                <span class="secondary-text">
                <label for="companyUnitName">Имя подразделения</label>
                </span>
                </span>
                </li>
            </ul>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="button raised bg-blue-500 color-white">Редактировать</button>
        </form>
        <p class="text">${errorCompanyUnitRed}</p>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
