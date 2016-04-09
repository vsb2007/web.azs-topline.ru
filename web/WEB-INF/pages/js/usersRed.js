/**
 * Created by VSB on 09.04.2016.
 */
function checkCompany(post) {
    var xmlhttp;
    document.getElementById("divCompanyAndUnits").innerHTML = "&nbsp;";
    document.getElementById("divCompany").innerHTML = "&nbsp;";
    if (post.value == -1) {
        document.getElementById("divCompany").innerHTML = "&nbsp;";
        xmlhttp = null;
    }
    else {
        document.getElementById("divCompany").innerHTML = "<ul class='zmdi-hc-ul'>" +
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
            if (post.value != -1) {
                document.getElementById("divCompany").innerHTML = xmlhttp.responseText;
            }
        }
    }

    if (post.value != -1) {
        var token = document.getElementById("token");
        xmlhttp.open("POST", "/getCompany", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("postId=" + post.value + "&" + token.name + "=" + token.value);
    }
}
function getCompanyUnits(company) {
    var xmlhttp;
    if (company.value == -1) {
        document.getElementById("divCompanyAndUnits").innerHTML = "&nbsp;";
        xmlhttp = null;
    }
    else {
        document.getElementById("divCompanyAndUnits").innerHTML = "<ul class='zmdi-hc-ul'>" +
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
            if (company.value != -1) {
                document.getElementById("divCompanyAndUnits").innerHTML = xmlhttp.responseText;
            }
        }
    }

    if (company.value != -1) {
        var token = document.getElementById("token");
        xmlhttp.open("POST", "/getCompanyAndUnits", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("companyId=" + company.value + "&" + token.name + "=" + token.value);
    }
}