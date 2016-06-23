<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_LIST')">
    <div class="section">
        <sec:authorize access="hasRole('ROLE_BID_CREATE')">
            <form action="bidcreateform" method="post">
                <select class="dropdown-menu" id="oilStorage" name="oilStorage" onchange="">
                    <option value="-1">Пункт загрузки</option>
                    <c:forEach items="${oilStorageList}" var="oilStorage">
                        <option value="${oilStorage.getIdOilStorage()}">${oilStorage.getOilStorageName()}</option>
                    </c:forEach>
                </select><br>
                <select class="dropdown-menu" id="driver" name="driver" onchange="">
                    <option value="-1">Водитель</option>
                    <c:forEach items="${driversList}" var="driver">
                        <option value="${driver.getIdDriver()}">${driver.getDriverFio()}</option>
                    </c:forEach>
                </select><br>
                <select class="dropdown-menu" id="driverCanUpdateIn" name="driverCanUpdateIn" onchange="">
                    <option value="0">Водитель не может принимать топливо</option>
                    <option value="1">Водитель может принимать топливо</option>
                </select><br>
                <select class="dropdown-menu" id="driverCanUpdateOut" name="driverCanUpdateOut" onchange="">
                    <option value="0">Водитель не может сливать топливо</option>
                    <option value="1">Водитель может сливать топливо</option>
                </select><br>
                <select class="dropdown-menu" id="car" name="car" onchange="onCarSelect(this)">
                    <option value="-1">Выбрать машину</option>
                    <c:forEach items="${carsList}" var="car">
                        <option value="${car.getId_car()}">${car.getCar_name()}</option>
                    </c:forEach>
                </select><br>
                <div id="divCarSectionId">
                </div>
                <div id="divTrailerId">
                </div>
                <div id="divTrailerSectionId">
                </div>
                <button class="button raised bg-blue-500 color-white">Добавить заявку</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
            </form>
        </sec:authorize>
    </div>

    <script src="js/bidCreate.js"></script>

</sec:authorize>
<%@ include file="footer.jsp" %>
