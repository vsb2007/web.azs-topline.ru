function onVolumeChange(idVol, idMass, idP) {
    var vol = document.getElementById(idVol);
    var p = document.getElementById(idP);
    var mass = document.getElementById(idMass);
    if (p == null || p.value == 0) return;
    mass.value = (vol.value * p.value / 1000).toFixed(4);
}

function onPChange(idVol, idMass, idP) {
    var vol = document.getElementById(idVol);
    var p = document.getElementById(idP);
    var mass = document.getElementById(idMass);
    if (p == null) return;
    if (p.value == 0) return;
    if (vol == null || vol.value == 0) return;
    mass.value = (vol.value * p.value / 1000).toFixed(4);
}

function onMassChange(idVol, idMass, idP) {
    var vol = document.getElementById(idVol);
    var p = document.getElementById(idP);
    var mass = document.getElementById(idMass);
    if (p == null) return;
    if (p.value == 0) return;
    vol.value = (mass.value * 1000 / p.value).toFixed(2);
}
