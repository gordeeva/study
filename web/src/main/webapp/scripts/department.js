var departmentIdField = document.getElementById('departmentId');
var departmentNameField = document.getElementById('departmentName');
var radios = document.getElementsByName("radios");
var updateButton = document.getElementById("updateButton");
var addButton = document.getElementById("addButton");

radios[0].checked = true;
radios[0].onclick();

var employees = document.getElementsByName('employees');
var deleteDepartmentLinks = document.getElementsByName('deleteDepartmentLinks');
for (var i = 0; i < employees.length; i++) {
    if (employees[i].value) {
        deleteDepartmentLinks[i].href = 'javascript:void(0)';
    }
}

function OnRadioSelected(radio) {
    var id = radio.parentNode.nextSibling.nextSibling.textContent;
    var name = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
    departmentIdField.value = id;
    departmentNameField.value = name;
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

