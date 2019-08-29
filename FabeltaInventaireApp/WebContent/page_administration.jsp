<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/jpg" href="images/fableta_logo.jpg" />

<!-- css calendrier -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<!--bootstrap CSS-->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- CSS link -->
<link style="text/css" href="css/style.css" rel="stylesheet">
<!-- Glyphicons link -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css"
	rel="stylesheet">
<title>Page Administration - Fabelta</title>
</head>

<body>
	<h1 class="text-center">Application Inventaire</h1>
	<h1 class="text-center">Page Administration</h1>

	<div class="row">
		<div class="col-12 text-center">
			<button type="button" class="btn btn-default btn-lg text-center"
				onclick="window.location.href = 'InventaireControllerServlet'">
				<span class="glyphicon glyphicon-home" style="font-size: 4em"></span>
			</button>
			<p>Page Login</p>
		</div>
	</div>

	<div id="accordion">

		<div class="card">
			<div class="card-header">
				<a class="card-link" data-toggle="collapse" href="#collapseOne">
					Rapport EXCEL </a>
			</div>
			<div id="collapseOne" class="collapse" data-parent="#accordion">
				<div class="row">
					<div class="col-lg-3 text-center">
						<h3>Creer rapport Inventaire</h3>
						<form action="InventaireControllerServlet" method="get">
							<input type="hidden" name="command" value="RAPPORTINVENTAIRE" />
							<button type="submit">
								<span style="font-size: 6em; color: blue"
									class="glyphicon glyphicon-signal" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div class="col-lg-3 text-center">
						<h3>Creer rapport historique</h3>
						<button type="button" data-toggle="modal"
							data-target="#modalDates">
							<span style="font-size: 6em; color: red"
								class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- logo fabelta -->
	<div>
		<img class="d-none d-sm-block" alt="image_fabelta"
			src="images/fableta_logo.jpg"
			style="width: 10%; display: block; margin-left: auto; margin-right: auto;">
	</div>


	<!-- modal pour entrer les dates de recherche dans le rapport historique -->
	<div class="modal fade" id="modalDates" role="dialogue">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">ENTREZ LES DATES</h5>

				</div>
				<div class="modal-body">
					<form action="InventaireControllerServlet" method="get">
						<div class="form-group">
							<input type="hidden" name="command" value="RAPPORTHISTORIC" /> <label
								class="control-label" for="date">Date</label> <input
								class="form-control" id="date" name="dateDe"
								placeholder="YYYY/MM/DD" type="text" /> <label
								class="control-label" for="date2">Date</label> <input
								class="form-control" id="date2" name="dateA"
								placeholder="YYYY/MM/DD" type="text" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">CLOSE</button>
							<button type="submit">ACCEPT</button>
						</div>
					</form>
				</div>

			</div>
		</div>

	</div>



	<!-- ajout des fichiers scripts -->
	<%@include file="includes/ajoutScripts.jsp"%>
	<script type="text/javascript" src="js/page_administration.js"></script>
	<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</body>
</html>