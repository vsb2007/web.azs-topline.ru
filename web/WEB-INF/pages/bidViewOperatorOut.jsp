<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_VIEW')">
    <div class="section">
            ${message}
        OperOut
    </div>
    <div class="section">
        Загрузка:<br>
        <c:set var="readonlyTmp" value="" scope="application"/>
        <c:if test="${bid.getBid_is_freeze() !=null}">
            <sec:authorize access="hasRole('ROLE_BID_UPDATE')">
                <c:if test="${(isCarSectionBidUp==false || bidDetailsCar==null || (isCarSectionBidUp==true && bidDetailsCar!=null && (isTrailerSectionBidUp==false && bidDetailsTrailer!=null)))
                        && (isTrailerSectionBidUp==false || bidDetailsTrailer==null || (isTrailerSectionBidUp==true && bidDetailsTrailer!=null && (isCarSectionBidUp==false && bidDetailsCar!=null)))}">

                    <c:if test="${bid.getBid_is_freeze() !=null && !bid.getBid_is_freeze().equals(\"0\")}">
                        <form action="bidUpdate" method="post">
                    </c:if>
                </c:if>
            </sec:authorize>
            <c:if test="${!bid.getBid_is_freeze().equals(\"0\")}">
                <c:set var="freeze" value="out" scope="application"/>
                <c:set var="submitButtonValue" value="Принять топливо" scope="application"/>
            </c:if>
            <input type="text" class="text-input border-green-500" placeholder="Заявка (Номер)" required
                   name="bidNumber" value="Заявка №${bid.getId_bid()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Точка загрузки" required
                   name="oilStorageIn" value="${bid.getOilStorageIn().getOilStorageName()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Водитель" required
                   name="driver" value="${bid.getDriver().getDriverFio()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Машина" required
                   name="car" value="${bid.getCar().getCar_name()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Прицеп" required
                   name="trailer" value="${bid.getTrailer().getTrailer_number()}" readonly> <br>
            <br>
            <c:if test="${bid.getBid_is_freeze() !=null && !bid.getBid_is_freeze().equals(\"0\")}">
                Доставка:<br>
                Секции на машине:<br>
                <c:forEach items="${bidDetailsCar}" var="bidDetails">
                    <%@ include file="bidViewOperatorOutSection.jsp" %>
                </c:forEach>
                Секции на прицепе:<br>
                <c:forEach items="${bidDetailsTrailer}" var="bidDetails">
                    <%@ include file="bidViewOperatorOutSection.jsp" %>
                </c:forEach>
                <sec:authorize access="hasRole('ROLE_BID_UPDATE')">
                    <c:if test="${(isCarSectionBidUp==false || bidDetailsCar==null || (isCarSectionBidUp==true && bidDetailsCar!=null && (isTrailerSectionBidUp==false && bidDetailsTrailer!=null)))
                        && (isTrailerSectionBidUp==false || bidDetailsTrailer==null || (isTrailerSectionBidUp==true && bidDetailsTrailer!=null && (isCarSectionBidUp==false && bidDetailsCar!=null)))}">
                        <button class="button raised bg-blue-500 color-white">${submitButtonValue}</button>
                        <input type="hidden" name="bidId" value="${bid.getId_bid()}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
                        <c:if test="${bid.getBid_is_freeze()!=null && !bid.getBid_is_freeze().equals(\"0\")}">
                            </form>
                        </c:if>
                    </c:if>
                </sec:authorize>
            </c:if>
        </c:if>
    </div>
</sec:authorize>
<%@ include file="footer.jsp" %>
