function onVolumeChange(idVol, idMass, idP) {
    document.getElementById("submitButton").setAttribute("disabled","disabled");
    var vol = document.getElementById(idVol);
    var p = document.getElementById(idP);
    var mass = document.getElementById(idMass);
    if (p == null || p.value == 0) return;
    mass.value = (vol.value * p.value / 1000).toFixed(3);
}

function onPChange(idVol, idMass, idP) {
    document.getElementById("submitButton").setAttribute("disabled","disabled");
    var vol = document.getElementById(idVol);
    var p = document.getElementById(idP);
    var mass = document.getElementById(idMass);
    var control = document.getElementById("control");
    if (p == null) return;
    if (p.value == 0 || p.value == NaN) {
        mass.value = NaN;
        return;
    }
    if (vol == null || vol.value == 0) return;
    mass.value = (vol.value * p.value / 1000).toFixed(3);
}

function onMassChange(idVol, idMass, idP) {
    document.getElementById("submitButton").setAttribute("disabled","disabled");
    var vol = document.getElementById(idVol);
    var p = document.getElementById(idP);
    var mass = document.getElementById(idMass);
    if (p == null) return;
    if (p.value == 0 || p.value == NaN) {
        return;
    }
    vol.value = (mass.value * 1000 / p.value).toFixed(2);
}

function checkForm() {
    document.getElementById("submitButton").setAttribute("disabled","disabled");
    var msg = $('#bidUpdateForm').serialize();
    $.ajax({
        type: 'POST',
        url: 'bidCheckUpdate',
        data: msg,
        success: function (data) {
            //$('.results').html(data);
            //document.getElementById("control").innerHTML = data;
            if (data == 1) {
                document.getElementById("control").innerHTML = "Можно отправлять";
                document.getElementById("submitButton").removeAttribute("disabled");
            }
            if (data != 1) {
                document.getElementById("control").innerHTML = "Ошибка: " + data;
                document.getElementById("submitButton").setAttribute("disabled","disabled");
            }
        },
        error: function (xhr, str) {
            //$('.results').html('Возникла ошибка: ' + xhr.responseCode);
            document.getElementById("control").innerHTML = "Возникла ошибка: " + xhr.responseCode;
            document.getElementById("submitButton").setAttribute("disabled","disabled");
        }
    });
}