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

<title>Deplacement-Produit</title>
</head>
<body>
	<!-- Titre principale -->
	<h2 class="text-center titre-principale">DEPLACEZ UN PRODUIT</h2>

	<!-- ENTETE (Image,id produit, quantite) -->
	<div id="divPrincipaleReception" class="row">
		<!-- Div image -->
		<c:forEach var="tempProdDeplacement" items="${PRODUITADEPLACER}">
			<div class="col-5">
				<c:set var="codeProdSplit"
					value="${fn:split(tempProdDeplacement.code, '/') }" />
				<img src="images/images_produits/Extrusions/${codeProdSplit[0]}.jpg"
					alt="image produit" class="img-thumbnail" id="imgProduit"
					style="padding: 2%; max-width: 75%">

				<!-- The Modal -->
				<div id="modalImgProduit" class="modal modal-prod">
					<span class="close">&times;</span> <img
						class="modal-content modal-content-prod" id="img01">
					<div id="caption"></div>
				</div>
			</div>

			<!-- Div id prduit -->
			<div class="col-4 text-center">
				<h3>Id Produit</h3>

				<code>${tempProdDeplacement.code}</code>

			</div>
		</c:forEach>
		<!-- calcul total quantite -->
		<c:set var="totalSum" value="${0}" />
		<c:forEach var="totalQuantitePord" items="${PRODUCTZONEAPLACER}">
			<c:set var="totalSum" value="${totalSum + totalQuantitePord.value }" />
		</c:forEach>

		<!-- Div quantite -->
		<div class="col-3 text-center">
			<h3>Quantite</h3>
			<p>${totalSum}</p>
		</div>
	</div>

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

	<!-- Description du produit -->
	<div id="descriptionProduit">
		<h4>
			<c:forEach var="tempProdDeplacement" items="${PRODUITADEPLACER}">
			DESCRIPTION <small>${tempProdDeplacement.description}</small>
			</c:forEach>
		</h4>
	</div>
	<hr class="style15">
	<!-- titre 2 -->
	<h2 class="text-center">LOCALISATION DE DEPART</h2>


	<div class="text-center">
		<input type="text" id="myInput" onkeyup="tableRecherce()"
			placeholder="Rechercher la localisation..." size="40px;"
			style="height: 40px;">
	</div>

	<hr class="style15">

	<!-- table pour montrer les resultats de la recherche  -->
	<table class="table table-striped" id="myTable">
		<thead>
			<tr>
				<th scope="col">LOCALISATION</th>
				<th scope="col">QUANTITE</th>
			</tr>
		</thead>
		<tbody>
			<!--  key = location & value = quantite  -->
			<c:forEach var="tempProdLocationDep" items="${PRODUCTZONEAPLACER}">
				<tr>
					<td style="font-size: 25px;"><a id="codeZoneADeplacer"
						data-zonedeplacement="${tempProdLocationDep.key}"
						data-qtydeplacement="${tempProdLocationDep.value}"
						onclick="setDataInModalDeplacement(this)"
						data-target="#modalConfirmationToutDeplacement"
						data-toggle="modal" href="#modalConfirmationToutDeplacement">${tempProdLocationDep.key}</a></td>
					<td style="font-size: 25px;">${tempProdLocationDep.value}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Bouton fermer -->
	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-block  btn-danger btn-menu-principale"
			data-toggle="modal" data-target="#modalReception"
			onclick="window.location.href = 'main.jsp';">
			<h2>FERMER</h2>
		</button>

	</div>

	<!-- modal confirmation de deplacer tout la quantite -->
	<%@include file="includes/modals/modals_deplacement/modal_deplacement_confirmation.jsp"%>
	
	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
</body>
<script type="text/javascript" src="js/deplacement.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</html>