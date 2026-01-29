package com.revpm.dao;

public interface OTPDAO {

	void saveOTP(int userId, String otp, long expiryTime);

	boolean validateOTP(int userId, String otp);
}
