package com.fabelta.inventaire;

import java.sql.Timestamp;
import java.util.UUID;

public class ProductLocationInventaire {

	// Attributs
	private UUID id;
	private UUID productInventaire;
	private UUID locationInventaire;
	private int locationQuantity;
	private String commentaire;
	private UUID utilisateurInventaire;
	private Timestamp dateDerniereModif;

	// constructeurs
	public ProductLocationInventaire(UUID id, UUID productInventaire,
			UUID locationInventaire, int locationQuantity, String commentaire,
			UUID utilisateurInventaire, Timestamp dateDerniereModif) {
		this.id = id;
		this.productInventaire = productInventaire;
		this.locationInventaire = locationInventaire;
		this.locationQuantity = locationQuantity;
		this.commentaire = commentaire;
		this.utilisateurInventaire = utilisateurInventaire;
		this.dateDerniereModif = dateDerniereModif;
	}

	// constructeur sans parametres
	public ProductLocationInventaire() {
	}

	// accesseurs
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getProductInventaire() {
		return productInventaire;
	}

	public void setProductInventaire(UUID productInventaire) {
		this.productInventaire = productInventaire;
	}

	public UUID getLocationInventaire() {
		return locationInventaire;
	}

	public void setLocationInventaire(UUID locationInventaire) {
		this.locationInventaire = locationInventaire;
	}

	public int getLocationQuantity() {
		return locationQuantity;
	}

	public void setLocationQuantity(int locationQuantity) {
		this.locationQuantity = locationQuantity;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public UUID getUtilisateurInventaire() {
		return utilisateurInventaire;
	}

	public void setUtilisateurInventaire(UUID utilisateurInventaire) {
		this.utilisateurInventaire = utilisateurInventaire;
	}

	public Timestamp getDateDerniereModif() {
		return dateDerniereModif;
	}

	public void setDateDerniereModif(Timestamp dateDerniereModif) {
		this.dateDerniereModif = dateDerniereModif;
	}

}
