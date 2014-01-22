var table = document.getElementById('table');
var checkboxesArray = table.getElementsByClassName('userCheckboxes');
var rows = table.getElementsByTagName('tr');

var userIdField = document.getElementById('userId');
userIdField.disabled = true;
var userNameField = document.getElementById('userName');
var departmentField = document.getElementById('departmentName');
var updateButtonField = document.getElementById('updateButton');

function OnChangeCheckbox(checkbox) {
    var isChecked = checkbox.checked;
    for (var i = 0; i < checkboxesArray.length; i++)
        checkboxesArray[i].checked = false;

    checkbox.checked = isChecked;

    var id = '';
    var name = '';
    var depName = '';
    var buttonName = '';
    if (isChecked) {
        id = checkbox.parentNode.nextSibling.nextSibling.textContent;
        name = checkbox.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
        depName = checkbox.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
        buttonName = updateButtonName;
    } else {
        buttonName = addButtonName;
    }
    userIdField.disabled = !isChecked;
    userIdField.setAttribute('value', id);
    userNameField.setAttribute('value', name);
    departmentField.value = depName;
    updateButtonField.setAttribute('value', buttonName);
}



