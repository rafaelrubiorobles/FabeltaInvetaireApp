<div id="modalConfirmationSplitReception" class="modal fade"
	role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content text-center">
			<div class="modal-header" style="background-color: orange">
				<h4 class="modal-title text-center">CONFIRMEZ LA QUANTITE A
					AJOUTER DANS LA ZONE</h4>
			</div>

			<hr class="style15">

			<h3>DIVISEZ LA COMMANDE</h3>

			<div class="row">
				<div class="col-5">
					<h4>PODUIT ID</h4>
				</div>
				<div class="col-2">
					<h4>QTE</h4>
				</div>
				<div class="col-5">
					<h4>ZONE</h4>
				</div>
			</div>

			<form action="InventaireControllerServlet" method="get">
				<div class="row">

					<div class="col-1">
						<input type="hidden" name="command" value="RECEPTIONSPLIT" /> <input
							type="hidden" id="boutonSplitX" /> <input type="hidden"
							id="userIdInput" /><input type="hidden"
							id="fromReceptionNonSplit" />
					</div>

					<div class="col-4">

						<c:forEach var="tempProdReceptionSplit"
							items="${PRODUITFROMRECEPTION}">
							<input type="text" class="text-center" id="idProduitModalSplit"
								value="${tempProdReceptionSplit.code}" style="width: 120px;"
								name="codeProdSplit" readonly="readonly" />
						</c:forEach>

					</div>

					<div class="col-2">

						<!-- Quantite replit dynamiquement par js (reception.js) -->
						<input class="text-center" type="number"
							id="quantiteProduitModalSplit" style="width: 45px;"
							name="qtyProdSplit" required min="1" />
					</div>

					<div class="col-5">

						<!-- zone remplit dynamiquement par js (recception.js) -->
						<input type="text" class="text-center" id="zonePorduitModalSplit"
							style="width: 110px;" name="zoneProdSplit" readonly="readonly" />

					</div>
				</div>

				<hr class="style15">


				<span>QUANTITE TOTAL COMMANDE:<input id="spanQteTotalSplit"
					type="text" readonly="readonly" style="width: 40px;"
					name="quantiteTotal" /></span>
				<hr class="style15">

				<div class="modal-footer" style="background-color: orange">
					<button type="button" class="btn btn-default btn-block"
						data-dismiss="modal">ANNULER</button>
					<button type="submit" class="btn btn-default btn-block">ACCEPT</button>
				</div>

			</form>
		</div>

	</div>
</div>