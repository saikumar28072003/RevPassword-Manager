package com.revpm.model;

public class User {

	private int userId;
	private String name;
	private String email;
	private String masterPassword;

	public User(int userId, String name, String email, String masterPassword) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.masterPassword = masterPassword;

	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMasterPassword() {
		return masterPassword;
	}

	public void setMasterPassword(String masterPassword) {
		this.masterPassword = masterPassword;
	}

}
