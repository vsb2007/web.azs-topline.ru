<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_LIST')">
    <div class="section">
        ${message}
    </div>
    <c:forEach items="${bidsList}" var="bid">
        ${bid.getCreateUser().getName()} ${bid.getDriver().getDriverFio()} <br>
    </c:forEach>


    <script src="js/bidCreate.js"></script>

</sec:authorize>
<%@ include file="footer.jsp" %>
