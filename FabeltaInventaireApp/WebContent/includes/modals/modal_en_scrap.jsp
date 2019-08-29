<div id="modalEnvoiTotalEnScrap" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal Content -->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-center">ENVOYEZ EN SCRAP</h4>
				</div>
				<div class="modal-body text-center">
					<!-- formulare avec l'information a envoyer au backend -->
					<form>
						<!-- information envoye au backend -->
						<input type="hidden" name="command" value="ENVOIENSCRAPCODE">
						<input type="hidden" id="idUserName">

						<!-- entete modal -->
						<div>
							<h3>Id Produit</h3>
							<c:forEach var="tempProdInformation"
								items="${PRODUITFROMENVOIENSCRAP}">
								<input type="text" class="text-center" readonly="readonly"
									name="codeProdEnvoiEnScrap" style="width: 120px;"
									value="${tempProdInformation.code}">
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
								<input type="number" class="text-center" id="inputQtyAScrap"
									style="width: 60px;" required="required" min="1"
									name="quantiteADeduire">
							</div>
							<div class="col-4">
								<input type="text" class="text-center" id="inputZoneCode"
									style="width: 120px;" readonly="readonly" name="zoneCode">
							</div>
							<div class="col-5">
								<p>SCRAP</p>
							</div>
						</div>

						<hr class="style15">

						<div style="margin-top: 10px;">
							<p>
								QUANTITE DANS LA LOCALISATION: <span id="qtyInZone"></span>
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
