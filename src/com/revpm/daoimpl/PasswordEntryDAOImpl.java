package com.revpm.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revpm.dao.PasswordEntryDAO;
import com.revpm.model.PasswordEntry;
import com.revpm.util.DBUtil;

public class PasswordEntryDAOImpl implements PasswordEntryDAO {

	public void save(PasswordEntry entry) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO password_entries VALUES (password_seq.NEXTVAL, ?, ?, ?, ?)");

			ps.setString(1, entry.getAccountName());
			ps.setString(2, entry.getUsername());
			ps.setString(3, entry.getPassword());
			ps.setInt(4, entry.getUserId());

			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PasswordEntry> findByUser(int userId) {
		List<PasswordEntry> list = new ArrayList<PasswordEntry>();

		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM password_entries WHERE user_id=?");

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new PasswordEntry(rs.getInt("entry_id"), rs
						.getString("account_name"), rs.getString("username"),
						rs.getString("password"), rs.getInt("user_id")));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public PasswordEntry findById(int entryId, int userId) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM password_entries WHERE entry_id=? AND user_id=?");

			ps.setInt(1, entryId);
			ps.setInt(2, userId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new PasswordEntry(rs.getInt("entry_id"),
						rs.getString("account_name"), rs.getString("username"),
						rs.getString("password"), rs.getInt("user_id"));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updatePassword(int entryId, String newPassword) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("UPDATE password_entries SET password=? WHERE entry_id=?");

			ps.setString(1, newPassword);
			ps.setInt(2, entryId);

			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(int entryId, int userId) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("DELETE FROM password_entries WHERE entry_id=? AND user_id=?");

			ps.setInt(1, entryId);
			ps.setInt(2, userId);

			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PasswordEntry> searchByAccount(String keyword, int userId) {
		List<PasswordEntry> list = new ArrayList<PasswordEntry>();

		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM password_entries WHERE user_id=? AND LOWER(account_name) LIKE ?");

			ps.setInt(1, userId);
			ps.setString(2, "%" + keyword.toLowerCase() + "%");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new PasswordEntry(rs.getInt("entry_id"), rs
						.getString("account_name"), rs.getString("username"),
						rs.getString("password"), rs.getInt("user_id")));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
