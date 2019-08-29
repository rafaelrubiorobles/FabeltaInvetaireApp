/**
 * 
 */
document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM contruit');

	//call function pour afficher la date
	getDate();
	

	//call fucntion pour afficher le user
	getUserName();
	

});


//function pour afficher la date
function getDate() {
	var dateToday = new Date();
	document.getElementById("dateToday").innerHTML = dateToday.toDateString();
}

function getUserName(){

	var user = localStorage.getItem("UserNameLoged");
	document.getElementById("userName").textContent += user;
	
}


//clear local storage (username, id)
function clearLocalStorage(){
	localStorage.clear();
}


//function pour envoyer data dans le url onclick "Produits en recepetion"
function redirect() {
	window.location.href = "http://localhost:8080/FabeltaInventaireApp/InventaireControllerServlet?command=PRODUITENRECEPTION"

}

//recetion local storage
function setFromReception(){
	localStorage.setItem("fromReception","non");
}
