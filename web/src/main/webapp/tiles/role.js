var roleIdField = document.getElementById('roleId');
var roleNameField = document.getElementById('roleName');
var radios = document.getElementsByName("radios");
var updateButton = document.getElementById("updateButton");
var addButton = document.getElementById("addButton");

radios[0].checked = true;
radios[0].onclick();

function OnRadioSelected(radio) {
    var id = radio.parentNode.nextSibling.nextSibling.textContent;
    var name = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
    roleIdField.value = id;
    roleNameField.value = name;
    updateButton.disabled = false;
    addButton.disabled = false;
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


