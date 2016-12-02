<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_SALE_LIST')">
    <div class="section">
        <sec:authorize access="hasRole('ROLE_SALE_RED')">
            <h3>Продажа Топлива</h3>
            <form action="javascript:void(null);" method="post" id="saleUpdateForm" onsubmit="sendFormForSaleRed()">
                <div class="grid-list">
                    <div class="tile">
                        <select class="dropdown-menu" id="idUnit" name="idUnit" onchange="">
                            <c:set var="selected" value=""/>
                            <c:if test="${sale.getCompanyUnit() == null}">
                                <c:set var="selected" value="selected"/>
                            </c:if>
                            <option value="-1" ${selected}>Отдел Логистики</option>
                            <c:forEach items="${oilStorageList}" var="oilStorage">
                                <c:set var="selected" value=""/>
                                <c:if test="${sale.getCompanyUnit() != null && sale.getCompanyUnit().getIdCompanyUnit()==oilStorage.getCompanyUnit().getIdCompanyUnit()}">
                                    <c:set var="selected" value="selected"/>
                                </c:if>
                                <option value="${oilStorage.getCompanyUnit().getIdCompanyUnit()}"  ${selected}>${oilStorage.getCompanyUnit().getCompanyUnitName()}</option>
                            </c:forEach>
                        </select>
                        <div>
                            <span class="secondary-text">Ответственный за отгрузку</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type=text class="text-input border-blue-500"
                               id="OrgId_text" onChange="getOrganization(this,'_OrgId_span')"
                               value="${sale.getOrganization().getOrganizationName()}">
                        <div>
                            <span class="secondary-text">Фильтр организаций</span>
                        </div>
                    </div>
                    <div class="tile">
                        <span id="_OrgId_span">
                            <c:if test="${sale.getOrganization()!=null}">
                                <select name="OrgId" id="OrgId" class="dropdown-menu">
                                    <option value="${sale.getOrganization().getIdOrganization()}">${sale.getOrganization().getOrganizationName()}</option>
                                </select>
                            </c:if>
                            <c:if test="${sale.getOrganization()==null}">
                            <input type="text" class="text-input border-blue-500" readonly
                                   placeholder="Установите фильтр" required>
                            </c:if>
                        </span>
                        <div>
                            <span class="secondary-text">Грузополучатель</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <span id="_orgDogId_span">
                            <select class="dropdown-menu" name="orgDogId" id="orgDogId" style="width: 150px"
                                    required onchange="getSumForDogForOrg()">
                                <option></option>
                                <c:if test="${sale.getOrganization()!=null}">
                                    <c:if test="${sale.getOrganization().getOrganizationPacts()!=null}">
                                        <c:forEach items="${sale.getOrganization().getOrganizationPacts()}" var="pact" >
                                            <option value="${pact.getId()}">${pact.getPactName()}</option>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                        </select>
                        </span>
                        <div>
                            <span class="secondary-text">Договор грузополучателя</span>
                        </div>
                    </div>
                    <br>

                    <div class="tile">
                        <input type="text" class="text-input border-green-500"
                               value="0" placeholder="На счете" name="openSum" id="openSum" required readonly>
                        <div>
                            <span class="secondary-text">Доступная сумма на счете</span>
                        </div>
                    </div>
                    <br>

                    <div class="tile">
                        <input type="text" class="text-input border-green-500"
                               value="${sale.getFio()}" placeholder="Ф.И.О." name="fio" required>
                        <div>
                            <span class="secondary-text">Ф.И.О.</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="text" class="text-input border-green-500"
                               value="${sale.getCarNumber()}" placeholder="Номер Машины" id="carNumber" name="carNumber"
                               required>
                        <div>
                            <span class="secondary-text">Номер Машины</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <select class="dropdown-menu" id="oilTypeId" name="oilTypeId" onchange="">
                            <option value="-1">Выбрать Топливо</option>
                            <c:forEach items="${oilTypeList}" var="oilType">
                                <c:set var="selected" value=""/>
                                <c:if test="${sale.getOilType().getId_oilType() == oilType.getId_oilType()}">
                                    <c:set var="selected" value="selected"/>
                                </c:if>
                                <option value="${oilType.getId_oilType()}" ${selected}>${oilType.getOilTypeName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br>
                    <div class="tile">
                        <select id="lt" name="lt" class="dropdown-menu" required>
                            <option></option>
                            <c:set var="selected" value=""/>
                            <c:if test="${sale.getLt() ==0}">
                                <c:set var="selected" value="selected"/>
                            </c:if>
                            <option value="0" ${selected}>Литры</option>
                            <c:set var="selected" value=""/>
                            <c:if test="${sale.getLt() ==1}">
                                <c:set var="selected" value="selected"/>
                            </c:if>
                            <option value="1" ${selected}>Тонны</option>
                        </select>
                        <div>
                            <span class="secondary-text">Единицы измерения</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getCol()}" placeholder="Количество единиц" id="colLiters" name="colLiters"
                               required onchange="getSum()" step="any">
                        <div>
                            <span class="secondary-text">Количество единиц</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getPriceOil()}" placeholder="Цена за единицу" id="priceLiters"
                               name="priceLiters"
                               required onchange="getSum()" step="any">
                        <div>
                            <span class="secondary-text">Цена за единицу</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getPriceShipping()}" placeholder="Цена доставки" id="priceShipping"
                               name="priceShipping"
                               required onchange="getSum()" step="any">
                        <div>
                            <span class="secondary-text">Цена доставки</span>
                        </div>
                    </div>
                    <br>
                    <div class="tile">
                        <input type="number" class="text-input border-green-500"
                               value="${sale.getSum()}" placeholder="Сумма" id="sum" name="sum" required readonly
                               step="any">
                        <div>
                            <span class="secondary-text">Сумма</span>
                        </div>
                    </div>
                    <div class="tile">
                        <input type="text" class="text-input border-green-500"
                               value="${sale.getDesiredDate()}" placeholder="Дата доставки" id="desiredDate" name="desiredDate" required readonly>
                        <div>
                            <span class="secondary-text">Дата доставки</span>
                        </div>

                    </div>
                    <br>
                    <div class="tile">
                      <textarea class="text-area border-green-500"  placeholder="Коментарии к заявке"
                                id="comments" name="comments">${sale.getComments()}</textarea>
                        <div>
                            <span class="secondary-text">Коментарии</span>
                        </div>
                    </div>
                </div>
                <br>
                <button class="button raised bg-blue-500 color-white" disabled="disabled" id="addBidButton">
                    Изменить заявку
                </button>
                <button class="button raised bg-blue-500 color-white" type="button" onclick="checkAddBidForm()">
                    Проверить данные
                </button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
                <input type="hidden" required name="saleNumber" value="${sale.getId()}" id="saleNumber">
                <input type="hidden" name="totalSum" id="totalSum" value="0">
            </form>
            <div id="results"></div>
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
                    getDogForOrg();
                }
            }
            var token = document.getElementById("token");
            xmlhttp.open("POST", "orgGetListByFilter", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("filter=" + inText.value + "&" + token.name + "=" + token.value + "&idSelect=" + inText.id);
        }
        function checkAddBidForm() {
            document.getElementById("results").innerHTML = "";
            var openSum = document.getElementById("openSum").value;
            var sum = document.getElementById("sum").value;
            if (sum == NaN || sum <=0) return;
            if (openSum - sum < 0){
                document.getElementById("addBidButton").innerHTML = "Отправить на проверку";
            }
            else{document.getElementById("addBidButton").innerHTML = "Изменить заявку";
            }
            document.getElementById("totalSum").value = openSum - sum;
            document.getElementById("addBidButton").removeAttribute("disabled");
        }
        function getDogForOrg() {
            document.getElementById("addBidButton").setAttribute("disabled", "disabled");
            document.getElementById("openSum").value = 0;
            var orgId = document.getElementById("OrgId");
            var xmlhttp;
            var idSpan = "_orgDogId_span";
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
                    //getSumForDogForOrg();
                }
            }
            var token = document.getElementById("token");
            xmlhttp.open("POST", "orgGetDog", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("orgId=" + orgId.value + "&" + token.name + "=" + token.value);
        }

        function getSumForDogForOrg() {
            document.getElementById("addBidButton").setAttribute("disabled", "disabled");
            var orgDogId = document.getElementById("orgDogId");
            var xmlhttp;
            var idSpan = "openSum";
            document.getElementById(idSpan).value = "loading...";
            //document.getElementById(idSpan).value = orgDogId.value;
            if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            }
            else {// code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById(idSpan).value = xmlhttp.responseText;
                }
            }
            var token = document.getElementById("token");
            xmlhttp.open("POST", "getOrgDogOpenSum", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("orgDogId=" + orgDogId.value + "&" + token.name + "=" + token.value);
        }

        function sendFormForSaleRed() {
            var msg = $('#saleUpdateForm').serialize();
            $.ajax({
                type: 'POST',
                url: 'saleRedUpdate',
                data: msg,
                success: function (data) {
                    //$('.results').html(data);
                    document.getElementById("results").innerHTML = data;
                },
                error: function (xhr, str) {
                    //$('.results').html('Возникла ошибка: ' + xhr.responseCode);
                    document.getElementById("results").innerHTML = "Возникла ошибка: " + xhr.responseCode;
                }
            });

        }
    </script>
    <script>
        $(function () {
            $("#desiredDate").datepicker({
                defaultDate: "today",
                changeMonth: false,
                numberOfMonths: 1,
                dateFormat: 'yy-mm-dd', // See format options on parseDate
                firstDay: 1,
            });
        });
    </script>
</sec:authorize>
<%@ include file="footer.jsp" %>
