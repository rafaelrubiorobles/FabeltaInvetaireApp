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
<title>Information-Produit</title>
</head>
<body>
	<!-- titre principale -->

	<h2 class="text-center titre-principale">INFORMATION PRODUIT</h2>

	<div class="text-center">

		<h3>Ajouter un commentaire</h3>

		<hr class="style15">

		<form action="InventaireControllerServlet" method="get">

			<c:set var="comment" value="${COMMENTAIRE}" />
			<textarea rows="3" cols="40" name="commentTochange">${comment}</textarea>

			<c:set var="prod" value="${PRODCODE}" />
			<input type="hidden" name="produitCode" value="${prod}">

			<c:set var="zone" value="${ZONECODE}" />
			<input type="hidden" name="zoneCode" value="${zone}"> <input
				type="hidden" name="command" value="EDITCOMMENT"> <br />

			<button type="submit" class="btn-success btn-lg">SAUVEGARDER</button>
		</form>
	</div>



	<hr class="style15">


	<!-- Bouton Fermer -->
	<div class="d-flex justify-content-center">

		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-success btn-menu-principale"
			onclick="window.location.href = 'main.jsp';">
			<h2>FERMER</h2>
		</button>
	</div>
	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
	<script type="text/javascript" src="js/recherche_page.js"></script>
</body>
</html>