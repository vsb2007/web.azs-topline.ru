<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_SALE_VIEW')">
    <div class="section">
            ${message}<br>
        Operator
    </div>
    <div class="section">
        <c:if test="${!sale.isClose()}">
            <sec:authorize access="hasRole('ROLE_SALE_UPDATE')">
                <c:if test="${!sale.isDone()}">
                    <form action="saleUpdate" method="post" id="saleUpdateForm">
                </c:if>
            </sec:authorize>
            <div class="grid-list">
                <div class="tile">
                    <input type="text" class="text-input border-green-500" placeholder="Заявка (Номер)" required
                           name="" value="Заявка №${sale.getId()}" readonly>
                    <input type="hidden" required name="saleNumber" id="saleNumber" value="${sale.getId()}">
                    <div>
                        <span class="secondary-text">Номер заявки</span>
                    </div>
                </div>
                <br>
                <div class="tile">
                    <c:if test="${sale.getCompanyUnit() != null}">
                        <input type="text" class="text-input border-green-500" placeholder="Точка загрузки" required
                               name="azs" value="${sale.getCompanyUnit().getCompanyUnitName()}" readonly> <br>
                    </c:if>
                    <c:if test="${sale.getCompanyUnit() == null}">
                        <input type="text" class="text-input border-green-500" placeholder="Точка загрузки" required
                               name="azs" value="Отел логистики" readonly> <br>
                    </c:if>
                    <div>
                        <span class="secondary-text">Ответственный за отгрузку</span>
                    </div>
                </div>
                <br>
                <div class="tile">
                    <input type="text" class="text-input border-blue-500" readonly required
                           value="${sale.getOrganization().getOrganizationName()}">
                    <div>
                        <span class="secondary-text">Грузополучатель</span>
                    </div>
                </div>
                <br>
                <div class="tile">
                    <input type="text" class="text-input border-green-500" readonly required
                           value="${sale.getFio()}" name="fio">
                    <div>
                        <span class="secondary-text">Ф.И.О.</span>
                    </div>
                </div>
                <div class="tile">
                    <input type="text" class="text-input border-green-500" name="carNumber" required
                           value="${sale.getCarNumber()}">
                    <div>
                        <span class="secondary-text">Номер Машины</span>
                    </div>
                </div>
                <br>
                <div class="tile">
                    <input class="text-input border-green-500" name="oilTypeId" readonly
                           value="${sale.getOilType().getOilTypeName()}">
                    <div>
                        <span class="secondary-text">Тип топлива</span>
                    </div>
                </div>
                <br>
                <div class="tile">
                    <c:if test="${sale.getLt()==0}">
                        <input class="text-input border-green-500" name="lt" readonly
                               value="Литры">
                    </c:if>
                    <c:if test="${sale.getLt()==1}">
                        <input class="text-input border-green-500" name="lt" readonly
                               value="Тонны">
                    </c:if>
                    <div>
                        <span class="secondary-text">Единицы измерения</span>
                    </div>
                </div>
                <br>
                <div class="tile">
                    <input type="number" class="text-input border-green-500"
                           value="${sale.getCol()}" placeholder="Количество единиц" id="colLiters" name="colLiters"
                           required onchange="getSum()">
                    <div>
                        <span class="secondary-text">Количество единиц</span>
                    </div>
                </div>
                    <%--
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getPriceOil()}" placeholder="Цена за единицу" id="priceLiters" name="priceLiters"
                               required onchange="getSum()">
                        <div>
                            <span class="secondary-text">Цена за единицу</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getPriceShipping()}" placeholder="Цена доставки" id="priceShipping" name="priceShipping"
                               required onchange="getSum()">
                        <div>
                            <span class="secondary-text">Цена доставки</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getSum()}" placeholder="Сумма" id="sum" name="sum" required readonly>
                        <div>
                            <span class="secondary-text">Сумма</span>
                        </div>
                    </div>
                    --%>
            </div>
            <br>
            <c:if test="${!sale.isDone()}">
                <button class="button raised bg-blue-500 color-white" disabled="disabled" id="addBidButton">
                    Отпустить топливо
                </button>
                <button class="button raised bg-blue-500 color-white" type="button" onclick="checkAddBidForm()">
                    Проверить данные
                </button>
                <sec:authorize access="hasRole('ROLE_SALE_UPDATE')">
                    <%--
                    кнопка о прочтении
                    --%>
                    <%@ include file="saleOilViewCheckReadButton.jsp" %>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
                    </form>
                </sec:authorize>
            </c:if>
        </c:if>
    </div>

    <script src="js/jquery.js"></script>
    <script>
        function getSum() {
            document.getElementById("addBidButton").setAttribute("disabled", "disabled");
            var colLiters = document.getElementById("colLiters").value;
            var priceLiters = document.getElementById("priceLiters").value;
            var priceShipping = document.getElementById("priceShipping").value;
            if (colLiters == null) return;
            if (priceLiters == null) return;
            if (priceShipping == null) return;
            var sum = colLiters * priceLiters + priceShipping;
            document.getElementById("sum").value = (priceShipping * 1 + colLiters * priceLiters).toFixed(2);
        }
        function checkAddBidForm() {
            document.getElementById("addBidButton").removeAttribute("disabled");
        }
    </script>
</sec:authorize>
<%@ include file="footer.jsp" %>
