var userIdField = document.getElementById('userId');
var userNameField = document.getElementById('userName');
var departmentField = document.getElementById('departmentName');
var radios = document.getElementsByName("radios");

radios[0].checked = true;
radios[0].onchange();

function OnRadioSelected(radio, departamentId) {
    var id = radio.parentNode.nextSibling.nextSibling.textContent;
    var name = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
//    var depName = radio.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.textContent;
    userIdField.setAttribute('value', id);
    userNameField.setAttribute('value', name);

    departmentField.value = departamentId;
}



