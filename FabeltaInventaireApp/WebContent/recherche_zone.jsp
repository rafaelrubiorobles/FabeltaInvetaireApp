<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Rechercher-zone</title>
</head>
<body>
	<!-- titre principale -->

	<h2 class="text-center titre-principale">RECHERCHER UNE
		LOCALISATION</h2>

	<!-- Input et bouton pour rechercher -->
	<div class="row" id="searchContent">
		<form action="InventaireControllerServlet" method="get" class="row">

			<!-- on envoi la variable command (value = SEARCHZONE) par le url -->
			<div class="col-1 col-sm-1">
				<input type="hidden" name="command" value="SEARCHZONE" />
			</div>

			<div class="col-7 col-sm-7">
				<input class="input-lg" type="text" id="inputRechercher"
					name="produitZone" style="width: 200px; height: 45px;">
			</div>

			<div class="col-4 col-sm-4">
				<button class="btn btn-primary btn-lg btn-block" type="submit"
					id="boutonRechercher" disabled="disabled">
					<span style="font-size: 1.9em;" class="glyphicon glyphicon-search"></span>
				</button>
			</div>

		</form>
	</div>

	<!-- Resultats-->
	<h2 class="text-center">RESULTATS</h2>
	<hr class="style15">
	<br>

	<div>
		<h3 class="text-center">
			<c:forEach var="tempZone" items="${ZONE}">
				<p id="zoneCodeInCode">${tempZone.code}</p>
			</c:forEach>
		</h3>
	</div>

	<!-- traitement de la somme de la quantite total dans cette zone -->
	<c:set var="totalProd" value="${0}" />
	<c:forEach var="totalPrduitsZone" items="${PRODUITLOCATIONINZONE}">
		<c:set var="totalProd" value="${totalProd + totalPrduitsZone.value}" />
	</c:forEach>


	<div class="text-center">
		<h3>
			<c:set var="notFound" value="${NONTROUVE}" />
			<c:if test="${notFound == 0}">
				<p style="font-size: 25px; color: red">
					<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>ZONE
					NON TROUVE
				</p>
				<br>
			</c:if>
			<code>QUANTITE DANS LA LOCALISATION:</code>${totalProd}</h3>
	</div>
	<hr class="style15">

	<div class="text-center">
		<h5>SCANNEZ LE PRODUIT</h5>
		<input type="text" id="myInput" onkeyup="tableRecherce()"
			placeholder="Rechercher le produit..." size="40px;"
			style="height: 40px;">
	</div>

	<!-- table pour montrer les resultats de la recherche de la zone-->
	<table class="table table-striped" id="myTable">
		<thead>
			<tr>
				<th scope="col">PRODUIT</th>
				<th scope="col">QUANITE</th>
				<th scope="col">INFORMATION</th>
			</tr>
		</thead>
		<tbody>
			<!-- key = produit & value = quantite -->
			<c:forEach var="tempLocationProduit2"
				items="${PRODUITLOCATIONINZONE}">
				<tr>
					<td style="font-size: 20px;">${tempLocationProduit2.key}</td>
					<td style="font-size: 20px;"><a
						onclick="informationToModify(this)"
						data-code="${tempLocationProduit2.key}"
						data-qty="${tempLocationProduit2.value }"
						data-target="#modalAjustementProduitEnZone" data-toggle="modal"
						href="#modalAjustementProduitEnZone">${tempLocationProduit2.value}</a></td>
					<td><button class="btn btn-primary btn-lg btn-block"
							id="boutonInformation" data-code="${tempLocationProduit2.key}"
							onclick="sendInformationFormZone(this);">
							<span style="font-size: 1.5em;"
								class="glyphicon glyphicon-info-sign"></span>
						</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form action="InventaireControllerServlet" method="get">
		<input type="hidden" name="command" value="COMMENTAIRE"> <input
			type="hidden" id="prodCode"> <input type="hidden"
			id="zoneCode">
		<button type="submit" hidden="hidden" id="buttonSubmitComment"></button>
	</form>

	<!-- Bouton Fermer -->
	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-success btn-menu-principale"
			onclick="window.location.href = 'main.jsp';">
			<h2>FERMER</h2>
		</button>
	</div>

	<!-- modal ajustement quantite dans la zone -->
	<%@include file="includes/modals/modals_recherche/modal_recherche_zone.jsp"%>

	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
	<script type="text/javascript" src="js/recherche_page.js"></script>
	<script type="text/javascript" src="js/recherche_zone_page.js"></script>
</body>
</html>