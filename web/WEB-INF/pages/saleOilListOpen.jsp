<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_SALE_LIST')">
    <div class="section">
            ${message}
    </div>

    <div class="section">
        <ul class="list">
            <c:forEach items="${saleOilList}" var="sale">
                <c:if test="${!sale.isRead()}">
                    <c:set var="bgColor" value="bg-blue-500" scope="application"/>
                </c:if>
                <c:if test="${sale.isRead()}">
                    <c:set var="bgColor" value="bg-red-500" scope="application"/>
                </c:if>
                <c:if test="${sale.isDone()}">
                    <c:set var="bgColor" value="bg-green-500" scope="application"/>
                </c:if>
                <li ripple>
                    <form action="bidView" method="post">
                        <input value="${sale.getId()}" name="bidIdButton" id="bidIdButton${sale.getId()}"
                               type="hidden">
                        <button class="button raised color-white ${bgColor}" type="submit" style="width: 15em;">
                            <span class="item-text">${sale.getDateCreate()} №${sale.getId()}
			                    <span class="secondary-text">${sale.getUserCreate().getName()} ${sale.getOrganization().getOrganizationName()}</span>
		                    </span>
                        </button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <sec:authorize access="hasRole('ROLE_SALE_RED')">
                        &nbsp;
                        <c:if test="${sale.isDone()}">
                            <c:set var="actionUrl" value="saleOilClose"/>
                        </c:if>
                        <c:if test="${!sale.isDone()}">
                            <c:set var="actionUrl" value="saleOilRed"/>
                        </c:if>
                        <form action="${actionUrl}" method="post">
                            <input value="${bid.getId_bid()}" name="saleIdButton" id="saleIdButton${sale.getId()}"
                                   type="hidden">
                            <button class="button raised color-white bg-blue-500" type="submit" style="width: 10em;">
                            <span class="item-text">Редактировать
			                    <span class="secondary-text">Закрыть</span>
		                    </span>
                            </button>
                                <%--<span class="item-text" style="font-size: small"> Машина: ${bid.getCar().getCar_name()}<br>
                                   Прицеп: ${bid.getTrailer().getTrailer_number()}</span> --%>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </sec:authorize>
                </li>
            </c:forEach>
        </ul>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
