document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM recherche construit');

});

function getInfoFromProduct(zone){

	//get les attributes id et quantite
	var zoneId = zone.getAttribute("data-code");
	var zoneQty = zone.getAttribute("data-qty");

	//set les informations dans le modal
	document.getElementById("inputZoneCode").value = zoneId;
	document.getElementById("qtyInZone").textContent = zoneQty;

	//set le maximum du input
	document.getElementById("inputQtyAScrap").max = zoneQty;

		//put user data in the url
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputUserId = document.getElementById('idUserName');
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