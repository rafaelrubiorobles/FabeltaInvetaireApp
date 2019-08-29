package com.fabelta.inventaire;

import java.util.UUID;

public class ProductInventaire {
		
	//Attributs
	private UUID id;
	private String code;
	private String description;
	private boolean isUsed;
	
	
	//constructeurs
	public ProductInventaire(UUID id, String code, String description, boolean isUsed) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.isUsed = isUsed;
	}


	public ProductInventaire(String code, String description, boolean isUsed) {
		this.code = code;
		this.description = description;
		this.isUsed = isUsed;
	}

	public ProductInventaire() {}
	
	//accesseurs
	//id
	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}

	//code
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	//description
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	//isused
	public boolean isUsed() {
		return isUsed;
	}


	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
}
