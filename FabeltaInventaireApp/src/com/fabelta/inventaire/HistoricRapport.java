package com.fabelta.inventaire;

import java.sql.Timestamp;

public class HistoricRapport {

	// attributs
	private String productNameHistoric;
	private String locationNameHistoric;
	private int quantityHistoric;
	private String operation;
	private Timestamp dateHistoric;
	private String userNameHistoric;

	// constructeur
	public HistoricRapport(String productNameHistoric, String locationNameHistoric, int quantityHistoric,
			String operation, Timestamp dateHistoric, String userNameHistoric) {
		this.productNameHistoric = productNameHistoric;
		this.locationNameHistoric = locationNameHistoric;
		this.quantityHistoric = quantityHistoric;
		this.operation = operation;
		this.dateHistoric = dateHistoric;
		this.userNameHistoric = userNameHistoric;
	}

	public HistoricRapport() {
	}

	// accesseurs

	public String getProductNameHistoric() {
		return productNameHistoric;
	}

	public void setProductNameHistoric(String productNameHistoric) {
		this.productNameHistoric = productNameHistoric;
	}

	public String getLocationNameHistoric() {
		return locationNameHistoric;
	}

	public void setLocationNameHistoric(String locationNameHistoric) {
		this.locationNameHistoric = locationNameHistoric;
	}

	public int getQuantityHistoric() {
		return quantityHistoric;
	}

	public void setQuantityHistoric(int quantityHistoric) {
		this.quantityHistoric = quantityHistoric;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Timestamp getDateHistoric() {
		return dateHistoric;
	}

	public void setDateHistoric(Timestamp dateHistoric) {
		this.dateHistoric = dateHistoric;
	}

	public String getUserNameHistoric() {
		return userNameHistoric;
	}

	public void setUserNameHistoric(String userNameHistoric) {
		this.userNameHistoric = userNameHistoric;
	}

}
