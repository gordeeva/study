var table = document.getElementById('table');
var checkboxesArray = table.getElementsByClassName('userCheckboxes');
var rows = table.getElementsByTagName('tr');

var updateButtonName='<fmt:message key="UPDATE.BUTTON" />';
var addButtonName='<fmt:message key="ADD.BUTTON" />';

var userIdField = document.getElementById('userId');
userIdField.disabled = true;
var userNameField = document.getElementById('userName');
var updateButtonField = document.getElementById('updateButton');

function OnChangeCheckbox(checkbox) {
	var isChecked = checkbox.checked;
	for (var i = 0; i < checkboxesArray.length; i++)
		checkboxesArray[i].checked = false;

	checkbox.checked = isChecked;

	var id = '';
	var name = '';
	var buttonName = '';
	if (isChecked) {
		id = checkbox.parentNode.nextSibling.nextSibling.textContent;
		name = checkbox.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
		buttonName = updateButtonName;		
	} else {
		buttonName = addButtonName;		
	}
	userIdField.disabled = !isChecked;
	userIdField.setAttribute('value', id);
	userNameField.setAttribute('value', name);
	updateButtonField.setAttribute('value', buttonName);
}



