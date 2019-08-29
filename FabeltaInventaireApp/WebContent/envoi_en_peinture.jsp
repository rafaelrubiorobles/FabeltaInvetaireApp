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

<title>Envoi en peinture</title>
</head>
<body>
	<!-- Titre principale -->
	<h2 class="text-center titre-principale">ENVOYEZ UN PRODUIT EN
		PEINTURE</h2>

	<!-- ENTETE (Image,id produit, quantite) -->
	<div id="divPrincipaleReception" class="row">
		<c:forEach var="tempProdEnvoiEnProd"
			items="${PRODUITFROMENVOIENPEINTURE}">
			<!-- Div image -->
			<div class="col-5">
				<c:set var="codeProdSplit"
					value="${fn:split(tempProdEnvoiEnProd.code, '/')}" />
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
			<div class="col-7 text-center">
				<h3>Id Produit</h3>

				<code>${tempProdEnvoiEnProd.code}</code>

			</div>
		</c:forEach>
	</div>

	<!-- Description du produit -->
	<div id="descriptionProduit">
		<h4>
			<c:forEach var="tempProdDescEnvoiEnProd"
				items="${PRODUITFROMENVOIENPEINTURE}">
			DESCRIPTION <small>${tempProdDescEnvoiEnProd.description}</small>
			</c:forEach>
		</h4>
	</div>

	<!-- message d'erreur si produit non trouve  -->
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

	<!-- titre 2 -->
	<h2 class="text-center">ENVOYEZ LE PRODUIT EN PEINTURE</h2>

	<!-- input recherche -->
	<div class="text-center">
		<input type="text" id="myInput" onkeyup="tableRecherce()"
			placeholder="Rechercher la localisation..." size="40px;"
			style="height: 40px;">
	</div>

	<!-- table pour afficher les resultats de la recherche -->
	<table class="table table-striped" id="myTable">
		<thead>
			<tr>
				<th>LOCALISATION</th>
				<th>QUANTITE</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tempProdLocationEnvoiEnProd"
				items="${PRODUITZONEENVOIENPEINTURE}">
				<tr>
					<td><a id="codeZone" style="font-size: 25px;"
						data-code="${tempProdLocationEnvoiEnProd.key}"
						data-qty="${tempProdLocationEnvoiEnProd.value}"
						onclick="getQtyCodeZone(this)"
						data-target="#modalEnvoiTotalEnPeinture" data-toggle="modal"
						href="modalEnvoiTotalEnPeinture">${tempProdLocationEnvoiEnProd.key}</a></td>
					<td><h2 id="quantiteZone">${tempProdLocationEnvoiEnProd.value}</h2></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Bouton fermer -->
	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-block  btn-secondary btn-menu-principale"
			onclick="window.location.href = 'main.jsp';">
			<h2>FERMER</h2>
		</button>

	</div>


	<!-- modal confirmation envoi en peinture -->
	<%@include file="includes/modals/modal_en_peinture.jsp"%>

	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
</body>
<script type="text/javascript" src="js/script.js"></script>
<script type="text/javascript" src="js/envoi_en_peinture.js"></script>
</html>