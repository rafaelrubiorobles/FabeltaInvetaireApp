package com.fabelta.inventaire;

import java.util.UUID;

public class LocationInventaire {
	
	//Attributs
	private UUID id;
	private boolean isUsed;
	private String code;

	
	//Constructeurs
	public LocationInventaire(UUID id, boolean isUsed, String code) {
		this.id = id;
		this.isUsed = isUsed;
		this.code = code;
	}


	public LocationInventaire(boolean isUsed, String code) {
		this.isUsed = isUsed;
		this.code = code;
	}


	public LocationInventaire() {}


	//Accesseurs
	
	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public boolean isUsed() {
		return isUsed;
	}


	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	

}
