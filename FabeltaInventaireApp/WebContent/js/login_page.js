document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM login contruit');



});

function getName() {

	var selected = document.getElementById("formLogin");
	var text = selected.options[selected.selectedIndex].text;
	localStorage.setItem("UserNameLoged", text);

}

function saveUser() {

	var uuid = $("#formLogin").find(':selected').attr('data-uuid');
	localStorage.setItem("UuidLogedUser", uuid);

}