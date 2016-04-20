<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_VIEW')">
    <div class="section">
            ${message}
                Driver
    </div>

    <div class="section">
        Загрузка:<br>
        <c:set var="readonlyTmp" value="" scope="application"/>
        <c:if test="${bid.getBid_is_freeze() !=null}">
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
            <br>
            Доставка:<br>
            Секции на машине:<br>
            <c:forEach items="${bidDetailsCar}" var="bidDetails">
                    <input type="text" class="text-input border-green-500"
                           value="Секция ${bidDetails.getSection().getOilSectionName()} (${bidDetails.getSection().getVol()}л.)"
                           readonly>&nbsp;
                    <input type="text" class="text-input border-green-500"
                           value="${bidDetails.getOilType().getOilTypeName()}" readonly>&nbsp;
                    <input type="text" class="text-input border-green-500"
                           value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>&nbsp;<br>
            </c:forEach>
            Секции на прицепе:<br>
            <c:forEach items="${bidDetailsTrailer}" var="bidDetails">
                <input type="text" class="text-input border-green-500"
                       value="Секция ${bidDetails.getSection().getOilSectionName()} (${bidDetails.getSection().getVol()}л.)"
                       readonly>&nbsp;
                <input type="text" class="text-input border-green-500"
                       value="${bidDetails.getOilType().getOilTypeName()}" readonly>&nbsp;
                <input type="text" class="text-input border-green-500"
                       value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>&nbsp;<br>
            </c:forEach>
        </c:if>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
