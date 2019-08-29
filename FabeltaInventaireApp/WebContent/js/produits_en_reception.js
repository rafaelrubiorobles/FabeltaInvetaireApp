//function pour envoyer la data au modal
function dataInModal(zone) {


	//get les data attributs code et zone
	var zoneCodeAEnvoyer = zone.getAttribute("data-prod");
	var zoneQtyAEnvoyer = zone.getAttribute("data-qty");

	//set les data recu des attributs et le placer dans le modal
	document.getElementById('inputIdProduit').value = zoneCodeAEnvoyer;
	document.getElementById('inputQuantiteProduit').value = zoneQtyAEnvoyer;
	document.getElementById('inputQuantiteProduit').max = zoneQtyAEnvoyer;

	localStorage.setItem("zone", zoneCodeAEnvoyer);

	document.getElementById('qteDansReception').textContent = zoneQtyAEnvoyer;
}

//recetion local storage
function setFromReception() {
	localStorage.setItem("fromReception", "oui");

	var quantiteInput = document.getElementById('inputQuantiteProduit').value;

	localStorage.setItem("quantite", quantiteInput);

	var zone = localStorage.getItem("zone");
	var quantite = localStorage.getItem("quantite");

	document.getElementById('dataAEnvoyer').value = zone + " " + quantite;

	localStorage.removeItem("zone");
	localStorage.removeItem("quantite");
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