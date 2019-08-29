<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/jpg" href="images/fableta_logo.jpg" />

<!--bootstrap CSS-->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- CSS link -->
<link href="css/style.css" rel="stylesheet">

<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css"
	rel="stylesheet">
<title>Recherche-Produit</title>
</head>
<body>
	<!-- titre principale -->

	<h2 class="text-center titre-principale">RECHERCHER UN PRODUIT</h2>


	<!-- Input et bouton pour rechercher -->
	<div class="row" id="searchContent">
		<form action="InventaireControllerServlet" method="get" class="row">

			<!-- on envoi la variable command (value=SEARCHPRODUCT) par le url -->
			<div class="col-1 col-sm-1">
				<input type="hidden" name="command" value="SEARCHPRODUCT" />
			</div>

			<div class="col-7 col-sm-6">
				<input class="input-lg" type="text" id="inputRechercher"
					name="produitCode" style="width: 200px; height: 45px;">
			</div>

			<div class="col-4 col-sm-3">
				<button class="btn btn-primary btn-lg btn-block" type="submit"
					id="boutonRechercher" disabled="disabled">
					<span style="font-size: 1.9em;" class="glyphicon glyphicon-search"></span>
				</button>

			</div>
		</form>
	</div>

	<!-- Resultats-->
	<h2 class="text-center">RESULTATS</h2>

	<!-- message si  le produit existe pas -->
	<div class="text-center">
		<c:set var="notFound" value="${NONTROUVE}" />
		<c:if test="${notFound == 0}">
			<p style="font-size: 25px; color: red">
				<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>PRODUIT
				NON TROUVE
			</p>
			<br>
		</c:if>
	</div>

	<!-- Zone pour montrer le Id de produit scannee et la quantite total en stock -->
	<div class="row">
		<!-- div pour le ID du produit -->
		<div class="col-5 col-sm-5 text-center">
			<h2>
				<!--  on recoit linformation depuis la requete dans le servlet -->
				<c:forEach var="tempProduit" items="${PRODUIT}">
					<code>${tempProduit.code}</code>
				</c:forEach>

			</h2>

		</div>

		<!-- on traite la somme des quantites recus de la bd -->
		<c:set var="totalSum" value="${0}" />
		<c:forEach var="totalQuantiteProduit" items="${PRODUITLOCATION}">
			<c:set var="totalSum"
				value="${totalSum + totalQuantiteProduit.value}" />
		</c:forEach>


		<!-- div pour le text quantite total -->
		<div class="col-5 col-sm-5">
			<h2>Quantite total:</h2>
		</div>
		<!-- div pour afficher la quantite total -->
		<!-- totalSum viens du c:set en haut -->
		<div class="col-2 col-sm-2">
			<h2>${totalSum}</h2>
		</div>
	</div>

	<!-- Div pour montrer la description -->
	<div class="text-center" style="margin-top: 10px;">
		<c:forEach var="tempProduit" items="${PRODUIT}">
			<p>${tempProduit.description}</p>
		</c:forEach>

	</div>

	<!-- image -->
	<div class="text-center">
		<c:forEach var="prodInfo" items="${PRODUIT}">
			<c:set var="codeProdSplit" value="${fn:split(prodInfo.code, '/') }" />
			<img src="images/images_produits/Extrusions/${codeProdSplit[0]}.jpg"
				alt="image produit" class="img-thumbnail" id="imgProduit"
				style="padding: 2%; max-width: 50%">

			<!-- The Modal -->
			<div id="modalImgProduit" class="modal modal-prod">
				<span class="close">&times;</span> <img
					class="modal-content modal-content-prod" id="img01">
				<div id="caption"></div>
			</div>
		</c:forEach>
	</div>
	<hr class="style15">

	<div class="text-center">
		<h5>SCANNEZ LA LOCALISATION</h5>
		<input type="text" id="myInput" onkeyup="tableRecherce()"
			placeholder="Rechercher la localisation..." size="40px;"
			style="height: 40px;">
	</div>

	<!-- table pour montrer les resultats de la recherche du produit -->
	<table class="table table-striped" id="myTable">
		<thead>
			<tr>
				<th scope="col">LOCALISATION</th>
				<th scope="col">QUANTITE</th>
				<th scope="col">AJUSTER</th>
			</tr>
		</thead>
		<tbody>
			<!-- key = location & value = quantite -->
			<c:forEach var="tempProduitLocation2" items="${PRODUITLOCATION}">
				<tr>
					<td style="font-size: 25px;">${tempProduitLocation2.key}</td>
					<td style="font-size: 25px;">${tempProduitLocation2.value}</td>
					<td><button class="btn btn-primary btn-lg btn-block"
							data-toggle="modal" data-target="#modalAjustementProduit"
							data-zoneajus="${tempProduitLocation2.key}"
							data-qtyajus="${tempProduitLocation2.value}"
							onclick="getQuantiteEtZone(this) ">
							<span style="font-size: 1.5em;"
								class="glyphicon glyphicon-pencil"></span>
						</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<!-- Bouton Fermer -->
	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-success btn-menu-principale"
			onclick="window.location.href = 'main.jsp';">
			<h2>FERMER</h2>
		</button>
	</div>

	<!-- Modal confirmation ajustement produit -->
	<%@include
		file="includes/modals/modals_recherche/modal_recherche_produit.jsp"%>

	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
	<script type="text/javascript" src="js/script.js"></script>
	<script type="text/javascript" src="js/recherche_page.js"></script>
</body>
</html>