<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_COMPANY_RED')">
    <div class="section">
        <form action="companyRed" method="get">
            <ul class="list">
                <li ripple>
                <span class="item-text">
                <input type="text" class="text-input border-green-500" placeholder="Имя организации"
                       value="${company.getCompanyName()}"
                       id="companyName" name="companyName">
                    <input type="hidden" name="companyId" id="companyId" value="${company.getIdCompany()}">
                <span class="secondary-text">
                <label for="companyName">Имя организации</label>
                </span>
                </span>
                </li>
            </ul>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="button raised bg-blue-500 color-white">Редактировать организацию</button>
        </form>
        <p class="text">${errorCompanyRed}</p>
    </div>
    <div class="section">
        <sec:authorize access="hasRole('ROLE_COMPANY_RED')">
            <form action="companyUnitAdd" method="post">
                <input type="text" class="text-input border-green-500" placeholder="Company unit name" required
                       name="companyUnitName"
                       id="companyUnitName"> <br>
                <input type="hidden" name="companyId" id="companyId" value="${company.getIdCompany()}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="button raised bg-blue-500 color-white">Добавить подразделение</button>
            </form>
            <p class="text">${errorCompanyUnitAdd}</p>
        </sec:authorize>
    </div>
    <div class="section">
        <ul class="list">
            <c:forEach items="${companyUnitList}" var="companyUnit">
                <li ripple>
                    <form action="companyUnitView" method="get">
                        <button class="button raised color-white bg-blue-500" value="${companyUnit.getIdCompanyUnit()}"
                                name="buttonCompanyUnitRed" id="buttonCompanyUnitRed"
                                style="width: 150px"
                        >
                                ${companyUnit.getCompanyUnitName()}
                        </button>
                        <input type="hidden" name="companyUnitId" id="companyUnitId"
                               value="${companyUnit.getIdCompanyUnit()}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </div>

</sec:authorize>
<%@ include file="footer.jsp" %>
