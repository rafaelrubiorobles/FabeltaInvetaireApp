package com.fabelta.inventaire;

public class InventaireRapport {
	
	private String productName;
	private String productZone;
	private int quantity;
	private String commentaire;
	
	
	public InventaireRapport(String productName, String productZone, int quantity, String commentaire) {
		this.productName = productName;
		this.productZone = productZone;
		this.quantity = quantity;
		this.commentaire = commentaire;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductZone() {
		return productZone;
	}


	public void setProductZone(String productZone) {
		this.productZone = productZone;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	
}
