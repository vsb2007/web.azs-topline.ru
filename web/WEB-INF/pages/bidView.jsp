<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_VIEW')">
    <div class="section">
            ${message}
    </div>
    <div class="section">
        <sec:authorize access="!hasRole('ROLE_BID_RED')">
            Загрузка:<br>
            <sec:authorize access="hasRole('ROLE_BID_UPDATE')">
                <form action="bidUpdate" method="post">
            </sec:authorize>
            <c:set var="readonly" value="" scope="application"/>
            <input type="text" class="text-input border-green-500" placeholder="Заявка (Номер)" required
                   name="bidNumber" value="${bid.getName()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Точка загрузки" required
                   name="oilStorageIn" value="${bid.getOilStorageIn().getOilStorageName()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Водитель" required
                   name="driver" value="${bid.getDriver().getDriverFio()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Машина" required
                   name="car" value="${bid.getCar().getCar_name()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Прицеп" required
                   name="trailer" value="${bid.getTrailer().getTrailer_number()}" readonly> <br>
            <sec:authorize access="hasRole('ROLE_BID_UPDATE')">
                <button class="button raised bg-blue-500 color-white">Сохранить изменения</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
                </form>
            </sec:authorize>
            <br>
            Доставка:<br>
        </sec:authorize>
    </div>
    <script src="js/bidCreate.js"></script>
</sec:authorize>
<%@ include file="footer.jsp" %>
