var table = document.getElementById('table');
var checkboxesArray = table.getElementsByClassName('userCheckboxes');
var rows = table.getElementsByTagName('tr');

var userIdField = document.getElementById('userId');
userIdField.disabled = true;
var userNameField = document.getElementById('userName');
var updateButtonField = document.getElementById('updateButton');

//var deleteRoleComboField = document.getElementById('deleteRoleComboId');


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
		buttonName = 'Update';		
	} else {
		buttonName = 'Add';		
	}
	userIdField.disabled = !isChecked;
	userIdField.setAttribute('value', id);
	userNameField.setAttribute('value', name);
	updateButtonField.setAttribute('value', buttonName);
}


