<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_LIST')">
    <div class="section">
        <sec:authorize access="hasRole('ROLE_BID_CREATE')">
            <c:if test="${title != null}">
                <h3> ${title}</h3>
            </c:if>
            <form action="bidcreateform" method="post">
                <select class="dropdown-menu" id="oilStorage" name="oilStorage" onchange="">
                    <option value="-1">Пункт загрузки</option>
                    <c:forEach items="${oilStorageList}" var="oilStorage">
                        <option value="${oilStorage.getIdOilStorage()}">${oilStorage.getOilStorageName()}</option>
                    </c:forEach>
                </select><br>
                <select class="dropdown-menu" id="driver" name="driver" onchange="" >
                    <option value="-1">Водитель</option>
                    <c:forEach items="${driversList}" var="driver">
                        <option value="${driver.getIdDriver()}">${driver.getDriverFio()}</option>
                    </c:forEach>
                </select><br>
                <select class="dropdown-menu" id="driverCanUpdateIn" name="driverCanUpdateIn" onchange="">
                    <option value="0">Водитель не может принимать топливо</option>
                    <c:if test="${isEntrance!=null}">
                        <c:if test="${isEntrance}">
                            <option value="1" selected>Водитель может принимать топливо</option>
                        </c:if>
                        <c:if test="${!isEntrance}">
                            <option value="1">Водитель может принимать топливо</option>
                        </c:if>
                    </c:if>

                </select><br>
                    <%--
                    <select class="dropdown-menu" id="driverCanUpdateOut" name="driverCanUpdateOut" onchange="">
                        <option value="0">Водитель не может сливать топливо</option>
                        <option value="1">Водитель может сливать топливо</option>
                    </select><br>
                    --%>
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
                <button class="button raised bg-blue-500 color-white" disabled="disabled" id="addBidButton">Добавить
                    заявку
                </button>
                <button class="button raised bg-blue-500 color-white" type="button" onclick="checkAddBidForm()">
                    Проверить
                    данные
                </button>
                <c:if test="${isEntrance!=null}">
                    <c:if test="${isEntrance}">
                        <input type="hidden" name="isEntrance" value="1"/>
                    </c:if>
                    <c:if test="${!isEntrance}">
                        <input type="hidden" name="isEntrance" value="0"/>
                    </c:if>
                </c:if>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
            </form>
        </sec:authorize>
    </div>

    <script src="js/bidCreate01.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script>
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
        function getSaleBidId(idSection) {
            var idSpan = "span_" + idSection + "_OrgId";
            var orgId = document.getElementById(idSection + "_OrgId").value;
            if (orgId == -1) {
                document.getElementById(idSpan).innerHTML = "";
                return;
            }
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
            xmlhttp.open("POST", "getSaleBidsIdByOrg", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("orgId=" + orgId + "&idSection=" + idSection + "&" + token.name + "=" + token.value);

        }
    </script>

</sec:authorize>
<%@ include file="footer.jsp" %>
