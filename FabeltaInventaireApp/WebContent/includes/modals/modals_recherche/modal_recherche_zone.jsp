	<div id="modalAjustementProduitEnZone" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal Content -->
			<div class="modal-content">
				<div class="modal-header">
					<h4>AJUSTEZ LES PRODUIT</h4>
				</div>
				<div class="modal-body text-center">
					<!-- formulaire pour envoyer l'information au backend -->
					<form action="InventaireControllerServlet" method="get">

						<input type="hidden" name="command" value="AJUSTEMENT"> <input
							type="hidden" id="userIdInformation"> <input
							type="hidden" name="zone" value="rechercherZone">

						<h3>Id Produit</h3>
						<input type="text" class="text-center" style="width: 120px;"
							id="inputIdProduit" name="idProdAAjuster" readonly="readonly" />
						<div class="row">
							<div class="col-6">
								<h4>ZONE</h4>
								<c:forEach var="tempZone" items="${ZONE}">
									<input value="${tempZone.code}" name="codeZoneAAjuster"
										readonly="readonly" style="width: 90px;">
								</c:forEach>
							</div>
							<div class="col-6">
								<h4>QUANTITE</h4>
								<input type="number" id="quantiteAModifier"
									name="quantiteAAjuster" class="text-center"
									style="width: 60px;" required="required" value="0">
							</div>
						</div>
						<hr class="style15">
						<div>
							<p>
								QUANTITE DANS CETTE ZONE:<span id="quantiteEnZoneProd"></span>
							</p>
						</div>

						<div class="row">
							<button type="button" class="btn-lg btn-default btn-block col-6"
								id="plus">AJOUTER</button>
							<button type="button" class="btn-lg btn-default btn-block col-6"
								id="minus" style="margin: 0">ENLEVER</button>
						</div>

						<div class="modal-footer" style="background-color: green">
							<button type="button" class="btn btn-default btn-block"
								data-dismiss="modal">ANNULER</button>
							<button type="submit" class="btn btn-default btn-block">ACCEPT</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>