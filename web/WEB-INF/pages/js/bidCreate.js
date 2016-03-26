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
                getTrailerOnGetCarSection(car);
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

function getTrailerOnGetCarSection(car) {
    var xmlhttp1;
    if (car.value == -1) {
        document.getElementById("divTrailerId").innerHTML = "&nbsp;";
        xmlhttp1.close();
        xmlhttp1 = null;
    }
    else {
        document.getElementById("divTrailerId").innerHTML = "<ul class='zmdi-hc-ul'>" +
            "<li><i class='zmdi-hc-li zmdi zmdi-refresh zmdi-hc-spin'></i>loading...</li>" +
            "</ul>";
    }
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp1 = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp1 = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp1.onreadystatechange = function () {
        if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
            if (car.value != -1) {
                document.getElementById("divTrailerId").innerHTML = xmlhttp1.responseText;
            }
        }
    }
    if (car.value != -1) {
        var token = document.getElementById("token");
        xmlhttp1.open("POST", "/getTrailers", true);
        xmlhttp1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp1.send("idCar=" + car.value + "&" + token.name + "=" + token.value);
    }
}

function onTrailerSelect(trailer) {
    var xmlhttp;
    if (trailer.value == -1) {
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
                getTrailerOnGetCarSection(car);
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
