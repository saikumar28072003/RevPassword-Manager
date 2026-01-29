package com.revpm.service;

import java.util.Random;

import com.revpm.dao.OTPDAO;
import com.revpm.daoimpl.OTPDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OTPService {

	private static OTPDAO otpDAO = new OTPDAOImpl();

	private static final Logger logger = LoggerFactory
			.getLogger(OTPService.class);

	public static void setOTPDAO(OTPDAO mockDao) {
		otpDAO = mockDao;
	}

	public static String generateOTP(int userId) {

		logger.info("OTP generation started for userId={}", userId);
		Random r = new Random();
		int num = 1000 + r.nextInt(8999);
		String otp = String.valueOf(num);

		long expiryTime = System.currentTimeMillis() + (2 * 60 * 1000); // 2
																		// minutes

		otpDAO.saveOTP(userId, otp, expiryTime);
		logger.info(
				"OTP generated and saved successfully for userId={} (expires in 2 minutes)",
				userId);
		return otp;
	}

	public static boolean validateOTP(int userId, String otp) {
		logger.info("OTP validation attempt for userId={}", userId);

		boolean valid = otpDAO.validateOTP(userId, otp);

		if (valid) {
			logger.info("OTP validation successful for userId={}", userId);
		} else {
			logger.warn("OTP validation failed for userId={}", userId);
		}

		return valid;
	}
}
