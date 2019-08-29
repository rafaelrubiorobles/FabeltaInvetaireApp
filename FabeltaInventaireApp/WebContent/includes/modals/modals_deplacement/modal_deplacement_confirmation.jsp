<div id="modalConfirmationToutDeplacement" class="modal fade"
	role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content text-center">
			<div class="modal-header" style="background-color: red">
				<h4 class="modal-title text-center">DEPLACEZ LE PRODUIT VERS</h4>
			</div>

			<div class="modal-body text-center">
				<!-- formulaore avec l'information a envoyer au backend -->
				<form action="InventaireControllerServlet" method="get">

					<!-- information a envoyer par le url -->
					<div>
						<input type="hidden" name="command"
							value="ENVOIPRODUCTDEPLACEMENT"> <input type="hidden"
							id="userData" />
					</div>
					<div>
						<h3>Id produit</h3>
						<c:forEach var="tempProdDeplacement" items="${PRODUITADEPLACER}">
							<input type="text" class="text-center" id="idCodeProdADeplacer"
								style="width: 120px;" name="codeProdADeplacer"
								readonly="readonly" value="${tempProdDeplacement.code }">
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

					<div class="row">
						<div class="col-3">
							<input type="number" class="text-center" style="width: 60px;"
								required="required" min="1" id="inputQtyAEnvoyerDeplacement"
								name="quantiteADeplacer">
						</div>
						<div class="col-4">
							<input type="text" class="text-center"
								id="zoneCodeInputDeplacement" style="width: 120px;"
								name="zoneProduitDe" readonly="readonly" required="required" />
						</div>
						<div class="col-5">
							<input type="text" style="width: 120px;" name="zoneProduitA"
								required="required">
						</div>
					</div>

					<hr class="style15">

					<div style="margin-top: 10px;">
						<p>
							QUANTITE DANS LA LOCALISATION: <span id="quantiteZoneInModal"></span>
						</p>
					</div>

					<div class="modal-footer" style="background-color: red">
						<button type="button" class="btn btn-default btn-block"
							data-dismiss="modal">ANNULER</button>
						<button type="submit" class="btn btn-default btn-block">ACCEPT</button>
					</div>

				</form>
			</div>


		</div>

	</div>
</div>
