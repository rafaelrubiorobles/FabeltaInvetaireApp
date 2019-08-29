document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM recherche construit');

	//annuler evenement sur linput quantite dans le model split
	document.getElementById('quantiteProduitModalTout').addEventListener("keypress", function (evt) {
		evt.preventDefault();
	})


	document.getElementById('boutonFermerReception').onclick = function () { fermer() }

	//envoi de user name du modal fermer
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputZoneId = document.getElementById("userNameLoged");
	inputZoneId.setAttribute('name', "IdUser")
	inputZoneId.setAttribute('value', userId);

});
//function dans le bouton fermer
function fermer() {

	var quantiteEnReceptionInput = document.getElementById('inputQuantite').value;

	if (quantiteEnReceptionInput == 0) {
		window.location.href = 'main.jsp';
	} else {
		$('#modalConfirmationEnvoiReceptionSplit').modal('show');

	}

	//remplisage modal
	//remplissage le code du produit
	var codeProd = document.getElementById('codeProduit').textContent;
	document.getElementById('prodCodeEnModal').value = codeProd;
	//remplissage quantite
	var quantityProd = document.getElementById('inputQuantite').value;
	document.getElementById('prodQtyEnModal').value = quantityProd;


}


//function pour set le code de zone dans le modal tout
function getDataZone(zone) {

	var zoneNameData = zone.getAttribute("data-zone");
	document.getElementById('zoneProduitModalTout').value = zoneNameData;

}

//function pour set le code de la zone dans le modal split
function getDataZoneSplit(zone) {

	var zoneNameDataSplit = zone.getAttribute("data-zone");
	document.getElementById('zonePorduitModalSplit').value = zoneNameDataSplit;
}

//function pour set la quantite tout modal total
function getQuantite() {

	var quantiteInput = document.getElementById('inputQuantite').value;
	document.getElementById('quantiteProduitModalTout').value = quantiteInput;
	document.getElementById('spanQteTotalSplit').value = quantiteInput;
}

// function pour set la quantite tout modal split
function getQuantiteSplit() {

	var quantiteInput = document.getElementById('inputQuantite').value;
	document.getElementById('spanQteTotalSplit').value = quantiteInput;

}

//function set data-zone in scannez votre zone tout
function setDataZone() {

	//get les 2 elements a ajouter data-zone
	var boutonTout = document.getElementById('boutonToutScannezVotreZone');
	var inputValue = document.getElementById('inputScannezZone').value;

	//set comme data-zone le value du input scannez votre zone
	boutonTout.setAttribute('data-zone', inputValue);

	//get la data-zone et le setter dans le modal
	var newZone = boutonTout.getAttribute("data-zone");
	document.getElementById('zoneProduitModalTout').value = newZone;

}

//function set data-zone in scannez votre zone split
function setDataZoneSplit() {

	//get les 2 elements a ajouter data zone
	var boutonSplit = document.getElementById('boutonSplitScannezVotreCode');
	var inputValue = document.getElementById('inputScannezZone').value;

	//set comme data-zone le value du input scannez votre zone
	boutonSplit.setAttribute('data-zone', inputValue);

	//get la data-zone et le setter dans le modal
	var newZone = boutonSplit.getAttribute("data-zone");
	document.getElementById('zonePorduitModalSplit').value = newZone

}


//------------------------------ tout-------
//set attribute pour differencer entre une zone deja dans la liste ou une zone ajoute manuellement tout
function setNameParams() {

	var inputZone = document.getElementById('boutonX');
	inputZone.setAttribute('name', "Scanned")
	inputZone.setAttribute('value', "OUI");

	//----set le user id in the url
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputZoneId = document.getElementById("userIdInputX");
	inputZoneId.setAttribute('name', "IdUser")
	inputZoneId.setAttribute('value', userId);

	//set if is from reception
	var isFromReception = window.localStorage.getItem('fromReception');
	var inputIsFromRececption = document.getElementById('fromReceptionNon');
	inputIsFromRececption.setAttribute('name', "isFromRec");
	inputIsFromRececption.setAttribute('value', isFromReception);
}

//set attribute pour differencer les envoi de donnes avec la zone tout
function setNameParamsTout() {

	var inputZone = document.getElementById('boutonX');
	inputZone.setAttribute('name', "Scanned")
	inputZone.setAttribute('value', "NON");

	//----set le user id in the url
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputZoneId = document.getElementById("userIdInputX");
	inputZoneId.setAttribute('name', "IdUser")
	inputZoneId.setAttribute('value', userId);

	//set if is from reception
	var isFromReception = window.localStorage.getItem('fromReception');
	var inputIsFromRececption = document.getElementById('fromReceptionNon');
	inputIsFromRececption.setAttribute('name', "isFromRec");
	inputIsFromRececption.setAttribute('value', isFromReception);
}

//------------------------------ tout-------


//------------------------------ split-------
function setNameParamnsSplitScanned() {

	var inputZone = document.getElementById('boutonSplitX');
	inputZone.setAttribute('name', "Scanned")
	inputZone.setAttribute('value', "OUI");

	//----set le user id in the url
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputZoneId = document.getElementById('userIdInput');
	inputZoneId.setAttribute('name', "IdUser")
	inputZoneId.setAttribute('value', userId);

	//set if is from reception
	var isFromReception = window.localStorage.getItem('fromReception');
	var inputIsFromRececption = document.getElementById('fromReceptionNonSplit');
	inputIsFromRececption.setAttribute('name', "isFromRec");
	inputIsFromRececption.setAttribute('value', isFromReception);

}

//set attribute pour differencer les envois de donnes avez la zone split
function setNameParamnsSplit() {


	var inputZone = document.getElementById('boutonSplitX');
	inputZone.setAttribute('name', "Scanned")
	inputZone.setAttribute('value', "NON");

	//----set le user id in the url
	var userId = window.localStorage.getItem('UuidLogedUser');
	var inputZoneId = document.getElementById('userIdInput');
	inputZoneId.setAttribute('name', "IdUser")
	inputZoneId.setAttribute('value', userId);

	//set if is from reception
	var isFromReception = window.localStorage.getItem('fromReception');
	var inputIsFromRececption = document.getElementById('fromReceptionNonSplit');
	inputIsFromRececption.setAttribute('name', "isFromRec");
	inputIsFromRececption.setAttribute('value', isFromReception);

}
//------------------------------ split-------

//set attribute max = a inputQuantite quand on click sur split 
function setMaxAttributreSplit() {
	var inputQty = document.getElementById('inputQuantite').value;
	document.getElementById('quantiteProduitModalSplit').max = inputQty

}















