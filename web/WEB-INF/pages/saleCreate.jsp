<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_LIST')">
    <div class="section">
        <sec:authorize access="hasRole('ROLE_SALE_CREATE')">
            <h3>Продажа Топлива</h3>
            <form action="addSale" method="post">
                <div class="grid-list">
                    <div class="tile">
                        <select class="dropdown-menu" id="idUnit" name="idUnit" onchange="">
                            <option value="-1">Отдел Логистики</option>
                            <c:forEach items="${oilStorageList}" var="oilStorage">
                                <option value="${oilStorage.getCompanyUnit().getIdCompanyUnit()}">${oilStorage.getCompanyUnit().getCompanyUnitName()}</option>
                            </c:forEach>
                        </select>
                        <div>
                            <span class="secondary-text">Ответственный за отгрузку</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type=text class="text-input border-blue-500"
                               id="OrgId_text" onChange="getOrganization(this,'_OrgId_span')">
                        <div>
                            <span class="secondary-text">Фильтр организаций</span>
                        </div>
                    </div>
                    <div class="tile">
                        <span id="_OrgId_span"><input type="text" class="text-input border-blue-500" readonly
                                                      placeholder="Установите фильтр" required> </span>
                        <div>
                            <span class="secondary-text">Грузополучатель</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type="text" class="text-input border-green-500"
                               value="" placeholder="Ф.И.О." name="fio" required>
                        <div>
                            <span class="secondary-text">Ф.И.О.</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="text" class="text-input border-green-500"
                               value="" placeholder="Номер Машины" id="carNumber" name="carNumber" required>
                        <div>
                            <span class="secondary-text">Номер Машины</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <select class="dropdown-menu" id="oilTypeId" name="oilTypeId" onchange="">
                            <option value="-1">Выбрать Топливо</option>
                            <c:forEach items="${oilTypeList}" var="oilType">
                                <option value="${oilType.getId_oilType()}">${oilType.getOilTypeName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br>
                    <div class="tile">
                        <select id="lt" name="lt" class="dropdown-menu" required>
                            <option></option>
                            <option value="0">Литры</option>
                            <option value="1">Тонны</option>
                        </select>
                        <div>
                            <span class="secondary-text">Единицы измерения</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="" placeholder="Количество единиц" id="colLiters" name="colLiters"
                               required onchange="getSum()">
                        <div>
                            <span class="secondary-text">Количество единиц</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="" placeholder="Цена за единицу" id="priceLiters" name="priceLiters"
                               required onchange="getSum()">
                        <div>
                            <span class="secondary-text">Цена за единицу</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="" placeholder="Цена доставки" id="priceShipping" name="priceShipping"
                               required onchange="getSum()">
                        <div>
                            <span class="secondary-text">Цена доставки</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="" placeholder="Сумма" id="sum" name="sum" required readonly>
                        <div>
                            <span class="secondary-text">Сумма</span>
                        </div>
                    </div>
                </div>
                <br>
                <button class="button raised bg-blue-500 color-white" disabled="disabled" id="addBidButton">Добавить
                    заявку
                </button>
                <button class="button raised bg-blue-500 color-white" type="button" onclick="checkAddBidForm()">
                    Проверить данные
                </button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
            </form>
        </sec:authorize>
    </div>

    <script src="js/jquery.js"></script>
    <script src="js/jquery-ui.js"></script>
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

        function getOrganization(inText, idSpan) {
            document.getElementById("addBidButton").setAttribute("disabled", "disabled");
            var xmlhttp;
            document.getElementById(idSpan).innerHTML = "<ul class='zmdi-hc-ul'>" +
                    "<li><i class='zmdi-hc-li zmdi zmdi-refresh zmdi-hc-spin'></i>loading...</li>" +
                    "</ul>";
            if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            }
            else {// code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById(idSpan).innerHTML = xmlhttp.responseText;
                }
            }
            var token = document.getElementById("token");
            xmlhttp.open("POST", "orgGetListByFilter", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("filter=" + inText.value + "&" + token.name + "=" + token.value + "&idSelect=" + inText.id);
        }
        function checkAddBidForm() {
            document.getElementById("addBidButton").removeAttribute("disabled");
        }

    </script>

</sec:authorize>
<%@ include file="footer.jsp" %>
