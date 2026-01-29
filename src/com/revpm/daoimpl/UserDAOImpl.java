package com.revpm.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revpm.dao.UserDAO;
import com.revpm.model.User;
import com.revpm.util.DBUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public void save(User user) {
		Connection con = DBUtil.getConnection();
		try {
			String sql = "INSERT INTO users VALUES (user_seq.NEXTVAL, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getMasterPassword());

			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findByEmail(String email) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM users WHERE email=?");

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new User(rs.getInt("user_id"), rs.getString("name"),
						rs.getString("email"), rs.getString("master_password"));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updatePassword(int userId, String newPassword) {

		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("UPDATE users SET master_password=? WHERE user_id=?");

			ps.setString(1, newPassword);
			ps.setInt(2, userId);
			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProfile(int userId, String name, String email) {
		try {
			Connection con = DBUtil.getConnection();

			PreparedStatement ps = con
					.prepareStatement("UPDATE users SET name=?, email=? WHERE user_id=?");

			ps.setString(1, name);
			ps.setString(2, email);
			ps.setInt(3, userId);

			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
