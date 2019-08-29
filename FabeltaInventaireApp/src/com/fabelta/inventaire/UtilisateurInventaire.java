package com.fabelta.inventaire;

import java.util.UUID;

public class UtilisateurInventaire {

	//Attributs
	private UUID id;
	private String userName;
	private String password;
	private boolean isUsed;
	
	
	//constructeurs
	public UtilisateurInventaire(UUID id, String userName, String password, boolean isUsed) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.isUsed = isUsed;
	}


	public UtilisateurInventaire(String userName, String password, boolean isUsed) {
		this.userName = userName;
		this.password = password;
		this.isUsed = isUsed;
	}

	public UtilisateurInventaire() {}
	
	//accesseurs
	//id
	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	//user name
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	//password
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	//is_used
	public boolean isUsed() {
		return isUsed;
	}


	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}


	@Override
	public String toString() {
		return "UtilisateurInventaire [id=" + id + ", userName=" + userName + ", password=" + password
				+ ", isUsed=" + isUsed + "]";
	}
	
	
	
}
