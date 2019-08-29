
<div id="modalEnvoiEnProduction" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header" style="background-color: royalblue">
				<h4 class="modal-title text-center">Scannez le produit</h4>
			</div>

			<form action="InventaireControllerServlet" method="get">

				<div>
					<input type="hidden" name="command" value="ENVOIENPRODUCTIONCODE" />
				</div>

				<div class="modal-body text-center">
					<input type="text" id="codeProduitEnvoiEnProd"
						name="codePorductEnvoiEnProd" required="required">
				</div>

				<div class="modal-footer" style="background-color: royalblue">
					<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
					<button type="submit" class="btn btn-default">Accept</button>
				</div>
			</form>
		</div>

	</div>
</div>
