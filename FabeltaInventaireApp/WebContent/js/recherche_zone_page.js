document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM recherche construit');

	//function buttons ajouter enlever
	var minusButton = document.getElementById('minus');
	var plusButton = document.getElementById('plus');
	var inputField = document.getElementById('quantiteAModifier');

	minusButton.addEventListener('click', event => {
		event.preventDefault();
		const currentValue = Number(inputField.value) || 0;
		inputField.value = currentValue - 1;
	});

	plusButton.addEventListener('click', event => {
		event.preventDefault();
		const currentValue = Number(inputField.value) || 0;
		inputField.value = currentValue + 1;
	});

});

function sendInformationFormZone(zone) {

	//get attributs
	var prodCode = zone.getAttribute("data-code");

	var prodCodeToSend = document.getElementById('prodCode');
	prodCodeToSend.setAttribute("name", "produitCode");
	prodCodeToSend.setAttribute("value", prodCode);

	var zoneCode = document.getElementById('zoneCodeInCode').innerHTML;

	var zoneIci = document.getElementById('zoneCode');
	zoneIci.setAttribute("name", "zoneCode1");
	zoneIci.setAttribute("value", zoneCode);

	$("#buttonSubmitComment").click();
}


//function pour passer information au modal de modification
function informationToModify(product) {

	//get attributes from the line
	var productId = product.getAttribute("data-code");
	var productQuantity = product.getAttribute("data-qty");

	document.getElementById('quantiteEnZoneProd').textContent = productQuantity;
	document.getElementById('inputIdProduit').value = productId;

	//set the uuid a la requete
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputUserId = document.getElementById('userIdInformation');
	inputUserId.setAttribute("name", "idUser");
	inputUserId.setAttribute("value",userId);
}

//function pour la recherche dans la table des produits dans la zone
function tableRecherce() {
	//variables 
	var input, filter, table, tr, td, i, txtValue;

	input = document.getElementById('myInput');

	filter = input.value.toUpperCase();

	table = document.getElementById('myTable');

	tr = table.getElementsByTagName('tr');

	//boucle for pour aller dans tout la table et cacher eux quon a pas dans notre recherche
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName('td')[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}