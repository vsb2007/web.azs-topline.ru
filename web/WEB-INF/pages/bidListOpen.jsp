<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_LIST')">
    <div class="section">
            ${message}
    </div>

    <div class="section">
        <ul class="list">
            <c:forEach items="${bidsList}" var="bid">
                <c:if test="${!bid.getBid_is_freeze()}">
                    <c:set var="bgColor" value="bg-blue-500" scope="application"/>
                </c:if>
                <c:if test="${bid.getBid_is_freeze()}">
                    <c:set var="bgColor" value="bg-red-500" scope="application"/>
                </c:if>
                <c:if test="${bid.isDone()}">
                    <c:set var="bgColor" value="bg-green-500" scope="application"/>
                </c:if>
                <li ripple>
                    <form action="bidView" method="post">
                        <input value="${bid.getId_bid()}" name="bidIdButton" id="bidIdButton${bid.getId_bid()}"
                               type="hidden">
                        <button class="button raised color-white ${bgColor}" type="submit" style="width: 15em;">
                            <span class="item-text">${bid.getBid_date_create()} №${bid.getId_bid()}
			                    <span class="secondary-text">${bid.getCreateUser().getName()} ${bid.getDriver().getDriverFio()}</span>
		                    </span>
                        </button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <sec:authorize access="hasRole('ROLE_BID_RED')">
                        &nbsp;
                        <c:if test="${bid.isDone()}">
                            <c:set var="actoinUrl" value="bidClose"/>
                        </c:if>
                        <c:if test="${!bid.isDone()}">
                            <c:set var="actoinUrl" value="bidRed"/>
                        </c:if>
                        <form action="${actoinUrl}" method="post">
                            <input value="${bid.getId_bid()}" name="bidIdButton" id="bidIdButton${bid.getId_bid()}"
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
