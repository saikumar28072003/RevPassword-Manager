package com.revpm.model;

public class PasswordEntry {

	private int id;
	private String accountName;
	private String username;
	private String password;
	private int userId;

	public PasswordEntry(int id, String accountName, String username,
			String password, int userId) {
		this.id = id;
		this.accountName = accountName;
		this.username = username;
		this.password = password;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

}
