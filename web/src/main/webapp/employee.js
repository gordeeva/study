var userIdField = document.getElementById('userId');
var userNameField = document.getElementById('userName');
var departmentField = document.getElementById('departmentName');
var radios = document.getElementsByName("radios");
var updateButton = document.getElementById("updateButton");
var addButton = document.getElementById("addButton");

radios[0].checked = true;
radios[0].onchange();

var existingRolesCombos = document.getElementsByName('existing_roles');
var deleteRoleButtons = document.getElementsByName('deleteRoleButton');
var newRolesCombos = document.getElementsByName('new_roles');
var addRoleButtons = document.getElementsByName('addRoleButton');

for (var i = 0; i < existingRolesCombos.length; i++) {
    enableButtonIfNeed(existingRolesCombos[i], deleteRoleButtons[i]);
    enableButtonIfNeed(newRolesCombos[i], addRoleButtons[i]);
}

function OnRadioSelected(radio, departamentId) {
    var id = radio.parentNode.nextSibling.nextSibling.textContent;
    var name = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
//    var depName = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
    userIdField.value = id;
    userNameField.value = name;

    departmentField.value = departamentId;
}

function enableButtonIfNeed(combobox, button) {
    if (!combobox.value) {
        button.disabled = true;
    } else {
        button.disabled = false;
    }
}

function disableControlsIfNeed(textField) {
    if (!textField.value) {
        updateButton.disabled = true;
        addButton.disabled = true;
    } else {
        updateButton.disabled = false;
        addButton.disabled = false;
    }
}


