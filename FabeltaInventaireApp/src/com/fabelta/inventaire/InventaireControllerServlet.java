package com.fabelta.inventaire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet implementation class InventaireControllerServlet
 */
@WebServlet("/InventaireControllerServlet")
public class InventaireControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private InventaireDbUtil inventaireDbUtil;

	// Define datasource/connection pool for Resource Injection
	@Resource(name = "jdbc/web_inventaire_fabelta")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		// creer inventaire db util ... et le passer a la connection pool / datasource
		try {
			inventaireDbUtil = new InventaireDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			// lire le parametre "command"
			String command = req.getParameter("command");

			switch (command) {
			case "LOGIN":
				login(req, resp);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	// function pour gerer le login de chaque utilisateur, si valde redirection vers
	// main sinon rester dans la meme page
	private void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String username = req.getParameter("username");

		String password = req.getParameter("password");

		Boolean exist = inventaireDbUtil.getLogin(username, password);

		if (exist == true) {
			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = req.getRequestDispatcher("/main.jsp");
			dispatcher.forward(req, resp);
		} else if (exist == false) {
			int wrong = 0;
			req.setAttribute("WRONGPASSWORD", wrong);
			// avoir les utilisateurs de la bd
			List<UtilisateurInventaire> utilisateursInventaire = inventaireDbUtil.getUSers();

			// ajouter les utilisateurs a la request
			req.setAttribute("USERS", utilisateursInventaire);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
			dispatcher.forward(req, resp);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// lire le parametre "command"
			String command = request.getParameter("command");

			// si le parametre "command" existe pas, aller au debut
			if (command == null) {
				command = "LIST";
			}

			// envoyer au bon methode
			switch (command) {
			case "LIST":
				listUsers(request, response);
				break;

			case "SEARCHPRODUCT":
				searchProduct(request, response);
				break;

			case "SEARCHZONE":
				searchzone(request, response);
				break;

			case "RECEPTIONPRODUITCODE":
				searchCodeReception(request, response);
				break;

			case "RECEPTIONTOUT":
				receptionAllProductScanned(request, response);
				break;

			case "RECEPTIONSPLIT":
				receptionSplitProductScanned(request, response);
				break;

			case "ENVOIENPRODUCTIONCODE":
				searchCodeEnvoiEnProduction(request, response);
				break;

			case "ENVOIENPRODUCTION":
				sendProduitToProduction(request, response);
				break;

			case "AJUSTEMENT":
				adjustProduct(request, response);
				break;

			case "DEPLACEMENTCODE":
				searchCodeDisplacement(request, response);
				break;

			case "ENVOIPRODUCTDEPLACEMENT":
				productDisplacement(request, response);
				break;

			case "PRODUITENRECEPTION":
				productInReception(request, response);
				break;

			case "PRDUITENRECEPTION":
				envoiEnReception(request, response);
				break;

			case "ENVOIENPEINTURECODE":
				searchCodeEnvoiEnPeinture(request, response);
				break;

			case "ENVOIENPEINTURE":
				sendProductToPainting(request, response);
				break;

			case "COMMENTAIRE":
				showComment(request, response);
				break;

			case "EDITCOMMENT":
				editComment(request, response);
				break;

			case "RAPPORTINVENTAIRE":
				rapportInventaire(request, response);
				break;

			case "RAPPORTHISTORIC":
				rapporHistorique(request, response);
				break;

			case "ENVOIENSCRAP":
				sendProductToScrap(request, response);
				break;

			case "ENVOIENSCRAPCODE":
				productToScrap(request, response);
				break;

			default:
				listUsers(request, response);
				break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void productToScrap(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all the data recived from the front end
		// product code
		String productSendToScrap = request.getParameter("codeProdEnvoiEnScrap");
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(productSendToScrap);
		UUID uuidProduct = productInventaire.get(0).getId();

		// zone code
		String zoneADeplcer = request.getParameter("zoneCode");
		List<LocationInventaire> locationInventaire = inventaireDbUtil.searchZone(zoneADeplcer);
		UUID uuidZone = locationInventaire.get(0).getId();

		// zone code scrap
		String zoneScrap = "SCRAP-001";
		List<LocationInventaire> locationInventaireScrap = inventaireDbUtil.searchZone(zoneScrap);
		UUID uuidZoneScrap = locationInventaireScrap.get(0).getId();

		// user id
		String userId = request.getParameter("idUser");

		// quantite a traiter
		String quantity = request.getParameter("quantiteADeduire");
		int newQuantity = Integer.parseInt(quantity);

		// other data
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// data pour envoyer en historique
		// operation
		String operation = "PRODUIT ENVOYE A SCRAP";
		// operation recu en scrap
		String operationScrap = "PRODUIT EN SCRAP";

		// success
		Boolean success = Boolean.TRUE;

		// ajouter (enlever de la zone) ligne dans la table
		// productlocationfabeltahistroic
		inventaireDbUtil.setInHistoric(uuidProduct, uuidZone, -newQuantity, timestamp, userId, operation, success);

		// ajouter (ajouter a la zone scrap) ligne dans la table productlocationhistoric
		inventaireDbUtil.setInHistoric(uuidProduct, uuidZoneScrap, newQuantity, timestamp, userId, operationScrap,
				success);

		// mettre a jour la table productlocation
		inventaireDbUtil.setNewTotalScannedEnlever(uuidProduct, uuidZone, newQuantity, timestamp, userId);

		// renvoyer donnees a jour a la page envoi en scrap
		// code - description porduit
		List<ProductInventaire> productInventaires = inventaireDbUtil.searchProduit(productSendToScrap);
		// zone - quantite
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(productSendToScrap);

		request.setAttribute("PRODUITFROMENVOIENSCRAP", productInventaires);
		request.setAttribute("PRODUITZONEFROMENVOISCRAP", locationProduit);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_scrap.jsp");
		dispatcher.forward(request, response);

	}

	private void sendProductToScrap(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get data from frontend (url)
		String codeProduct = request.getParameter("codeProdEnScrap");

		// get information du produit
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(codeProduct);

		// information productlocation
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduct);

		// si el produit n'existe pas envoyer message au frontend
		if (productInventaire.isEmpty()) {

			int notFound = 0;
			request.setAttribute("NONTROUVE", notFound);
			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_scrap.jsp");
			dispatcher.forward(request, response);

		} else {

			request.setAttribute("PRODUITFROMENVOIENSCRAP", productInventaire);
			request.setAttribute("PRODUITZONEFROMENVOISCRAP", locationProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_scrap.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void rapporHistorique(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get data from url
		String dateDe = request.getParameter("dateDe");
		String dateA = request.getParameter("dateA");

		// data pour remplir la feuille excel
		List<HistoricRapport> list = inventaireDbUtil.getRapportFullHistoric(dateDe, dateA);

		// entete du feuille excel
		String[] columns = { "PRODUCT", "LOCATION", "QUANTITE", "OPERATION", "DATE", "USER" };

		Workbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// create the sheet
		Sheet sheet = workbook.createSheet("Historique");

		// creer lentete
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		// creer le cellstyle
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// create row
		Row headerRow = sheet.createRow(0);

		// create lentete
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// create cell style for formatting date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 1;

		// create rows with all the data from list
		for (HistoricRapport historicRapport : list) {

			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(historicRapport.getProductNameHistoric());
			row.createCell(1).setCellValue(historicRapport.getLocationNameHistoric());
			row.createCell(2).setCellValue(historicRapport.getQuantityHistoric());
			row.createCell(3).setCellValue(historicRapport.getOperation());
			// date
			Cell date = row.createCell(4);
			date.setCellValue(historicRapport.getDateHistoric());
			date.setCellStyle(dateCellStyle);

			row.createCell(5).setCellValue(historicRapport.getUserNameHistoric());

		}

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// date pour l'ajouter au nom du fichier excel
		LocalDate date = LocalDate.now();

		FileOutputStream fileOutputStream = new FileOutputStream("Historic" + date + ".xlsx");
		workbook.write(fileOutputStream);

		workbook.close();

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/page_administration.jsp");
		dispatcher.forward(request, response);
	}

	private void rapportInventaire(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// data avec laquel on va remplir la feuille excel
		List<InventaireRapport> list = inventaireDbUtil.getRapportFullInventry();

		// entete du excel
		String[] columns = { "PRODUCT", "ZONE", "QUANTITE", "COMMENTAIRE" };

		Workbook workbook = new XSSFWorkbook();

		// create a sheet
		Sheet sheet = workbook.createSheet("Inventaire");

		// create a font for styling header cells
		org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// create a cellstyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// create the row
		Row headerRow = sheet.createRow(0);

		// create the cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;

		for (InventaireRapport inventaireRapport : list) {

			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(inventaireRapport.getProductName());
			row.createCell(1).setCellValue(inventaireRapport.getProductZone());
			row.createCell(2).setCellValue(inventaireRapport.getQuantity());
			row.createCell(3).setCellValue(inventaireRapport.getCommentaire());

		}

		// resize all columns
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		FileOutputStream fileOut = new FileOutputStream("Inventaire.xlsx");
		workbook.write(fileOut);

		workbook.close();

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/page_administration.jsp");
		dispatcher.forward(request, response);

	}

	private void editComment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all the information from frontend
		// product code
		String productCode = request.getParameter("produitCode");
		List<ProductInventaire> productCodeFromUrl = inventaireDbUtil.searchProduit(productCode);
		UUID uuidProduct = productCodeFromUrl.get(0).getId();

		// zone code
		String zoneCode = request.getParameter("zoneCode");
		List<LocationInventaire> zoneCodeFromUrl = inventaireDbUtil.searchZone(zoneCode);
		UUID uuidZone = zoneCodeFromUrl.get(0).getId();

		// commenatire ajoute ou edite
		String commentaireAEditer = request.getParameter("commentTochange");

		inventaireDbUtil.editCommentFromProductLocaion(uuidProduct, uuidZone, commentaireAEditer);

		String commentaire = inventaireDbUtil.getCommentFromProductLocation(uuidProduct, uuidZone);

		request.setAttribute("COMMENTAIRE", commentaire);
		request.setAttribute("PRODCODE", productCode);
		request.setAttribute("ZONECODE", zoneCode);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/information_produit.jsp");
		dispatcher.forward(request, response);

	}

	private void showComment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all the information from frontend
		// product code
		String productCode = request.getParameter("produitCode");
		List<ProductInventaire> productCodeFromUrl = inventaireDbUtil.searchProduit(productCode);
		UUID uuidProduct = productCodeFromUrl.get(0).getId();

		// zone code
		String zoneCode = request.getParameter("zoneCode1");
		List<LocationInventaire> zoneCodeFromUrl = inventaireDbUtil.searchZone(zoneCode);
		UUID uuidZone = zoneCodeFromUrl.get(0).getId();

		String commentaire = inventaireDbUtil.getCommentFromProductLocation(uuidProduct, uuidZone);

		request.setAttribute("COMMENTAIRE", commentaire);
		request.setAttribute("PRODCODE", productCode);
		request.setAttribute("ZONECODE", zoneCode);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/information_produit.jsp");
		dispatcher.forward(request, response);
	}

	private void sendProductToPainting(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all the data received from the front end
		// product code
		String productToSendToPainting = request.getParameter("codeProdEnvoiEnPeinture");
		List<ProductInventaire> productInventaireFromUrl = inventaireDbUtil.searchProduit(productToSendToPainting);
		UUID uuidProduct = productInventaireFromUrl.get(0).getId();

		// zone code
		String zoneADeplacer = request.getParameter("zoneProduitAPeinture");
		List<LocationInventaire> locationInventaireFromUrl = inventaireDbUtil.searchZone(zoneADeplacer);
		UUID uuidZone = locationInventaireFromUrl.get(0).getId();

		// zone peinture
		String zonePeinture = "PEINT-001";
		List<LocationInventaire> locationInventairePeinture = inventaireDbUtil.searchZone(zonePeinture);
		UUID uuidZoneProduction = locationInventairePeinture.get(0).getId();

		// user id
		String userId = request.getParameter("idUser");

		// quantity
		String quantity = request.getParameter("quantiteEnvoyerEnPeinture");
		int newQuantity = Integer.parseInt(quantity);

		// other data
		// date
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// data pour envoyer dans l'historique
		// operation
		String operation = "PRODUIT ENVOYE EN PEINTURE";
		String operaitonEnPeinture = "PRODUIT RECU EN PEINTURE";
		// succes
		Boolean success = Boolean.TRUE;

		// ajouter ligne en historique avec le produit enleve de la zone
		inventaireDbUtil.setInHistoric(uuidProduct, uuidZone, -newQuantity, timestamp, userId, operation, success);

		// ajouter ligne en histrique avec le produit ajoute a la zone peinture
		inventaireDbUtil.setInHistoric(uuidProduct, uuidZoneProduction, newQuantity, timestamp, userId,
				operaitonEnPeinture, success);

		// ajouter ligne dans productlocation
		inventaireDbUtil.setNewTotalScannedEnlever(uuidProduct, uuidZone, newQuantity, timestamp, userId);

		// renvoyer toutes les donnees a jour a la page envoi en peinture
		// code -description du produit
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(productToSendToPainting);
		// zone - quantite du produit
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(productToSendToPainting);

		request.setAttribute("PRODUITFROMENVOIENPEINTURE", productInventaire);
		request.setAttribute("PRODUITZONEENVOIENPEINTURE", locationProduit);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_peinture.jsp");
		dispatcher.forward(request, response);
	}

	private void searchCodeEnvoiEnPeinture(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get the data from the front end
		String codeProduit = request.getParameter("codePorductEnvoiEnPeinture");

		// information du produit
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(codeProduit);

		// information from productlocationfabelta
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduit);

		if (productInventaire.isEmpty()) {
			int notFound = 0;
			request.setAttribute("NONTROUVE", notFound);
			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_peinture.jsp");
			dispatcher.forward(request, response);

		} else {

			request.setAttribute("PRODUITFROMENVOIENPEINTURE", productInventaire);
			request.setAttribute("PRODUITZONEENVOIENPEINTURE", locationProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_peinture.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void envoiEnReception(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all data from url
		// user
		String userId = request.getParameter("IdUser");
		// parsing the user id to UUID
		UUID uuidUser = UUID.fromString(userId);

		// produit
		String prodCode = request.getParameter("prodCodeAEnvoyer");
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(prodCode);
		UUID uuidProduct = productInventaire.get(0).getId();

		// quantite
		String quantite = request.getParameter("prodQtyAEnvoyer");
		int newQuantie = Integer.parseInt(quantite);

		// dateq d
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// operation
		String operation = "SAUVEGARDE EN RECEPTION";

		// success
		Boolean success = Boolean.TRUE;

		// reception
		String reception = "ENT-REC-000";
		List<LocationInventaire> locationInventaire = inventaireDbUtil.searchZone(reception);
		UUID uuidLocation = locationInventaire.get(0).getId();

		Boolean exist = inventaireDbUtil.zoneExist(uuidProduct, uuidLocation);

		if (exist) {
			inventaireDbUtil.setNewTotalScannedAjout(uuidProduct, uuidLocation, newQuantie, timestamp, uuidUser);
		} else {
			inventaireDbUtil.setNewZoneProduit(uuidProduct, uuidLocation, newQuantie, timestamp, uuidUser);

		}
		inventaireDbUtil.setInHistoric(uuidProduct, uuidLocation, newQuantie, timestamp, userId, operation, success);

		// montrer page principale(jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
		dispatcher.forward(request, response);
	}

	// afficher toutes les produits dans ENT-REC-000 (Reception)
	private void productInReception(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// zone reception
		String zoneCodeReception = "ENT-REC-000";

		LocationInventaire locationInventaire = inventaireDbUtil.searchLocationByCode(zoneCodeReception);

		UUID uuidLocation = locationInventaire.getId();

		inventaireDbUtil.erraseRowWhenIsZero(uuidLocation);

		HashMap<String, Integer> prodEnReception = inventaireDbUtil.searchCodeProduit(zoneCodeReception);

		// ajouiter le produit dans la requete
		request.setAttribute("PRODUITENRECEPTION", prodEnReception);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/produits_en_reception.jsp");
		dispatcher.forward(request, response);

	}

	//// function pour deplacer le produit
	private void productDisplacement(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all the data received from the back-end
		// user
		String userId = request.getParameter("idUser");
		UUID uidUser = UUID.fromString(userId);

		// code produit a deplacer
		String codeProduitADeplacer = request.getParameter("codeProdADeplacer");
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(codeProduitADeplacer);
		UUID uidProduct = productInventaire.get(0).getId();

		// quantite a deplacer
		String quantiteRecu = request.getParameter("quantiteADeplacer");
		int nouvelleQuantite = Integer.parseInt(quantiteRecu);

		// code zone ou on va enlever la quantite
		String zoneDeplacement = request.getParameter("zoneProduitDe");
		List<LocationInventaire> locationInventaire = inventaireDbUtil.searchZone(zoneDeplacement);
		UUID uidZoneDeplacement = locationInventaire.get(0).getId();

		// code zone ou on va ajouter la quantite
		String zoneADeplacer = request.getParameter("zoneProduitA");
		List<LocationInventaire> locationInventaire2 = inventaireDbUtil.searchZone(zoneADeplacer);

		if (locationInventaire2.isEmpty()) {
			// envoyer a la vue page d'erreur(jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/erreur_en_reception.jsp");
			dispatcher.forward(request, response);
		} else {

			UUID uidZoneADeplacer = locationInventaire2.get(0).getId();

			// information pour l'historique
			// operation
			String operationA = "DEPLACEMENT A";
			String operationDe = "DEPLACEMENT DE";

			// success
			Boolean success = Boolean.TRUE;

			// get la date et l heure current
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

			// traitement des donnes
			// function pour voir si la zone a deplacer existe deja avec produit dans la
			// table productlocationfabelta
			Boolean zoneExist = inventaireDbUtil.zoneExist(uidProduct, uidZoneADeplacer);

			// si le product/zone existe deja
			if (zoneExist == true) {
				// on met a jour la table productlocation en ajoutant la quantite a la nouvelle
				// zone
				inventaireDbUtil.setNewTotalScannedAjout(uidProduct, uidZoneADeplacer, nouvelleQuantite, timestamp,
						uidUser);
				// rajoute dans l'historique
				inventaireDbUtil.setInHistoric(uidProduct, uidZoneADeplacer, nouvelleQuantite, timestamp, userId,
						operationA, success);

				// on mets a jour la table productlocation en enlevant la quantite de lancienne
				// zone
				inventaireDbUtil.setNewTotalScannedEnlever(uidProduct, uidZoneDeplacement, nouvelleQuantite, timestamp,
						userId);
				// rajoute dans l'historique
				inventaireDbUtil.setInHistoric(uidProduct, uidZoneDeplacement, -nouvelleQuantite, timestamp, userId,
						operationDe, success);

				// si le product/zone existe pas dans la table productlocation
			} else {
				// on rajout la nouvelle ligne avec le product et la nouvelle zone
				inventaireDbUtil.setNewZoneProduit(uidProduct, uidZoneADeplacer, nouvelleQuantite, timestamp, uidUser);

				// rajoute dans l'historique
				inventaireDbUtil.setInHistoric(uidProduct, uidZoneADeplacer, nouvelleQuantite, timestamp, userId,
						operationA, success);

				// on mets a jour la table productlocation en enlevant la quantite de lancienne
				// zone
				inventaireDbUtil.setNewTotalScannedEnlever(uidProduct, uidZoneDeplacement, nouvelleQuantite, timestamp,
						userId);
				// rajoute dans l'historique
				inventaireDbUtil.setInHistoric(uidProduct, uidZoneDeplacement, -nouvelleQuantite, timestamp, userId,
						operationDe, success);

			}

			// mettre a jour toutes les donnes et la page deplacement

			// information du prduit
			List<ProductInventaire> productInventaire1 = inventaireDbUtil.searchProduit(codeProduitADeplacer);

			// information table locationproduit zone-quantite
			HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduitADeplacer);

			request.setAttribute("PRODUITADEPLACER", productInventaire1);
			request.setAttribute("PRODUCTZONEAPLACER", locationProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/deplacement.jsp");
			dispatcher.forward(request, response);
		}
	}

	// function pour rechercher le code du produit pour le deplacement
	private void searchCodeDisplacement(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// code du produit recu de l'input scannez un produit de deplacez un produit
		String codeProduitADeplacer = request.getParameter("codeProdDeplacement");

		// information du prduit
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(codeProduitADeplacer);

		// information table locationproduit zone-quantite
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduitADeplacer);

		// si le product existe pas envoyer message au frontend
		if (productInventaire.isEmpty()) {
			int notFound = 0;
			request.setAttribute("NONTROUVE", notFound);
			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/deplacement.jsp");
			dispatcher.forward(request, response);

		} else {

			request.setAttribute("PRODUITADEPLACER", productInventaire);
			request.setAttribute("PRODUCTZONEAPLACER", locationProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/deplacement.jsp");
			dispatcher.forward(request, response);
		}
	}

	// fonction pour ajuster la quantite de un prouit
	private void adjustProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all the data received from the backend
		// Product
		String productAAjuster = request.getParameter("idProdAAjuster");
		List<ProductInventaire> productCodeFromUrl = inventaireDbUtil.searchProduit(productAAjuster);
		UUID uidProduct = productCodeFromUrl.get(0).getId();

		// Zone
		String zoneAAjuster = request.getParameter("codeZoneAAjuster");
		List<LocationInventaire> zoneCodeFromUrl = inventaireDbUtil.searchZone(zoneAAjuster);
		UUID uidZone = zoneCodeFromUrl.get(0).getId();

		// User
		String userId = request.getParameter("idUser");
		// parsing the user id to UUID
		UUID uidUser = UUID.fromString(userId);

		// Quantite
		String quantiteAAjuster = request.getParameter("quantiteAAjuster");
		int newQuantite = Integer.parseInt(quantiteAAjuster);

		// other data
		// date
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// data pour remplir la table historique
		// operation
		String operation = "";

		if (newQuantite > 0) {
			operation = "AJUSTEMENT(QUANTITE AJOUTE AU PRODUIT)";
		} else {
			operation = "AJUSTEMENT(QUANTITE RETIRE DU PRODUIT)";
		}

		// success
		Boolean success = Boolean.TRUE;

		// requete pour update la nouvelle quantite en productlocationfabelta
		inventaireDbUtil.setNewTotalScannedAjout(uidProduct, uidZone, newQuantite, timestamp, uidUser);

		// requete pour remplir la table historic
		inventaireDbUtil.setInHistoric(uidProduct, uidZone, newQuantite, timestamp, userId, operation, success);

		// on check si on esta dans la recherche d un location ou de un produit
		String localisation = request.getParameter("zone");

		if (localisation.equals("rechercherZone")) {

			List<LocationInventaire> locationInventaire = inventaireDbUtil.searchZone(zoneAAjuster);
			HashMap<String, Integer> codeProduit = inventaireDbUtil.searchCodeProduit(zoneAAjuster);

			request.setAttribute("ZONE", locationInventaire);
			request.setAttribute("PRODUITLOCATIONINZONE", codeProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/recherche_zone.jsp");
			dispatcher.forward(request, response);

		} else {
			// mettre a jour les information pour les remettre dans la vue

			List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(productAAjuster);
			HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(productAAjuster);

			request.setAttribute("PRODUIT", productInventaire);
			request.setAttribute("PRODUITLOCATION", locationProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/recherche_produit.jsp");
			dispatcher.forward(request, response);
		}

	}

	// fonction pour envoyer le produit en production
	private void sendProduitToProduction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get all the data recived from the url
		// product code
		String produtToSendToProduction = request.getParameter("codeProdEnvoiEnProd");
		List<ProductInventaire> productInventaireFromUrl = inventaireDbUtil.searchProduit(produtToSendToProduction);
		UUID uidProduct = productInventaireFromUrl.get(0).getId();

		// zone code
		String zoneSendToProduction = request.getParameter("zoneProduit");
		List<LocationInventaire> locationInventaireFromUrl = inventaireDbUtil.searchZone(zoneSendToProduction);
		UUID uidZone = locationInventaireFromUrl.get(0).getId();

		// zone production
		String zoneProduction = "PROD-001";
		List<LocationInventaire> locationInventaireProduction = inventaireDbUtil.searchZone(zoneProduction);
		UUID uuidZoneProduction = locationInventaireProduction.get(0).getId();

		// user id
		String userId = request.getParameter("idUser");

		// quantity
		String quantity = request.getParameter("quantiteEnvoyerEnProduction");
		int qty = Integer.parseInt(quantity);
		int qtyEnNegatif = -qty;

		// other data
		// date
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// data pour envoyer dans l'historique
		// operation
		String operation = "PRODUIT ENVOYE EN PRODUCTION";
		String operaitonEnProd = "PRODUIT RECU EN PRODUCTION";

		// success
		Boolean success = Boolean.TRUE;

		// mettre ajour la table product location historic fabelta (on mets en negatif
		// la quantite qu'on a enleve de la zone)
		inventaireDbUtil.setInHistoric(uidProduct, uidZone, qtyEnNegatif, timestamp, userId, operation, success);

		// mettre a jour la table product location historic fabelta (on mets en positif
		// la quantite qu'on ajoute a la production)
		inventaireDbUtil.setInHistoric(uidProduct, uuidZoneProduction, qty, timestamp, userId, operaitonEnProd,
				success);

		// mettre a jour la table product location fabelta
		inventaireDbUtil.setNewTotalScannedEnlever(uidProduct, uidZone, qty, timestamp, userId);

		// renvoi des donnees a jour--------------------
		// code du produit recu depuis l'input dans le modal envoi en prod (par le url)
		String codeProduit = request.getParameter("codeProdEnvoiEnProd");

		// code -description du produit
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(codeProduit);
		// zone - quantite du produit
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduit);

		request.setAttribute("PRODUITFROMENVOIENPROD", productInventaire);
		request.setAttribute("PRODUCTZONEENVOIENPROD", locationProduit);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_production.jsp");
		dispatcher.forward(request, response);

	}

	// function pour rechercher le code mis dans le modal envoi en production
	private void searchCodeEnvoiEnProduction(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// code du produit recu depuis l'input dans le modal envoi en prod (par le url)
		String codeProduit = request.getParameter("codePorductEnvoiEnProd");

		// code -description du produit
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(codeProduit);
		// zone - quantite du produit
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduit);

		// si el produit existe pas envoyer message au frontend
		if (productInventaire.isEmpty()) {

			int notFound = 0;
			request.setAttribute("NONTROUVE", notFound);
			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_production.jsp");
			dispatcher.forward(request, response);

		} else {

			request.setAttribute("PRODUITFROMENVOIENPROD", productInventaire);
			request.setAttribute("PRODUCTZONEENVOIENPROD", locationProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/envoi_en_production.jsp");
			dispatcher.forward(request, response);
		}
	}

	// function pour separer la quantite du produit scanne en reception
	private void receptionSplitProductScanned(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get all the data from url
		String codeProduitSplit = request.getParameter("codeProdSplit");
		String zoneProduitSplit = request.getParameter("zoneProdSplit");

		// prendre le id de lutilisateur
		String userId = request.getParameter("IdUser");

		// la quantite a enlever de la bd
		String quantiteProduitSplit = request.getParameter("qtyProdSplit");

		// la quantite total du produit
		String quantiteProduitTotal = request.getParameter("quantiteTotal");

		// get la date et l heure current
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// parsing la quantite en int
		int quantiteRecu = Integer.parseInt(quantiteProduitSplit);
		int quantiteTotal = Integer.parseInt(quantiteProduitTotal);

		// get information from product and zone
		List<ProductInventaire> produitAChangerSplit = inventaireDbUtil.searchProduit(codeProduitSplit);
		List<LocationInventaire> locationAChangerSplit = inventaireDbUtil.searchZone(zoneProduitSplit);

		if (locationAChangerSplit.isEmpty()) {
			// envoyer a la vue page d'erreur(jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/erreur_en_reception.jsp");
			dispatcher.forward(request, response);
		} else {

			// get the UUID from product and zone
			UUID uidProductSplit = produitAChangerSplit.get(0).getId();
			UUID uidLocationSplit = locationAChangerSplit.get(0).getId();

			// parsing the user id to UUID
			UUID uidUser = UUID.fromString(userId);

			// information pour l'historique
			// operation
			String operation = "PRODUIT RECU ET PLACE";

			String operationEnvoyeDeReception = "PRODUIT PLACE: ENLEVE DE RECEPTION";

			// success
			Boolean success = Boolean.TRUE;

			// check if the request comes from reception
			String isFromReception = request.getParameter("isFromRec");

			// get the uuid from reception
			List<LocationInventaire> locationReception = inventaireDbUtil.searchZone("ENT-REC-000");
			UUID uidLocationReception = locationReception.get(0).getId();

			// get le paramettre "Scanned" pour differencer si un zone a ete scanne ou deja
			// dans la liste
			String typeReception = request.getParameter("Scanned");

			switch (typeReception) {
			// si le produit a ete scanne dans l'input de reception
			case "OUI":
				// si la requete viens direct du "Reception"
				if (isFromReception.equals("non")) {
					inventaireDbUtil.setNewZoneProduit(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp,
							uidUser);
					inventaireDbUtil.setInHistoric(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp, userId,
							operation, success);
				} else {
					// si la requete viens du "Produits en Reception" ajouter le produit - zone dans
					// productlocation et le placer dans l'historiqe
					inventaireDbUtil.setNewZoneProduit(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp,
							uidUser);
					inventaireDbUtil.setInHistoric(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp, userId,
							operation, success);
					// enlever la quantite de la zone "ENT-REC-000" et le placer dans l'historique
					inventaireDbUtil.setNewTotalScannedEnlever(uidProductSplit, uidLocationReception, quantiteRecu,
							timestamp, userId);
					inventaireDbUtil.setInHistoric(uidProductSplit, uidLocationReception, quantiteRecu, timestamp,
							userId, operationEnvoyeDeReception, success);
				}
				break;
			// si leproduit a ete choisi dans la liste de reception
			case "NON":
				// si la requete viens direc du "Reception"
				if (isFromReception.equals("non")) {
					inventaireDbUtil.setNewTotalScannedAjout(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp,
							uidUser);
					inventaireDbUtil.setInHistoric(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp, userId,
							operation, success);
				} else {
					inventaireDbUtil.setNewTotalScannedAjout(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp,
							uidUser);
					inventaireDbUtil.setInHistoric(uidProductSplit, uidLocationSplit, quantiteRecu, timestamp, userId,
							operation, success);
					// enlever la quantite de la zone "ENT-REC-000" et le placer dans l'historique
					inventaireDbUtil.setNewTotalScannedEnlever(uidProductSplit, uidLocationReception, quantiteRecu,
							timestamp, userId);
					inventaireDbUtil.setInHistoric(uidProductSplit, uidLocationReception, quantiteRecu, timestamp,
							userId, operationEnvoyeDeReception, success);
				}
				break;

			default:
				break;
			}

			// get les zones du produit
			HashMap<String, Integer> locationsProduit = inventaireDbUtil.searchLocationProduit(codeProduitSplit);

			// on set a nouveau les informations pour les montrer deja a jour dans lecran
			// produit
			request.setAttribute("PRODUITFROMRECEPTION", produitAChangerSplit);
			request.setAttribute("PRODUITZONEFROMERECEPTION", locationsProduit);

			// traitement de la quantite pour la mettre a jour dans le total
			// variable pour stocker la nouvelle quantite a envoyer
			int nouvelleQuantite = quantiteTotal - quantiteRecu;
			// parser en string
			String quantiteAJour = Integer.toString(nouvelleQuantite);

			// mettre la quantite a jour
			request.setAttribute("QUANTITE", quantiteAJour);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/reception.jsp");
			dispatcher.forward(request, response);
		}
	}

	// traiter linformation et update la quantite d un produit en reception
	// function pour les boutons "TOUT" de Reception
	private void receptionAllProductScanned(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// prendre tout le data venant du url
		String codeProduit = request.getParameter("codeProd");
		String quantiteProduit = request.getParameter("qtyProd");
		String zoneProduit = request.getParameter("zoneProd");

		// prendre le id de lutilisateur
		String userId = request.getParameter("IdUser");

		// get la date et l heure current
		java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

		// parsing la quantite a int
		int qty = Integer.parseInt(quantiteProduit);

		// get les information du produit et de la zone
		List<ProductInventaire> produitAChanger = inventaireDbUtil.searchProduit(codeProduit);
		List<LocationInventaire> locationAChanger = inventaireDbUtil.searchZone(zoneProduit);

		// si la zone saisie es pas correct ou intruvable on rerempli la page reception
		if (locationAChanger.isEmpty()) {

			// envoyer a la vue page d'erreur(jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/erreur_en_reception.jsp");
			dispatcher.forward(request, response);

		} else {
			// get les UUID du produit et de la zone
			UUID uidProduct = produitAChanger.get(0).getId();
			UUID uidLocation = locationAChanger.get(0).getId();

			// parsing the user id to UUID
			UUID uidUser = UUID.fromString(userId);

			// information pour l'historique
			// operation
			String operation = "PRODUIT RECU ET PLACE";

			String operationEnvoyeDeReception = "PRODUIT PLACE: ENLEVE DE RECEPTION";

			// success
			Boolean success = Boolean.TRUE;

			// chek if the request comes from a reception
			String isFromReception = request.getParameter("isFromRec");
			List<LocationInventaire> locationReception = inventaireDbUtil.searchZone("ENT-REC-000");
			UUID uidLocationReception = locationReception.get(0).getId();

			// get te paramettre scanned pour savoir si c'etait scanne ou si etait dans la
			// liste
			String typeReception = request.getParameter("Scanned");

			switch (typeReception) {
			// si le produit a ete scanne dans l'input de reception
			case "OUI":
				// si la requete viens direct du "Reception"
				if (isFromReception.equals("non")) {
					inventaireDbUtil.setNewZoneProduit(uidProduct, uidLocation, qty, timestamp, uidUser);
					inventaireDbUtil.setInHistoric(uidProduct, uidLocation, qty, timestamp, userId, operation, success);
				} else {
					// si la requete vies du "Produits en reception" ajouter le produit - zone dans
					// productlocation et le placer dans l'historique
					inventaireDbUtil.setNewZoneProduit(uidProduct, uidLocation, qty, timestamp, uidUser);
					inventaireDbUtil.setInHistoric(uidProduct, uidLocation, qty, timestamp, userId, operation, success);
					// enlever la quantite de la zone "ENT-REC-000" et le placer dans l'historique
					inventaireDbUtil.setNewTotalScannedEnlever(uidProduct, uidLocationReception, qty, timestamp,
							userId);
					inventaireDbUtil.setInHistoric(uidProduct, uidLocationReception, qty, timestamp, userId,
							operationEnvoyeDeReception, success);
				}
				break;
			// si le produit a ete choisi dans la liste de reception
			case "NON":
				// methode pour faire la requete update
				if (isFromReception.equals("non")) {
					inventaireDbUtil.setNewTotalScannedAjout(uidProduct, uidLocation, qty, timestamp, uidUser);
					inventaireDbUtil.setInHistoric(uidProduct, uidLocation, qty, timestamp, userId, operation, success);
					// si le produit viens de produit en reception
				} else {
					// ajout dans la zone si produit viens de produis en reception
					inventaireDbUtil.setNewTotalScannedAjout(uidProduct, uidLocation, qty, timestamp, uidUser);
					inventaireDbUtil.setInHistoric(uidProduct, uidLocation, qty, timestamp, userId, operation, success);
					// enlever la quantite das la zone reception
					inventaireDbUtil.setNewTotalScannedEnlever(uidProduct, uidLocationReception, qty, timestamp,
							userId);
					inventaireDbUtil.setInHistoric(uidProduct, uidLocationReception, qty, timestamp, userId,
							operationEnvoyeDeReception, success);
				}
				break;

			default:
				break;
			}

			// get les zone du produit
			HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduit);

			// on set a nouveau les information pour les montrer deja a jour dans lecran du
			// produit
			request.setAttribute("PRODUITFROMRECEPTION", produitAChanger);
			request.setAttribute("PRODUITZONEFROMERECEPTION", locationProduit);

			// on passe a 0 la quantite a montrer a lutilisateur
			qty = qty - qty;
			String quantiteProduitAZero = Integer.toString(qty);

			request.setAttribute("QUANTITE", quantiteProduitAZero);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/reception.jsp");
			dispatcher.forward(request, response);
		}
	}

	// get tout linformation d un produit pour le montrer en reception
	// search code en modal reception
	private void searchCodeReception(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// declaration pour stocker la quantite du produit
		String produitQuantite = null;

		// info recu de linput
		String receptionData = request.getParameter("codePorductReception");

		// separer les string sil y a des spaces
		String[] splitStringReception = receptionData.split("\\s+");

		// setter dans la variable le code du produit recu du url
		String codeProduitARechercher = splitStringReception[0];

		// verifier si le string a ou non la quantite
		if (splitStringReception.length == 1) {
			produitQuantite = "0";
		} else {
			produitQuantite = splitStringReception[1];
		}

		// code - descrption du produit
		// methode pour rechercher product inventaire du db util
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(codeProduitARechercher);
		// zone -quantite du produit
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(codeProduitARechercher);

		HashMap<String, Integer> hashMap = new HashMap<>();

		// envoyer tout sauf la liste en reception
		for (Map.Entry<String, Integer> entry : locationProduit.entrySet()) {

			if (!entry.getKey().equals("ENT-REC-000")) {
				hashMap.put(entry.getKey(), entry.getValue());
			}
		}

		// reception par default
		HashMap<String, Integer> reception = new HashMap<>();

		// get the uuid de reception
		String receptionCode = "ENT-REC-000";
		List<LocationInventaire> locationInventaires = inventaireDbUtil.searchZone(receptionCode);
		UUID uuidReception = locationInventaires.get(0).getId();

		// get the uuid produit
		List<ProductInventaire> productInventaires = inventaireDbUtil.searchProduit(codeProduitARechercher);

		// si le produit recherche existe pas retourner a la vue
		if (productInventaire.isEmpty()) {
			request.setAttribute("PRODENRECNONEXISTE", 0);
			// si le produit existe
		} else {

			UUID uuidProduit = productInventaires.get(0).getId();

			// voir sil existe
			Boolean prodExist = inventaireDbUtil.zoneExist(uuidProduit, uuidReception);

			if (prodExist) {
				int quantiteDansZone = inventaireDbUtil.getQuantityFromProductLocation(uuidProduit, uuidReception);

				reception.put(receptionCode, quantiteDansZone);
			} else {
				reception.put(receptionCode, 0);
			}
		}

		// information reception
		request.setAttribute("RECEPTIONINFO", reception);

		request.setAttribute("PRODUITFROMRECEPTION", productInventaire);
		request.setAttribute("PRODUITZONEFROMERECEPTION", hashMap);
		request.setAttribute("QUANTITE", produitQuantite);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/reception.jsp");
		dispatcher.forward(request, response);
	}

	// methode pour rechercher information de la zone
	// search une zone
	private void searchzone(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// lire le code a rechercher du form (input)
		String zoneRecherche = request.getParameter("produitZone");

		// code - traitement
		// methode pour search le location inventaire from db util
		List<LocationInventaire> locationInventaire = inventaireDbUtil.searchZone(zoneRecherche);

		if (locationInventaire.isEmpty()) {
			System.out.println("does not exist");
			int notFound = 0;
			request.setAttribute("NONTROUVE", notFound);
		} else {
			UUID uuidLocation = locationInventaire.get(0).getId();
			List<ProductLocationInventaire> productLocationInventaires = inventaireDbUtil
					.getProductLocationByZoneId(uuidLocation);

			request.setAttribute("PRODUCTLOCATION", productLocationInventaires);
		}

		// ajouter le produit a la requete
		request.setAttribute("ZONE", locationInventaire);

		// produits- quantite traitement
		// methode recherchcer les produits et sa quantite
		HashMap<String, Integer> codeProduit = inventaireDbUtil.searchCodeProduit(zoneRecherche);

		// ajouiter le produit dans la requete
		request.setAttribute("PRODUITLOCATIONINZONE", codeProduit);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/recherche_zone.jsp");
		dispatcher.forward(request, response);

	}

	// function pour rechercher informaation du produit
	// search un produit
	private void searchProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// lire le code a rechercher du form (input)
		String productRecherche = request.getParameter("produitCode");

		// code - description traitement
		// methode pour search product inventaire form db util
		List<ProductInventaire> productInventaire = inventaireDbUtil.searchProduit(productRecherche);

		// location - quantite traitement
		// methode rechercher la location et la quantite
		HashMap<String, Integer> locationProduit = inventaireDbUtil.searchLocationProduit(productRecherche);

		if (productInventaire.isEmpty()) {

			int notFound = 0;
			request.setAttribute("NONTROUVE", notFound);
			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/recherche_produit.jsp");
			dispatcher.forward(request, response);

		} else {

			// ajouter le produit a la requete
			request.setAttribute("PRODUIT", productInventaire);
			// ajouter le produit a la requete
			request.setAttribute("PRODUITLOCATION", locationProduit);

			// envoyer a la vue (jsp)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/recherche_produit.jsp");
			dispatcher.forward(request, response);
		}
	}

	// function pour afficher les utilisateurs
	// rechercher tout les utilisateurs
	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// avoir les utilisateurs de la bd
		List<UtilisateurInventaire> utilisateursInventaire = inventaireDbUtil.getUSers();

		// ajouter les utilisateurs a la request
		request.setAttribute("USERS", utilisateursInventaire);

		// envoyer a la vue (jsp)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

}
