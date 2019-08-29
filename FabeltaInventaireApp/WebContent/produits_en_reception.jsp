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
<title>Produits En Reception</title>
</head>
<body>
	<!-- titre principale -->

	<h2 class="text-center titre-principale">PRODUITS EN RECEPTION</h2>
	
	<!-- input recherche -->
	<div class="text-center">
		<input type="text" id="myInput" onkeyup="tableRecherce()"
			placeholder="Rechercher le produit.." size="40px;"
			style="height: 40px;">
	</div>

	<!-- table pour montrer les resultats de la recherche du produit -->
	<table class="table table-striped" id="myTable">
		<thead>
			<tr>
				<th scope="col" class="text-center">PRODUIT</th>
				<th scope="col" class="text-center">QUANTITE</th>
				<th scope="col" class="text-center">PLACER</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tempProdInReception" items="${PRODUITENRECEPTION}">
				<tr>
					<td style="font-size: 25px;">${tempProdInReception.key}</td>
					<td style="font-size: 25px;">${tempProdInReception.value}</td>
					<td><button class="btn btn-primary btn-lg btn-block"
							data-prod="${tempProdInReception.key}"
							data-qty="${tempProdInReception.value}" data-toggle="modal"
							data-target="#modalConfirmationEnvoiReception"
							onclick="dataInModal(this)">
							<span style="font-size: 1.5em;" class="glyphicon glyphicon-send"></span>
						</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- Bouton fermer -->
	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-info btn-block btn-menu-principale"
			onclick="window.location.href = 'main.jsp';">
			<h2>FERMER</h2>
		</button>
	</div>



	<!-- Modal confiramation envoi dans reception -->
	<div id="modalConfirmationEnvoiReception" class="modal fade"
		role="dialog">
		<div class="modal-dialog">
			<!-- modal content -->
			<div class="modal-content">
				<div class="modal-header" style="background-color: DarkTurquoise">
					<h4>INFORMATION DU PRODUIT</h4>
				</div>
				<div class="modal-body text-center">
					<!-- formulaire pour envoyer l'information vers reception -->
					<form action="InventaireControllerServlet" method="get">

						<input type="hidden" name="command" value="RECEPTIONPRODUITCODE">
						<input type="hidden" id="dataAEnvoyer" name="codePorductReception" />


						<div>
							<h2>ID PRODUIT</h2>
							<input class="text-center" id="inputIdProduit" type="text" />
						</div>
						<div>
							<h2>QUANTITE</h2>
							<input class="text-center" id="inputQuantiteProduit"
								type="number" min="1" />
						</div>

						<hr class="style15">

						<div>
							<p>
								QUANTITE DANS RECEPTION : <span id="qteDansReception"></span>
							</p>
						</div>
						<!-- footer modal -->
						<div class="modal-footer" style="background-color: DarkTurquoise">
							<button type="button" class="btn btn-default btn-block"
								data-dismiss="modal">ANNULER</button>
							<button type="submit" class="btn btn-default btn-block"
								onclick="setFromReception()">ACCEPT</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
	<script type="text/javascript" src="js/produits_en_reception.js"></script>
</body>
</html>