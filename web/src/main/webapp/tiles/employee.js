var userIdField = document.getElementById('userId');
var userNameField = document.getElementById('userName');
var departmentField = document.getElementById('departmentName');
var radios = document.getElementsByName("radios");
var updateButton = document.getElementById("updateButton");
var addButton = document.getElementById("addButton");
var errorMessageDiv = document.getElementById('errorMessage');
console.log(errorMessageDiv);

radios[0].checked = true;
radios[0].onclick();

var existingRolesCombos = document.getElementsByName('existing_roles');
var deleteRoleButtons = document.getElementsByName('deleteRoleButton');
var newRolesCombos = document.getElementsByName('new_roles');
var addRoleButtons = document.getElementsByName('addRoleButton');

for (var i = 0; i < existingRolesCombos.length; i++) {
    enableButtonIfNeed(existingRolesCombos[i], deleteRoleButtons[i]);
    enableButtonIfNeed(newRolesCombos[i], addRoleButtons[i]);
}

var selectedRadio;
function OnRadioSelected(radio, departamentId) {
    selectedRadio = radio;
    var id = radio.parentNode.nextSibling.nextSibling.textContent;
    var name = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
//    var depName = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
    userIdField.value = id;
    userNameField.value = name;
    departmentField.value = departamentId;

    updateButton.disabled = false;
    addButton.disabled = false;
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

function onUpdateEmployee() {
    var oldName = selectedRadio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
    var newName = document.getElementById('userName').value;
    if (oldName == newName) {
        errorMessageDiv.innerHTML = EMPLOYEE_DUPLICATE_ERROR_MESSAGE;
        isSubmitAllowed = false;
    } else {
        isSubmitAllowed = true;
        errorMessageDiv.innerHTML = "";
    }
}

var isSubmitAllowed = true;
function onManageEmployeeFormSubmit() {
    return isSubmitAllowed;
}


