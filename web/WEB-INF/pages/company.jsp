<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="io.bgroup.topline.model.SiteUser" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="io.bgroup.topline.model.Company" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_COMPANY_LIST')">
    <div class="section">
        <ul class="list">
            <c:forEach items="${companyList}" var="company">
                <li ripple>
                    <form action="companyView" method="post">
                        <button class="button raised color-white bg-blue-500" value="${company.getIdCompany()}"
                                name="buttonCompanyRed" id="buttonCompanyRed"
                                style="width: 150px"
                        >
                                ${company.getCompanyName()}
                        </button>
                        <input type="hidden" name="companyId" id="companyId" value="${company.getIdCompany()}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="section">
        <sec:authorize access="hasRole('ROLE_COMPANY_ADD')">
            <form action="companyAdd" method="post">
                <input type="text" class="text-input border-green-500" placeholder="Company name" required
                       name="companyName"
                       id="companyName"> <br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="button raised bg-blue-500 color-white">Добавить организацию</button>
            </form>
            <p class="text">${errorCompanyAdd}</p>
        </sec:authorize>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
