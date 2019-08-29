<div id="modalConfirmationEnvoiReceptionSplit" class="modal fade"
	role="dialog">
	<div class="modal-dialog">
		<!-- modal content -->
		<div class="modal-content">
			<div class="modal-header" style="background-color: orange">
				<h4>
					ATTENTION <span class="glyphicon glyphicon-warning-sign"></span>
				</h4>
			</div>
			<div class="modal-body text-center">
				<!-- formulaire pour envoyer l'information vers reception -->
				<form action="InventaireControllerServlet" method="get">

					<input type="hidden" name="command" value="PRDUITENRECEPTION">
					<input type="hidden" id="userNameLoged">

					<h3>Vous avez pas fini de placer le produit</h3>
					<input class="text-center" type="text" readonly="readonly"
						id="prodCodeEnModal" name="prodCodeAEnvoyer">

					<h3>La quantite</h3>
					<input class="text-center" type="number" readonly="readonly"
						id="prodQtyEnModal" name="prodQtyAEnvoyer">

					<h3>sera place dans 'ENT-REC-000'</h3>

					<hr class="style15">
					<h3>
						<span class="glyphicon glyphicon-info-sign"></span>Vous pouvez le
						retrouver dans "Poduits en reception, Menu Principal"
					</h3>

					<!-- footer modal -->
					<div class="modal-footer" style="background-color: orange">
						<button type="button" class="btn btn-default btn-block"
							data-dismiss="modal">ANNULER</button>
						<button type="submit" class="btn btn-default btn-block">ACCEPT</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
