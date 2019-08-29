
<div id="modalAjustementProduit" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content -->
		<div class="modal-content">
			<div class="modal-header" style="background-color: green">
				<h4>AJUSTEZ LE PRODUIT</h4>
			</div>
			<div class="modal-body text-center">
				<!-- formulaire pour envoyer linformation au backend -->
				<form action="InventaireControllerServlet" method="get">

					<input type="hidden" name="command" value="AJUSTEMENT"> <input
						type="hidden" id="dataIdAjustement" /> <input type="hidden"
						name="zone" value="rechercherProduit">

					<div>
						<h3>Id Prduit</h3>
						<c:forEach var="tempProdAAjuster" items="${PRODUIT}">
							<input type="text" class="text-center" name="idProdAAjuster"
								id="idCodeProduitAAjuster" value="${tempProdAAjuster.code}"
								style="width: 120px;" readonly="readonly" />
						</c:forEach>
					</div>

					<hr class="style15">

					<div class="row">
						<div class="col-5">
							<p>ZONE</p>
						</div>
						<div class="col-7">
							<p>QUANTITE</p>
						</div>
					</div>

					<div class="row">
						<div class="col-4">
							<input type="text" class="text-center" id="codeZoneAAjuster"
								name="codeZoneAAjuster" style="width: 120px;" name="zoneProduit"
								readonly="readonly" />
						</div>
						<div class="col-8">

							<input type="number" class="text-center" style="width: 60px;"
								name="quantiteAAjuster" required="required"
								id="inputQtyAAjuster">

						</div>
					</div>

					<hr class="style15">

					<div style="margin-top: 10px;">
						<p>
							QUANTITE DANS LA ZONE: <span id="quantiteZoneInModalAjuster"></span>
						</p>
					</div>

					<div class="row">
						<button type="button" class="btn-lg btn-default btn-block col-6"
							id="plusplus">AJOUTER</button>
						<button type="button" class="btn-lg btn-default btn-block col-6"
							id="minusminus" style="margin: 0">ENLEVER</button>
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
