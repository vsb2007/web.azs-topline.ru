function onCarSelect(car) {
    var xmlhttp;
    if (car.value == -1) {
        document.getElementById("divCarSectionId").innerHTML = "&nbsp;";
        xmlhttp.close();
        xmlhttp = null;
    }
    else {
        document.getElementById("divCarSectionId").innerHTML = "<ul class='zmdi-hc-ul'>" +
            "<li><i class='zmdi-hc-li zmdi zmdi-refresh zmdi-hc-spin'></i>loading...</li>" +
            "</ul>";
    }
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            if (car.value != -1) {
                document.getElementById("divCarSectionId").innerHTML = xmlhttp.responseText;
            }
        }
    }

    if (car.value != -1) {
        var token = document.getElementById("token");
        xmlhttp.open("POST", "/getCarSections", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("idCar=" + car.value + "&" + token.name + "=" + token.value);
    }
}
