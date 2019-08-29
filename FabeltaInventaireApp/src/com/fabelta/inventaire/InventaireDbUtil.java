package com.fabelta.inventaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

public class InventaireDbUtil {

	private DataSource dataSource;

	public InventaireDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Prendre les utilisateurs de la base de donnees et afficher en page login
	public List<UtilisateurInventaire> getUSers() throws Exception {

		List<UtilisateurInventaire> utilisateursInventaire = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			// avoir une connexion
			connection = dataSource.getConnection();

			// creer sql statement
			String sql = "select * from fabeltaschema.utilisateurinventairefabelta fsuib where fsuib.is_used = true";
			statement = connection.createStatement();

			// executer sql
			resultSet = statement.executeQuery(sql);

			// process result set
			while (resultSet.next()) {
				// recevoir donnees du resul set row
				UUID id = resultSet.getObject("id", java.util.UUID.class);
				String userName = resultSet.getString("user_name");
				String password = resultSet.getString("password");
				boolean isUsed = resultSet.getBoolean("is_used");

				// creer objet utilisateurInventaire
				UtilisateurInventaire tempUtilisateur = new UtilisateurInventaire(id, userName, password, isUsed);

				// ajouter a la liste des utilisateurs
				utilisateursInventaire.add(tempUtilisateur);
			}

			return utilisateursInventaire;

		} finally {

			// fermer JDBC objects
			close(connection, statement, resultSet);
		}

	}

	// Function pour get tout l'information de l'utilisateur
	public UtilisateurInventaire searchUserByUsername(String userName) throws Exception {

		UtilisateurInventaire utilisateurInventaire = new UtilisateurInventaire();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// se connecter
			connection = dataSource.getConnection();

			// rechercher just si on recoit un nom
			if (userName != null && userName.trim().length() > 0) {

				// creer requete pour rechercher le produit
				String sql = "select * from fabeltaschema.utilisateurinventairefabelta where lower(user_name) like ?";

				// creer prepared statemnt
				preparedStatement = connection.prepareStatement(sql);

				// set le parametre recu dans la requete
				String userRechercheLike = "%" + userName.toLowerCase();
				preparedStatement.setString(1, userRechercheLike);

			} else {
				System.out.println("no records");
			}

			// execute la requete
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				// data recu du result set
				UUID uuid = resultSet.getObject("id", UUID.class);
				String userName1 = resultSet.getString("user_name");
				String password = resultSet.getString("password");
				boolean isUsed = resultSet.getBoolean("is_used");

				utilisateurInventaire = new UtilisateurInventaire(uuid, userName1, password, isUsed);
			}

		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return utilisateurInventaire;

	}

	// Fucntion pour rechercher un produit
	public List<ProductInventaire> searchProduit(String productRecherche) throws Exception {

		List<ProductInventaire> productInventaire = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// se connecter a la bd
			connection = dataSource.getConnection();

			// rechercher just si le input est pas vide
			if (productRecherche != null && productRecherche.trim().length() > 0) {

				// creer requete pour rechercher le produit
				String sql = "select \r\n" + "	prd.id,\r\n" + "	prd.code,\r\n" + "	pd.value as description,\r\n"
						+ "	prd.active as is_used\r\n" + "from\r\n" + "  	productdescription pd \r\n"
						+ "  	left join product prd on pd.product_id = prd.id where pd.languagecode like 'fr' and prd.code like ?";

				// creer prepared statement
				preparedStatement = connection.prepareStatement(sql);

				// on set le parametre dans la requete le code du produit
				// String productRechercheLike = "%" + productRecherche.toLowerCase();
				preparedStatement.setString(1, productRecherche);

			} else {
				System.out.println("no records");
			}

			// execute le prepared statement
			resultSet = preparedStatement.executeQuery();

			// recevoir data du result set row
			while (resultSet.next()) {

				// recevoir data du resul set row
				UUID id = resultSet.getObject("id", java.util.UUID.class);
				String code1 = resultSet.getString("code");
				String description = resultSet.getString("description");
				boolean isUsed = resultSet.getBoolean("is_used");

				// creer l'objet ProductInventaire
				ProductInventaire productInventaire2 = new ProductInventaire(id, code1, description, isUsed);

				// ajouter la liste productInventaire productInventaire2 a l'onjet deja cree
				productInventaire.add(productInventaire2);
			}

			return productInventaire;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	// Function pour rechercher la location d un produit
	public HashMap<String, Integer> searchLocationProduit(String productRecherche) throws Exception {

		// liste a remplir pour passer des donnes au servlet
		HashMap<String, Integer> produitLocation = new HashMap<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// connecion a la bd
			connection = dataSource.getConnection();

			// rechercher just si le input est pas vide
			if (productRecherche != null && productRecherche.trim().length() > 0) {

				// requete pour rechercher linformation (zone,quantite) du produit
				String sql = "select\r\n" + "	lf.code,\r\n" + "	locationquantity\r\n" + "from \r\n"
						+ "	fabeltaschema.productlocationfabelta plfb\r\n"
						+ "	left join fabeltaschema.locationfabelta lf on plfb.location_id = lf.id\r\n"
						+ "	left join product prd on plfb.product_id = prd.id where prd.code like ?;";

				// creer prepared statement
				preparedStatement = connection.prepareStatement(sql);

				// on set le parametre dans la requete (le code du produit)
				String productRechercheLike = "%" + productRecherche;
				preparedStatement.setString(1, productRechercheLike);

			} else {
				System.out.println("produit pas trouve");
			}

			// executer le prepared statement
			resultSet = preparedStatement.executeQuery();

			// recevoir data du resul set row
			while (resultSet.next()) {

				// recevoir data du result set row
				String code = resultSet.getString("code");
				Integer location = resultSet.getInt("locationquantity");

				// remplir hash map avec l'information obtenu
				produitLocation.put(code, location);
			}

			return produitLocation;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	public LocationInventaire searchLocationByCode(String code) throws Exception {

		LocationInventaire locationInventaire = new LocationInventaire();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// se connecter
			connection = dataSource.getConnection();

			// search only if we get a name
			if (code != null && code.trim().length() > 0) {

				// creer la requete
				String sql = "select * from fabeltaschema.locationfabelta where code = ?";

				// creer prepared statement
				preparedStatement = connection.prepareStatement(sql);

				// set le prepared statement recu dans la requete
				preparedStatement.setString(1, code);

			} else {
				System.out.println("no records");
			}

			// execute la requete
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				UUID uuid = resultSet.getObject("id", UUID.class);
				Boolean isUsed = resultSet.getBoolean("is_used");
				String code1 = resultSet.getString("code");

				locationInventaire = new LocationInventaire(uuid, isUsed, code1);

			}

		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return locationInventaire;

	}

	// Function pour rechercher info de un location
	public List<LocationInventaire> searchZone(String zoneRecherche) throws Exception {

		List<LocationInventaire> locationInventaire = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// get the connection
			connection = dataSource.getConnection();

			// rechercher juste si le input est pas vide
			if (zoneRecherche != null && zoneRecherche.trim().length() > 0) {

				// creer requete pour rechercher la zone
				String sql = "SELECT * FROM fabeltaschema.locationfabelta where LOWER(code) ILIKE ?";

				// prepared statement
				preparedStatement = connection.prepareStatement(sql);

				// on set le parametre dans la requete le code de la zone
				String zoneRechercheLike = "%" + zoneRecherche.toLowerCase();
				preparedStatement.setString(1, zoneRechercheLike);

			} else {
				System.out.println("no records");
			}

			// execute le prepared statemnt
			resultSet = preparedStatement.executeQuery();

			// recevoir ddata du result set
			while (resultSet.next()) {

				// recevoir data du result set row
				UUID id = resultSet.getObject("id", java.util.UUID.class);
				boolean isUsed = resultSet.getBoolean("is_used");
				String code = resultSet.getString("code");

				// creer l'objet location inventaire pour le remplir
				LocationInventaire locationInventaire2 = new LocationInventaire(id, isUsed, code);

				// ajouter a la liste l'objet locationInventaire2 q on viens de creeer
				locationInventaire.add(locationInventaire2);
			}

			return locationInventaire;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	// Function pour rechercer les produits dans un zone
	public HashMap<String, Integer> searchCodeProduit(String zoneRecherche) throws Exception {

		// Hash map a remplir pour passer de donnes au servlet
		HashMap<String, Integer> locationProduit = new HashMap<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// connexion a la base de donnes
			connection = dataSource.getConnection();

			// recherche si le input est pas vide
			if (zoneRecherche != null && zoneRecherche.trim().length() > 0) {

				// requte pour rechcercher les produits dans la zone saissie par le utilissateur
				String sql = "select\r\n" + "	case\r\n" + "	when prd.code is null then fspf.code\r\n"
						+ "	else prd.code\r\n" + "	end as code,\r\n" + "		locationquantity\r\n" + "from \r\n"
						+ "	fabeltaschema.productlocationfabelta plfb\r\n"
						+ "	left join fabeltaschema.productfabelta fspf on plfb.product_id = fspf.id\r\n"
						+ "	left join product prd on plfb.product_id = prd.id\r\n"
						+ "	left join fabeltaschema.locationfabelta fslf on plfb.location_id = fslf.id where fslf.code = ?";

				// creer le preparedstatement
				preparedStatement = connection.prepareStatement(sql);

				// on set le parametre dans la requete (le code de la zone)
				// String zoneRechercheLike = "%" + zoneRecherche;
				preparedStatement.setString(1, zoneRecherche);

			} else {
				System.out.println("no records");
			}

			// executer le prepared statement rececvoir information dans le resul set
			resultSet = preparedStatement.executeQuery();

			// recevoir data du result set
			while (resultSet.next()) {

				// recevoir data du result set row
				String code = resultSet.getString("code");
				Integer quantite = resultSet.getInt("locationquantity");

				// remplir le hashmap ave l'information obtenu
				locationProduit.put(code, quantite);
			}

			return locationProduit;

		} finally {
			// fermeture de la connexion
			close(connection, preparedStatement, resultSet);
		}

	}

	// Function pour changer le total venant de la fenetre reception
	public void setNewTotalScannedAjout(UUID uidProduct, UUID uidLocation, int qty, Timestamp timestamp, UUID uidUser)
			throws Exception {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get the connection
			connection = dataSource.getConnection();

			String sql1 = "update fabeltaschema.productlocationfabelta\r\n"
					+ "set locationquantity = locationquantity + ?, \r\n" + "date_derniere_modif = ?,\r\n"
					+ "utilisateur_id = ?\r\n" + "where product_id = ? and location_id = ?";

			// prepared statement
			preparedStatement = connection.prepareStatement(sql1);

			// on set les 3 parametres a passer dans la requete
			preparedStatement.setInt(1, qty);
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setObject(3, uidUser, java.sql.Types.OTHER);
			preparedStatement.setObject(4, uidProduct, java.sql.Types.OTHER);
			preparedStatement.setObject(5, uidLocation, java.sql.Types.OTHER);

			// execute query
			preparedStatement.execute();

		} finally {
			close(connection, preparedStatement, null);
		}

	}

	public void setNewZoneProduit(UUID uidProduct, UUID uidLocation, int qty, Timestamp timestamp, UUID uidUser)
			throws Exception {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get the connection
			connection = dataSource.getConnection();

			// la requete
			String sql = "insert into fabeltaschema.productlocationfabelta (id,product_id,location_id,locationquantity,utilisateur_id,date_derniere_modif)\r\n"
					+ "values (uuid_generate_v4(),?,?,?,?,?)";

			// prepared statment
			preparedStatement = connection.prepareStatement(sql);

			// on set les 4 parametres dans la requete
			preparedStatement.setObject(1, uidProduct, java.sql.Types.OTHER);
			preparedStatement.setObject(2, uidLocation, java.sql.Types.OTHER);
			preparedStatement.setInt(3, qty);
			preparedStatement.setObject(4, uidUser, java.sql.Types.OTHER);
			preparedStatement.setTimestamp(5, timestamp);

			// execute query
			preparedStatement.execute();

		} finally {
			close(connection, preparedStatement, null);
		}
	}

	// set information in product location fabelta historic
	public void setInHistoric(UUID uidProduct, UUID uidZone, int qty, Timestamp timestamp, String userId,
			String operation, Boolean success) throws Exception {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get the connection
			connection = dataSource.getConnection();

			// la requete
			String sql = "insert into fabeltaschema.productlocationfabeltahistoric (id,product_id,location_id,quantity,historicdate,user_id,operation,success)\r\n"
					+ "values (uuid_generate_v4(),?,?,?,?,?,?,?)";

			// prepared statement
			preparedStatement = connection.prepareStatement(sql);

			// set les paramentres dans la requete
			preparedStatement.setObject(1, uidProduct, Types.OTHER);
			preparedStatement.setObject(2, uidZone, Types.OTHER);
			preparedStatement.setInt(3, qty);
			preparedStatement.setTimestamp(4, timestamp);
			preparedStatement.setObject(5, userId, Types.OTHER);
			preparedStatement.setString(6, operation);
			preparedStatement.setBoolean(7, success);

			// execute query
			preparedStatement.execute();

		} finally {
			close(connection, preparedStatement, null);
		}
	}

	// enlever quantite dans product location fabelta et le mettre a jour
	public void setNewTotalScannedEnlever(UUID uidProduct, UUID uidZone, int qty, Timestamp timestamp, String userId)
			throws Exception {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get the connection
			connection = dataSource.getConnection();

			String sql = "update fabeltaschema.productlocationfabelta\r\n"
					+ "set locationquantity = locationquantity - ?, \r\n" + "date_derniere_modif = ?,\r\n"
					+ "utilisateur_id = ?\r\n" + "where product_id = ? and location_id = ?";

			// prepared statement
			preparedStatement = connection.prepareStatement(sql);

			// on set les 3 parametres a passer dans la requete
			preparedStatement.setInt(1, qty);
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setObject(3, userId, java.sql.Types.OTHER);
			preparedStatement.setObject(4, uidProduct, java.sql.Types.OTHER);
			preparedStatement.setObject(5, uidZone, java.sql.Types.OTHER);

			// execute query
			preparedStatement.execute();

		} finally {
			close(connection, preparedStatement, null);
		}

	}

	// Function pour set la nouvelle quantite dans la table productlocationfabelta
	public void setNewQuantityProduct(UUID uidProduct, UUID uidZone, String userId, int newQuantite,
			Timestamp timestamp) throws Exception {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get the connection
			connection = dataSource.getConnection();

			// requete
			String sql = "update fabeltaschema.productlocationfabelta\r\n" + "set locationquantity =  ?, \r\n"
					+ "date_derniere_modif = ?,\r\n" + "utilisateur_id = ?\r\n"
					+ "where product_id = ? and location_id = ?";

			// prepared statement
			preparedStatement = connection.prepareStatement(sql);

			// on set les parametres
			preparedStatement.setInt(1, newQuantite);
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setObject(3, userId, Types.OTHER);
			preparedStatement.setObject(4, uidProduct, Types.OTHER);
			preparedStatement.setObject(5, uidZone, Types.OTHER);

			// execute query
			preparedStatement.execute();

		} finally {
			close(connection, preparedStatement, null);
		}

	}

	// Function pour regarder dans la table productlocation si la zone existe deja
	// avec le produit recu
	public Boolean zoneExist(UUID uidProduct, UUID uidZoneADeplacer) throws Exception {

		boolean retour = false;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// avoir une connexion
			connection = dataSource.getConnection();

			// creer sql statement
			String sql = "select * from fabeltaschema.productlocationfabelta where product_id = ? and location_id = ?";
			statement = connection.prepareStatement(sql);

			statement.setObject(1, uidProduct, Types.OTHER);
			statement.setObject(2, uidZoneADeplacer, Types.OTHER);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				retour = true;
			} else {
				System.out.println("not found");
			}

		} finally {
			close(connection, statement, resultSet);
		}

		return retour;
	}

	public void erraseRowWhenIsZero(UUID uuidLocation) throws Exception {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// se connecter
			connection = dataSource.getConnection();

			String sql = "delete from fabeltaschema.productlocationfabelta where location_id = ? and locationquantity = 0;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, uuidLocation);

			// execute query
			preparedStatement.execute();

		} finally {
			close(connection, preparedStatement, null);
		}
	}

	public List<ProductLocationInventaire> getProductLocationByZoneId(UUID uuidLocation) throws Exception {

		List<ProductLocationInventaire> productLocationInventaires = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// get the connection
			connection = dataSource.getConnection();

			String sql = "select * from fabeltaschema.productlocationfabelta where location_id = ?;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, uuidLocation);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// data from result set
				UUID uuid = resultSet.getObject("id", UUID.class);
				UUID uuidProduct = resultSet.getObject("product_id", UUID.class);
				UUID uuidLocation1 = resultSet.getObject("location_id", UUID.class);
				int locationQuantity = resultSet.getInt("locationquantity");
				String commentaire = resultSet.getString("commentaire");
				UUID uuieUtilisateur = resultSet.getObject("utilisateur_id", UUID.class);
				Timestamp dateModification = resultSet.getTimestamp("date_derniere_modif");

				ProductLocationInventaire productLocationInventaire = new ProductLocationInventaire(uuid, uuidProduct,
						uuidLocation1, locationQuantity, commentaire, uuieUtilisateur, dateModification);

				// ajouter dans la liste
				productLocationInventaires.add(productLocationInventaire);
			}

			return productLocationInventaires;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	public String getCommentFromProductLocation(UUID uuidProduct, UUID uuidZone) throws Exception {

		String commentaire = "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// get connection
			connection = dataSource.getConnection();

			String sql = "select commentaire from fabeltaschema.productlocationfabelta where location_id = ? and product_id = ? ;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, uuidZone);
			preparedStatement.setObject(2, uuidProduct);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String commentaireFormBd = resultSet.getString("commentaire");

				commentaire = commentaireFormBd;
			}

			return commentaire;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	public void editCommentFromProductLocaion(UUID uuidProduct, UUID uuidZone, String commentaireAEditer)
			throws Exception {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get the connection
			connection = dataSource.getConnection();

			String sql = "update fabeltaschema.productlocationfabelta set commentaire = ? where location_id = ? and product_id = ?;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, commentaireAEditer);
			preparedStatement.setObject(2, uuidZone);
			preparedStatement.setObject(3, uuidProduct);

			preparedStatement.execute();

		} finally {
			close(connection, preparedStatement, null);
		}

	}

	public int getQuantityFromProductLocation(UUID uuidProduit, UUID uuidReception) throws Exception {

		int quantite = 0;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			String sql = "select locationquantity from fabeltaschema.productlocationfabelta where location_id = ? and product_id = ?;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, uuidReception);
			preparedStatement.setObject(2, uuidProduit);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int quantiteFromBd = resultSet.getInt("locationquantity");

				quantite = quantiteFromBd;
			}

			return quantite;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	// Function pour fermer la connexion a la bd
	private void close(Connection connection, Statement statement, ResultSet resultSet) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<InventaireRapport> getRapportFullInventry() throws Exception {

		List<InventaireRapport> inventaireRapports = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// connection
			connection = dataSource.getConnection();

			// requete
			String sql = "select \r\n" + "	case\r\n" + "	when prd.code is null then fspf.code\r\n"
					+ "	else prd.code\r\n" + "	end as code,\r\n" + "	fslf.code as locationCode,\r\n"
					+ "	locationquantity,\r\n" + "	commentaire \r\n" + "from \r\n"
					+ "	fabeltaschema.productlocationfabelta fsplf\r\n"
					+ "	left join product prd on fsplf.product_id = prd.id\r\n"
					+ "	left join fabeltaschema.productfabelta fspf on fsplf.product_id = fspf.id\r\n"
					+ "	left join fabeltaschema.locationfabelta fslf on fsplf.location_id = fslf.id";

			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// data from the result set
				String productCode = resultSet.getString("code");
				String zoneCode = resultSet.getString("locationcode");
				int quantity = resultSet.getInt("locationquantity");
				String commentaire = resultSet.getString("commentaire");

				InventaireRapport inventaireRapport = new InventaireRapport(productCode, zoneCode, quantity,
						commentaire);

				inventaireRapports.add(inventaireRapport);
			}

			return inventaireRapports;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	public List<HistoricRapport> getRapportFullHistoric(String dateDe, String dateA) throws Exception {

		List<HistoricRapport> historicRapports = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// connection
			connection = dataSource.getConnection();

			// requete
			String sql = "select \r\n" + "	case\r\n" + "	when prd.code is null then fspf.code\r\n"
					+ "	else prd.code\r\n" + "	end as code,\r\n" + "	fslf.code as locationCode,\r\n"
					+ "	quantity,\r\n" + "	operation,\r\n" + "	historicdate,\r\n" + "	uif.user_name\r\n"
					+ "from fabeltaschema.productlocationfabeltahistoric fsplfh\r\n"
					+ "	left join product prd on fsplfh.product_id = prd.id\r\n"
					+ "	left join fabeltaschema.productfabelta fspf on fsplfh.product_id = fspf.id\r\n"
					+ "	left join fabeltaschema.locationfabelta fslf on fsplfh.location_id = fslf.id\r\n"
					+ "	left join fabeltaschema.utilisateurinventairefabelta uif on fsplfh.user_id = uif.id where fsplfh.historicdate \r\n"
					+ "	between to_date( ? ,'YYYY/MM/DD') \r\n" + "	and to_date( ? ,'YYYY/MM/DD') \r\n"
					+ "	order by fsplfh.historicdate asc;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, dateDe);
			preparedStatement.setString(2, dateA);

			// execute query
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// data from de result set
				String productCode = resultSet.getString("code");
				String productLocation = resultSet.getString("locationcode");
				int quantite = resultSet.getInt("quantity");
				String operation = resultSet.getString("operation");
				Timestamp date = resultSet.getTimestamp("historicdate");
				String userName = resultSet.getString("user_name");

				HistoricRapport historicRapport = new HistoricRapport(productCode, productLocation, quantite, operation,
						date, userName);

				historicRapports.add(historicRapport);
			}

			return historicRapports;

		} finally {
			close(connection, preparedStatement, resultSet);
		}

	}

	public Boolean getLogin(String username, String password) throws Exception {

		boolean retour = false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// connection
			connection = dataSource.getConnection();

			// requete sql
			String sql = "select * from fabeltaschema.utilisateurinventairefabelta \r\n"
					+ "where user_name = ? and \"password\" = ? ;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			// execute query
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				retour = true;
			}

		} finally {
			close(connection, preparedStatement, resultSet);	
		}

		return retour;
	}

}
