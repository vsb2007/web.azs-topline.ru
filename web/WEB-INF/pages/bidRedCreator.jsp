<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_RED')">
    <div class="section">
            ${message}
        Creator
    </div>
    <div class="section">
        Загрузка:<br>
        <c:if test="${bid.getBid_is_freeze() !=null}">
            <form action="javascript:void(null);" method="post" id="bidRedUpdateForm" onsubmit="sendFormForBidRed()">
                <input type="text" class="text-input border-green-500" placeholder="Заявка (Номер)" required
                       name="bidNumber" value="${bid.getName()}" readonly> <br>
                <c:if test="${bid.getBid_is_freeze().equals(\"0\")}">
                    <select class="dropdown-menu" id="oilStorage" name="oilStorage" onchange="">
                        <c:forEach items="${oilStorageList}" var="oilStorage">
                            <c:if test="${oilStorage.getIdOilStorage().equals(bid.getOilStorageIn().getIdOilStorage())}">
                                <option value="${oilStorage.getIdOilStorage()}"
                                        selected>${oilStorage.getOilStorageName()}</option>
                            </c:if>
                            <c:if test="${!oilStorage.getIdOilStorage().equals(bid.getOilStorageIn().getIdOilStorage())}">
                                <option value="${oilStorage.getIdOilStorage()}">${oilStorage.getOilStorageName()}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </c:if>
                <c:if test="${!bid.getBid_is_freeze().equals(\"0\")}">
                    <input type="text" class="text-input border-green-500" placeholder="Точка загрузки" required
                           name="oilStorageName" value="${bid.getOilStorageIn().getOilStorageName()}" readonly>
                    <input type="hidden" required
                           name="oilStorage" value="${bid.getOilStorageIn().getIdOilStorage()}" readonly>
                </c:if><br>
                <select class="dropdown-menu" id="driver" name="driver" onchange="">
                    <c:forEach items="${driversList}" var="driver">
                        <c:if test="${driver.getIdDriver().equals(bid.getDriver().getIdDriver())}">
                            <option value="${driver.getIdDriver()}" selected>${driver.getDriverFio()}</option>
                        </c:if>
                        <c:if test="${!driver.getIdDriver().equals(bid.getDriver().getIdDriver())}">
                            <option value="${driver.getIdDriver()}">${driver.getDriverFio()}</option>
                        </c:if>
                    </c:forEach>
                </select><br>
                    <%-- машина --%>
                    <%-- Топливо НЕ отпущено --%>
                <c:if test="${bid.getBid_is_freeze().equals(\"0\")}">
                    <select class="dropdown-menu" id="car" name="car" onchange="onCarSelect(this)">
                        <option value="-1">Выбрать машину</option>
                        <c:forEach items="${carsList}" var="car">
                            <c:if test="${car.getId_car().equals(bid.getCar().getId_car())}">
                                <option value="${car.getId_car()}" selected>${car.getCar_name()}</option>
                            </c:if>
                            <c:if test="${!car.getId_car().equals(bid.getCar().getId_car())}">
                                <option value="${car.getId_car()}">${car.getCar_name()}</option>
                            </c:if>
                        </c:forEach>
                    </select><br>
                </c:if>
                    <%-- Топливо отпущено --%>
                <c:if test="${!bid.getBid_is_freeze().equals(\"0\")}">
                    <c:if test="${bidDetailsCar!=null}">
                        <input type="text" class="text-input border-green-500" placeholder="Машина" required
                               value="${bid.getCar().getCar_name()}" readonly> <br>
                        <input type="hidden" required
                               name="car" value="${bid.getCar().getId_car()}" readonly> <br>
                    </c:if>
                    <c:if test="${bidDetailsCar==null}">
                        <select class="dropdown-menu" id="car" name="car" onchange="">
                            <option value="-1">Выбрать машину</option>
                            <c:forEach items="${carsList}" var="car">
                                <c:if test="${car.getId_car().equals(bid.getCar().getId_car())}">
                                    <option value="${car.getId_car()}" selected>${car.getCar_name()}</option>
                                </c:if>
                                <c:if test="${!car.getId_car().equals(bid.getCar().getId_car())}">
                                    <option value="${car.getId_car()}">${car.getCar_name()}</option>
                                </c:if>
                            </c:forEach>
                        </select><br>
                    </c:if>
                </c:if>
                    <%-- прицеп --%>
                    <%-- Топливо НЕ отпущено --%>
                <c:if test="${bid.getBid_is_freeze().equals(\"0\")}">
                    <select class="dropdown-menu" id="trailerId" name="trailerId" onchange="onTrailerSelect(this)">
                        <option value="-1">Выбрать прицеп</option>
                        <c:forEach items="${trailersList}" var="trailer">
                            <c:if test="${trailer.getId_trailer().equals(bid.getTrailer().getId_trailer())}">
                                <option value="${trailer.getId_trailer()}" selected>
                                    Прицеп: ${trailer.getTrailer_number()}
                                </option>
                            </c:if>
                            <c:if test="${!trailer.getId_trailer().equals(bid.getTrailer().getId_trailer())}">
                                <option value="${trailer.getId_trailer()}">Прицеп: ${trailer.getTrailer_number()}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </c:if>
                    <%-- Топливо отпущено --%>
                <c:if test="${!bid.getBid_is_freeze().equals(\"0\")}">
                    <input type="text" class="text-input border-green-500" placeholder="Прицеп" required
                           name="trailer" value="${bid.getTrailer().getTrailer_number()}" readonly>
                    <input type="hidden" class="text-input border-green-500" placeholder="Прицеп" required
                           name="trailerId" value="${bid.getTrailer().getId_trailer()}" readonly>
                </c:if>
                <br>
                Доставка:<br>
                Секции на машине:<br>
                <div id="divCarSectionId">
                    <c:forEach items="${bidDetailsCar}" var="bidDetails">
                        <%@ include file="bidRedCreatorSection.jsp" %>
                    </c:forEach>
                </div>
                Секции на прицепе:<br>
                <div id="divTrailerSectionId">
                    <c:forEach items="${bidDetailsTrailer}" var="bidDetails">
                        <%@ include file="bidRedCreatorSection.jsp" %>
                    </c:forEach>
                </div>
                <button type="submit" class="button raised bg-blue-500 color-white">Сохранить изменения</button>
                <input type="hidden" name="bidId" value="${bid.getId_bid()}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
            </form>
            <div id="results"></div>
        </c:if>
    </div>
    <script src="js/bidRed.js"></script>
    <script src="js/jquery-2.2.3.min.js"></script>
</sec:authorize>
<%@ include file="footer.jsp" %>
