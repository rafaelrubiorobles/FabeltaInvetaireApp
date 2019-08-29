document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM recherche construit');

	//call functiton pour disabled button in recherche produit/zone
	document.getElementById('inputRechercher').addEventListener("keyup", disableButtonSearchProduit);


	//function buttons ajouter enlever
	var minusButton = document.getElementById('minusminus');
	var plusButton = document.getElementById('plusplus');
	var inputField = document.getElementById('inputQtyAAjuster');

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

//function disabled search button in product/zone
function disableButtonSearchProduit() {

	var inputSearchProduit = document.getElementById('inputRechercher').value;
	var buttonSearchProduit = document.getElementById('boutonRechercher');

	if (inputSearchProduit.length > 0) {
		buttonSearchProduit.disabled = false;
	} else if (inputSearchProduit.length <= 0) {
		buttonSearchProduit.disabled = true;
	}
}

//function pour remplir le modal dans recherche produit ajuster
function getQuantiteEtZone(zone) {

	//get les data attributs code et zone
	var zoneCodeAAjuster = zone.getAttribute("data-zoneajus");
	var zoneQtyAAjuster = zone.getAttribute("data-qtyajus");

	//set les data recu des attributs et le placer dans le modal
	document.getElementById('codeZoneAAjuster').value = zoneCodeAAjuster;
	document.getElementById('inputQtyAAjuster').value = 1;


	document.getElementById('quantiteZoneInModalAjuster').textContent = zoneQtyAAjuster;


	//set the uuid in the url
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputUserId = document.getElementById('dataIdAjustement');
	inputUserId.setAttribute("name", "idUser");
	inputUserId.setAttribute("value", userId);
}



function tableRecherce(){
	//variables 
	var input , filter, table, tr, td, i, txtValue;

	input = document.getElementById('myInput');

	filter = input.value.toUpperCase();

	table = document.getElementById('myTable');

	tr = table.getElementsByTagName('tr');

	//boucle for pour aller dans tout la table et cacher eux quon a pas dans notre recherche
	for(i=0; i< tr.length; i++){
		td = tr[i].getElementsByTagName('td')[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if(txtValue.toUpperCase().indexOf(filter) > -1){
				tr[i].style.display = "";
			}else{
				tr[i].style.display = "none";
			}
		}
	}
}




