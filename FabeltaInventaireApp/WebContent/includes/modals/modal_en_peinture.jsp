<div id="modalEnvoiTotalEnPeinture" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header" style="background-color: gray">
				<h4 class="modal-title text-center">ENVOYEZ EN PEINTURE</h4>
			</div>
			<div class="modal-body text-center">
				<!-- formulaire avec l'information a envoyer au backend -->
				<form action="InventaireControllerServlet" method="get">
					<!-- Entete modal -->
					<div>
						<h3>Id produit</h3>
						<c:forEach var="tempIdProdEnvoiEnPeinture"
							items="${PRODUITFROMENVOIENPEINTURE}">
							<input type="text" class="text-center" id="idCodeProd"
								value="${tempIdProdEnvoiEnPeinture.code}" style="width: 120px;"
								name="codeProdEnvoiEnPeinture" readonly="readonly" />
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

					<!-- information envoye au backend -->
					<input type="hidden" name="command" value="ENVOIENPEINTURE">
					<input type="hidden" id="userDataId" />

					<div class="row">
						<div class="col-3">
							<input type="number" class="text-center" style="width: 60px;"
								required="required" min="1" id="inputQtyAEnvoyerPeinture"
								name="quantiteEnvoyerEnPeinture">
						</div>
						<div class="col-4">
							<input type="text" class="text-center" id="zoneCodeInputPeinture"
								style="width: 120px;" name="zoneProduitAPeinture"
								readonly="readonly" />
						</div>
						<div class="col-5">
							<p>PEINTURE</p>
						</div>
					</div>

					<hr class="style15">

					<div style="margin-top: 10px;">
						<p>
							QUANTITE DANS LA LOCALISATION: <span
								id="quantiteZoneInModalPeinture"></span>
						</p>
					</div>

					<div class="modal-footer" style="background-color: gray">
						<button type="button" class="btn btn-default btn-block"
							data-dismiss="modal">ANNULER</button>
						<button type="submit" class="btn btn-default btn-block">ACCEPT</button>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>