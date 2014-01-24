var departmentIdField = document.getElementById('departmentId');
var departmentNameField = document.getElementById('departmentName');
var radios = document.getElementsByName("radios");

radios[0].checked = true;
radios[0].onchange();

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
    departmentIdField.setAttribute('value', id);
    departmentNameField.setAttribute('value', name);
}


