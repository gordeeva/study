var roleIdField = document.getElementById('roleId');
var roleNameField = document.getElementById('roleName');
var radios = document.getElementsByName("radios");

radios[0].checked = true;
radios[0].onchange();

function OnRadioSelected(radio) {
    var id = radio.parentNode.nextSibling.nextSibling.textContent;
    var name = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
    roleIdField.setAttribute('value', id);
    roleNameField.setAttribute('value', name);
}


