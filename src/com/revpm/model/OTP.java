package com.revpm.model;

public class OTP {
	private int userId;
	private String code;
	private long expiryTime;
	private boolean used;

	public OTP(int userId, String code, long expiryTime, boolean used) {
		this.userId = userId;
		this.code = code;
		this.expiryTime = expiryTime;
		this.used = used;
	}

	public int getUserId() {
		return userId;
	}

	public String getCode() {
		return code;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public boolean isUsed() {
		return used;
	}
}
