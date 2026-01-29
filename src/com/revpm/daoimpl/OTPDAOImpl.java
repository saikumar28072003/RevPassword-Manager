package com.revpm.daoimpl;

import java.sql.*;

import com.revpm.dao.OTPDAO;
import com.revpm.util.DBUtil;

public class OTPDAOImpl implements OTPDAO {

	public void saveOTP(int userId, String otp, long expiryTime) {
		try {
			Connection con = DBUtil.getConnection();

			PreparedStatement ps = con
					.prepareStatement("INSERT INTO otp_codes (otp_code, expiry_time, used, user_id) VALUES (?, ?, 'N', ?)");

			ps.setString(1, otp);
			ps.setLong(2, expiryTime);
			ps.setInt(3, userId);

			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validateOTP(int userId, String otp) {
		try {
			Connection con = DBUtil.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT expiry_time, used FROM otp_codes WHERE user_id=? AND otp_code=?");

			ps.setInt(1, userId);
			ps.setString(2, otp);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				long expiryTime = rs.getLong("expiry_time");
				String used = rs.getString("used");

				if ("Y".equals(used)) {
					con.close();
					return false;
				}

				if (System.currentTimeMillis() > expiryTime) {
					con.close();
					return false;
				}

				// mark OTP as used
				PreparedStatement ups = con
						.prepareStatement("UPDATE otp_codes SET used='Y' WHERE user_id=? AND otp_code=?");

				ups.setInt(1, userId);
				ups.setString(2, otp);
				ups.executeUpdate();

				con.close();
				return true;
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
