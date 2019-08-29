<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<link style="text/css" href="css/style.css" rel="stylesheet">

<title>Inventaire-APP-Fabelta</title>
</head>
<body>
	<!-- titre principale -->

	<h2 class="text-center titre-principale">INVENTAIRE</h2>

	<!-- div pour la date -->
	<div class="text-center">
		<h4 id="dateToday"></h4>
	</div>

	<div class="text-center">
		<p id="userName">Bienvenue:</p>
	</div>
	<!-- Boutons du main menu -->
	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-warning btn-menu-principale"
			data-toggle="modal" data-target="#modalReception">
			<h2>RECEPTION</h2>
		</button>

	</div>

	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-danger btn-menu-principale"
			data-toggle="modal" data-target="#modalDeplacement">
			<h2>DEPLACEMENT</h2>
		</button>
	</div>

	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-primary btn-menu-principale"
			data-toggle="modal" data-target="#modalEnvoiEnProduction">
			<h2>ENVOI EN PRODUCTION</h2>
		</button>
	</div>

	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-sm btn-block  btn-secondary btn-menu-principale"
			data-toggle="modal" data-target="#modalEnvoiEnPeinture">
			<h2>ENVOI EN PEINTURE</h2>
		</button>
	</div>

	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-sm btn-block  btn-light btn-menu-principale"
			data-toggle="modal" data-target="#modalScrap">
			<h2>ENVOI EN SCRAP</h2>
		</button>
	</div>


	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block btn btn-info btn-menu-principale"
			id="boutonProdEnReception" onclick="redirect();">
			<h2>PRODUITS EN RECEPTION</h2>
		</button>
	</div>

	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-success btn-menu-principale"
			data-toggle="modal" data-target="#modalRecherche">
			<h2>RECHERCHE</h2>
		</button>
	</div>

	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-sm btn-block  btn-dark btn-menu-principale"
			onclick="window.location.href = 'InventaireControllerServlet';clearLocalStorage()">
			<h2>SE DECONNECTER</h2>
		</button>
	</div>

	<!-- modal reception -->
	<%@include file="includes/modals/modals_main/modal_reception.jsp"%>

	<!-- modal deplacement -->
	<%@include file="includes/modals/modals_main/modal_deplacement.jsp"%>

	<!-- modal envoi en production -->
	<%@include file="includes/modals/modals_main/modal_production.jsp"%>

	<!-- modal recherche -->
	<%@include file="includes/modals/modals_main/modal_recherche.jsp"%>

	<!-- Mmdal envoi en peinture -->
	<%@include file="includes/modals/modals_main/modal_peinture.jsp"%>

	<!-- modal envoi en scrap -->
	<%@include file="includes/modals/modals_main/modal_scrap.jsp"%>

	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
	<script type="text/javascript" src="js/main_page.js"></script>
</body>
</html>