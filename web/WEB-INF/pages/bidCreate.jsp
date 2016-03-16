<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_LIST')">
    <div class="section">
        <sec:authorize access="hasRole('ROLE_BID_CREATE')">
            <form action="appcreate" method="post">
                <input type="text" class="text-input border-green-500" placeholder="Заявка (Номер)" required
                       name="bidname"
                       id="bidname"> <br>
                <select class="dropdown-menu" id="car" name="car" onchange="onCarSelect(${car.getId_cars()})">
                    <option value="-1">Выбрать машину</option>
                    <c:forEach items="${carsList}" var="car">
                        <option value="${car.getId_cars()}">${car.getCars_name()}</option>
                    </c:forEach>
                </select><br>
                <c:forEach items="${carsList}" var="car">
                    <div id="divCarSectionId_${car.getId_cars()}">
                        <ul>
                            <c:forEach items="${car.getCarSections()}" var="carSection">
                                <li>
                                    Секция ${carSection.getCarSectionName()} (${carSection.getVol()} л.) &nbsp;
                                    <select class="dropdown-menu"

                                            id="oilType_${car.getId_cars()}_${carSection.getId_section()}"
                                            name="oilType_${car.getId_cars()}_${carSection.getId_section()}">
                                        <option value="-1">Пустая секция</option>
                                        <c:forEach items="${oilTypesList}" var="oilType">
                                            <option value="${oilType.getId_oilType()}">${oilType.getOilTypeName()}</option>
                                        </c:forEach>
                                    </select>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:forEach>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="button raised bg-blue-500 color-white">Добавить заявку</button>
            </form>
        </sec:authorize>
    </div>

    <script src="js/bidCreate.js"></script>

</sec:authorize>
<%@ include file="footer.jsp" %>
