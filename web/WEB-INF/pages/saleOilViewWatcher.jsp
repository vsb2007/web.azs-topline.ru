<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_SALE_VIEW')">
    <div class="section">
            ${message}<br>
        Watcher
    </div>
    <div class="section">
        <c:if test="${!sale.isClose()}">
            <sec:authorize access="hasRole('ROLE_SALE_UPDATE')">
                <div class="grid-list">
                    <div class="tile">
                        <input type="text" class="text-input border-green-500" placeholder="Заявка (Номер)" required
                               name="" value="Заявка №${sale.getId()}" readonly>
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
                        <c:if test="${sale.getOrganization()!=null}">
                            <c:if test="${sale.getOrganization().getOrganizationPacts()!=null}">
                                <c:forEach items="${sale.getOrganization().getOrganizationPacts()}" var="pact">
                                    <c:if test="${pact.getId()  == sale.getOrgPactId()}">
                                        <input type="text" class="text-input border-blue-500"
                                               value="${pact.getPactName()}">
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </c:if>
                        <div>
                            <span class="secondary-text">Договор грузополучателя</span>
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
                               required onchange="" step="any" readonly>
                        <div>
                            <span class="secondary-text">Количество единиц</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getPriceOil()}" placeholder="Цена за единицу" id="priceLiters"
                               name="priceLiters"
                               required onchange="getSum()" step="any" readonly>
                        <div>
                            <span class="secondary-text">Цена за единицу</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getPriceShipping()}" placeholder="Цена доставки" id="priceShipping"
                               name="priceShipping"
                               required onchange="getSum()" step="any" readonly>
                        <div>
                            <span class="secondary-text">Цена доставки</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">

                        <c:forEach items="${sale.getOrganization().getOrganizationPacts()}" var="pact">
                            <c:if test="${pact.getId()  == sale.getOrgPactId()}">
                                <input type="number" class="text-input border-green-500" readonly
                                       value="${fn:substringBefore(pact.getSum(), '.')}">
                            </c:if>
                        </c:forEach>

                        <div>
                            <span class="secondary-text">Доступная сумма</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getSum()}" placeholder="Сумма" id="sum" name="sum" required readonly
                               step="any">
                        <div>
                            <span class="secondary-text">Сумма по сделке</span>
                        </div>
                    </div>
                </div>
                <br>
                <c:if test="${sale.isBlock()}">
                    <span id="status">Сделка заблокирована</span><br>
                </c:if>
                <c:if test="${!sale.isBlock()}">
                    <span id="status">Сделка открыта</span><br>
                </c:if>
                <button class="button raised bg-blue-500 color-white" disabled="disabled" id="addBidButton"
                        onclick="changeStatus()">
                    Поменять статус
                </button>
                <button class="button raised bg-blue-500 color-white" type="button" onclick="checkAddBidForm()">
                    Проверить данные
                </button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
                <input type="hidden" required name="saleNumber" value="${sale.getId()}" id="saleNumber">
            </sec:authorize>
        </c:if>
    </div>

    <script src="js/jquery.js"></script>
    <script>
        function checkAddBidForm() {
            document.getElementById("addBidButton").removeAttribute("disabled");
        }
        function changeStatus() {

            var xmlhttp;
            var idSpan = "status";
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
                    var status = xmlhttp.responseText;
                    if (status == 0) {
                        document.getElementById(idSpan).innerHTML = "Сделка открыта";
                        document.getElementById("addBidButton").setAttribute("disabled", "disabled");
                    }
                    else if (status == 1) {
                        document.getElementById(idSpan).innerHTML = "Сделка заблокирована";
                        document.getElementById("addBidButton").setAttribute("disabled", "disabled");
                    }else {
                        document.getElementById(idSpan).innerHTML = status;
                        document.getElementById("addBidButton").setAttribute("disabled", "disabled");
                    }
                }
            }
            var token = document.getElementById("token");
            var saleNumber = document.getElementById("saleNumber");
            xmlhttp.open("POST", "changeStatusSailOil", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("saleNumber=" + saleNumber.value + "&" + token.name + "=" + token.value);
        }
    </script>
</sec:authorize>
<%@ include file="footer.jsp" %>
