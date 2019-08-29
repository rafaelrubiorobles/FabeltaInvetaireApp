document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM recherche construit');

});

//fonction pour setter l'information a envoyer par le url
function setDataInModalDeplacement(zone) {

	//get la zone et la quantite 
	var zoneCode = zone.getAttribute("data-zonedeplacement");
	var zoneQty = zone.getAttribute("data-qtydeplacement");

	//set les data attributes dans le modal
	document.getElementById('zoneCodeInputDeplacement').value = zoneCode;
	
	//set max dans l'input quantite
	document.getElementById('inputQtyAEnvoyerDeplacement').max = zoneQty;

	//set dans linformation modal

	document.getElementById('quantiteZoneInModal').textContent = zoneQty;

	//put user data in the url
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputUserId = document.getElementById('userData');
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
