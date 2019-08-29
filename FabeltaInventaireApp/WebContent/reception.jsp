
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html >
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/jpg" href="images/fableta_logo.jpg" />

<!--bootstrap CSS-->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- CSS link -->
<link href="css/style.css" rel="stylesheet">

<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css"
	rel="stylesheet">

<title>Reception Produit</title>
</head>
<body>
	<!-- Titre principale -->
	<h2 class="text-center titre-principale">RECEPTION</h2>

	<!-- ENTETE (Image,id produit, quantite) -->
	<div id="divPrincipaleReception" class="row">
		<!-- Div image -->
		<c:forEach var="tempProdReception" items="${PRODUITFROMRECEPTION}">
			<div class="col-5">
				<c:set var="codeProdSplit"
					value="${fn:split(tempProdReception.code, '/')}" />
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

				<code id="codeProduit">${tempProdReception.code}</code>
			</div>
		</c:forEach>
		<!-- Div quantite -->
		<div class="col-3 text-center">
			<h3>Quantite</h3>
			<c:forEach var="tempQuantite" items="${QUANTITE}">
				<input type="number" id="inputQuantite" value="${tempQuantite}"
					style="width: 50px;">
			</c:forEach>
		</div>
	</div>

	<!-- Description du produit -->
	<div id="descriptionProduit">
		<h4>
			<c:forEach var="tempProdDescReception"
				items="${PRODUITFROMRECEPTION}">
			DESCRIPTION <small>${tempProdDescReception.description}</small>
			</c:forEach>
		</h4>
	</div>

	<!-- titre 2 -->
	<h2 class="text-center">PLACEZ LE PRODUIT</h2>
	<hr class="style15">
	<br>

	<!-- afficher si le produdit nexiste pas -->
	<c:set var="notFound" value="${PRODENRECNONEXISTE}" />
	<c:if test="${notFound == 0}">
		<p class="text-center" style="font-size: 25px; color: red">
			<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>PRODUIT
			NON TROUVE
		</p>
		<br>
	</c:if>

	<h4 class="text-left" style="margin-left: 10px;">LOCALISATION DE
		DEPART</h4>

	<!-- zone pour scanner la zone ou on va mettre le produit -->
	<div class="row" style="padding: 10px;">

		<div class="col-6">
			<input type="text" id="inputScannezZone" style="width: 150px;">
		</div>
		<div class="col-3">
			<button type="button" class="btn  btn-block  btn-warning"
				id="boutonToutScannezVotreZone" data-toggle="modal"
				style="height: 40px; width: 80px;"
				data-target="#modalConfirmationToutReception"
				onclick="setDataZone(); getQuantite(); setNameParams() ">TOUT</button>
		</div>
		<div class="col-3">
			<button type="button" class="btn  btn-block  btn-warning"
				style="height: 40px; width: 75px;" id="boutonSplitScannezVotreCode"
				data-toggle="modal" data-target="#modalConfirmationSplitReception"
				onclick="setDataZoneSplit();getQuantite();setMaxAttributreSplit(); setNameParamnsSplitScanned()">
				PARTIEL</button>
		</div>
	</div>

	<!-- Titre Location/Quantite -->
	<div class="row">
		<div class="col-5 text-center">
			<h2>LOCALISATION</h2>
		</div>
		<div class="col-1 text-center">
			<h2>QTE</h2>
		</div>
	</div>

	<!-- ajouter par default reception -->
	<div class="row">
		<c:forEach var="tempReception" items="${RECEPTIONINFO}">
			<div class="col-5 text-center">
				<h2>${tempReception.key}</h2>
			</div>
			<div class="col-1 text-center">
				<h2>${tempReception.value}</h2>
			</div>
			<div class="col-3 text-center">
				<button type="button" class="btn  btn-block  btn-warning"
					style="height: 40px; width: 80px; margin-bottom: 10px;"
					data-toggle="modal" data-target="#modalConfirmationToutReception"
					id="boutonTout" data-zone="ENT-REC-000"
					onclick="getDataZone(this); getQuantite(); setNameParamsTout()">TOUT</button>
			</div>
			<div class="col-3 text-center">
				<button type="button" class="btn  btn-block  btn-warning"
					style="height: 40px; width: 80px; margin-left: -10px;"
					data-toggle="modal" data-target="#modalConfirmationSplitReception"
					id="boutonSplit" data-zone="ENT-REC-000"
					onclick="getDataZoneSplit(this); getQuantiteSplit(); setMaxAttributreSplit(); setNameParamnsSplit()">PARTIEL</button>
			</div>
		</c:forEach>
	</div>

	<!-- Resultats de la recherche Location/Quantite -->
	<div class="row">
		<c:forEach var="tempProdLocationReception"
			items="${PRODUITZONEFROMERECEPTION}">
			<div class="col-5 text-center">
				<h2 id="locationProduitReception">${tempProdLocationReception.key}</h2>
			</div>
			<div class="col-1 text-center align-middle">
				<h2>${tempProdLocationReception.value}</h2>
			</div>

			<div class="col-3 text-center">
				<button type="button" class="btn  btn-block  btn-warning"
					style="height: 40px; width: 80px; margin-bottom: 10px;"
					data-toggle="modal" data-target="#modalConfirmationToutReception"
					id="boutonTout" data-zone="${tempProdLocationReception.key}"
					onclick="getDataZone(this); getQuantite(); setNameParamsTout()">TOUT</button>
			</div>
			<div class="col-3 text-center">
				<button type="button" class="btn  btn-block  btn-warning"
					style="height: 40px; width: 80px; margin-left: -10px;"
					data-toggle="modal" data-target="#modalConfirmationSplitReception"
					id="boutonSplit" data-zone="${tempProdLocationReception.key}"
					onclick="getDataZoneSplit(this); getQuantiteSplit(); setMaxAttributreSplit(); setNameParamnsSplit()">PARTIEL</button>
			</div>
		</c:forEach>
	</div>

	<!-- Bouton fermer -->
	<div class="d-flex justify-content-center">

		<button type="button" id="boutonFermerReception"
			class="btn btn-primary btn-block  btn-warning btn-menu-principale"
			data-toggle="modal" data-target="#modalReception">
			<h2>FERMER</h2>
		</button>

	</div>

	<!-- modal confirmation tout ajouter -->
	<%@include file="includes/modals/modals_reception/modal_tout_ajouter.jsp"%>

	<!-- modal confirmation split quantite produit -->
	<%@include file="includes/modals/modals_reception/modal_split.jsp"%>

	<!-- modal a montrer si on ferme et la quantite est > 0 -->
	<%@include file="includes/modals/modals_reception/modal_quantite.jsp"%>

	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>

</body>
<script type="text/javascript" src="js/script.js"></script>
<script type="text/javascript" src="js/reception.js"></script>
</html>