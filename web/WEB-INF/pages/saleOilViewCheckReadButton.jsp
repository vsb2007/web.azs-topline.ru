<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!sale.isRead()}">
    <button class="button raised bg-blue-500 color-white" type="button" onclick="checkReadForm()"
            id="readButton">
        Уведомить о прочтении
    </button>
    <br>
    <span id="spanReadButton"></span>
</c:if>
<script>
    function checkReadForm() {
        document.getElementById("addBidButton").setAttribute("disabled", "disabled");
        var xmlhttp;
        var idSpan = "spanReadButton";
        document.getElementById(idSpan).innerHTML = "<ul class='zmdi-hc-ul'>" +
                "<li><i class='zmdi-hc-li zmdi zmdi-refresh zmdi-hc-spin'></i>loading111...</li>" +
                "</ul>";
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                var response = xmlhttp.responseText;
                if (response == 1) {
                    document.getElementById(idSpan).innerHTML = "Уведомление прошло";
                    document.getElementById("readButton").setAttribute("class", "button raised bg-green-500 color-white");
                    document.getElementById("readButton").setAttribute("disabled", "disabled");
                }
                else {
                    document.getElementById(idSpan).innerHTML = response;
                }
            }
        }
        var token = document.getElementById("token");
        var idSale = document.getElementById("saleNumber");
        xmlhttp.open("POST", "checkReadSaleForm", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("idSale=" + idSale.value + "&" + token.name + "=" + token.value);
    }
</script>
