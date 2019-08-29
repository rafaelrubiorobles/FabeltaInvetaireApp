<div id="modalEnvoiTotalEnProduction" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header" style="background-color: royalblue">
				<h4 class="modal-title text-center">ENVOYEZ EN PRODUCTION</h4>
			</div>
			<div class="modal-body text-center">
				<!-- formulaire avec l'information a envoyer au backend -->
				<form action="InventaireControllerServlet" method="get">
					<div>
						<h3>Id produit</h3>
						<c:forEach var="tempIdProdEnvoiEnProd"
							items="${PRODUITFROMENVOIENPROD}">
							<input type="text" class="text-center" id="idCodeProd"
								value="${tempIdProdEnvoiEnProd.code}" style="width: 120px;"
								name="codeProdEnvoiEnProd" readonly="readonly" />
						</c:forEach>
					</div>

					<hr class="style15">

					<div class="row">
						<div class="col-3">
							<p>ENVOYER</p>
						</div>
						<div class="col-4">
							<p>DE</p>
						</div>
						<div class="col-5">
							<p>A</p>
						</div>
					</div>

					<input type="hidden" name="command" value="ENVOIENPRODUCTION">
					<input type="hidden" id="userData" />

					<div class="row">
						<div class="col-3">
							<input type="number" class="text-center" style="width: 60px;"
								required="required" min="1" id="inputQtyAEnvoyer"
								name="quantiteEnvoyerEnProduction">
						</div>
						<div class="col-4">
							<input type="text" class="text-center" id="zoneCodeInput"
								style="width: 120px;" name="zoneProduit" readonly="readonly" />
						</div>
						<div class="col-5">
							<p>PRODUCTION</p>
						</div>
					</div>

					<hr class="style15">

					<div style="margin-top: 10px;">
						<p>
							QUANTITE DANS LA LOCALISATION: <span id="quantiteZoneInModal"></span>
						</p>
					</div>

					<div class="modal-footer" style="background-color: royalblue">
						<button type="button" class="btn btn-default btn-block"
							data-dismiss="modal">ANNULER</button>
						<button type="submit" class="btn btn-default btn-block">ACCEPT</button>
					</div>
				</form>
			</div>

		</div>

	</div>
</div>