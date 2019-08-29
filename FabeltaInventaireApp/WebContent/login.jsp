<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/jpg" href="images/fableta_logo.jpg" />

<!--bootstrap CSS-->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- CSS link -->
<link style="text/css" href="css/style.css" rel="stylesheet">
<!-- Glyphicons link -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css"
	rel="stylesheet">
<title>Fabelta Inventaire - Connectez -Vous</title>
</head>

<body>
	<!-- titre principale -->
	<h2 class="text-center titre-principale">APPLICATION INVENTAIRE</h2>

	<!-- logo fabelta -->
	<div class="text-center">
		<img alt="image_fabelta" src="images/fableta_logo.jpg"
			style="width: 30%">
	</div>


	<!-- input select pour montrer les utilisateurs valides -->
	<form action="InventaireControllerServlet" method="post">
		<div class="container">

			<div class="form-group text-center">
				<label for="sel1" style="font-size: 20px;"> SELECTIONEZ
					VOTRE UTILISATEUR</label> <select class="form-control" id="formLogin"
					style="height: 75px; font-size: 30px;" name="username">
					<c:forEach var="tempUsers" items="${USERS}">
						<option data-uuid="${tempUsers.id}" value="${tempUsers.userName}"
							style="font-size: 30px;">${tempUsers.userName }</option>
					</c:forEach>
				</select>
				<h2>PASSWORD</h2>
				<input type="password"
					style="height: 75px; width: 340px; font-size: 30px;"
					name="password"> <input type="hidden" name="command"
					value="LOGIN">
			</div>

		</div>

		<!-- Montrer le message de mauvaise mot de passeS -->
		<div class="text-center">
			<c:set var="wrongPass" value="${WRONGPASSWORD}" />
			<c:if test="${wrongPass == 0}">
				<p id="errorMessage" style="font-size: 25px; color: red">
					<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>MAUVAIS
					MOT DE PASSE
				</p>

			</c:if>
		</div>

		<!-- bouton pour se connecter -->
		<div class="d-flex justify-content-center">
			<button type="submit" id="btnSeConnecter"
				class="btn btn-primary btn-lg btn-block  btn-dark btn-menu-principale"
				onclick="getName(); saveUser()">
				<h2>CONNECTEZ-VOUS</h2>
			</button>

		</div>
	</form>

	<!-- bouton pour aller a la page administration -->
	<div class="d-flex justify-content-center">
		<button type="button"
			class="btn btn-primary btn-lg btn-block  btn-dark btn-menu-principale"
			onclick="window.location.href = 'page_administration.jsp'">
			<h2>ADMINISTRATION</h2>
		</button>

	</div>

	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
	<script type="text/javascript" src="js/login_page.js"></script>
</body>
</html>