<div id="modalConfirmationToutReception" class="modal fade"
	role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content text-center">
			<div class="modal-header" style="background-color: orange">
				<h4 class="modal-title text-center">CONFIRMEZ L'AJOUT</h4>
			</div>

			<!-- Titres Product id/qte/zone  -->
			<div class="modal-body">

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

			</div>

			<!-- Information de l'ajout -->
			<form action="InventaireControllerServlet" method="get">
				<div class="row">
					<input type="hidden" name="command" value="RECEPTIONTOUT" /> <input
						type="hidden" id="boutonX" /> <input type="hidden"
						id="userIdInputX" /> <input type="hidden" id="fromReceptionNon" />

					<div class="col-1"></div>

					<div class="col-4">
						<c:forEach var="tempProdReceptionTout"
							items="${PRODUITFROMRECEPTION}">
							<input type="text" class="text-center" id="idProduitModalTout"
								value="${tempProdReceptionTout.code}" style="width: 120px;"
								name="codeProd" readonly="readonly">
						</c:forEach>

					</div>

					<!-- quantite replit dynamiquement par js (reception.js)-->
					<div class="col-2">
						<input class="text-center" id="quantiteProduitModalTout"
							type="number" style="width: 45px;" name="qtyProd" min="1" />
					</div>

					<!-- zone remplit dynamieuqment par js (reception.js) -->
					<div class="col-5">
						<input type="text" class="text-center" id="zoneProduitModalTout"
							style="width: 110px;" name="zoneProd" readonly="readonly" />
					</div>
				</div>
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