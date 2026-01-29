package com.revpm.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revpm.dao.SecurityQuestionDAO;
import com.revpm.model.SecurityQuestion;
import com.revpm.util.DBUtil;

public class SecurityQuestionDAOImpl implements SecurityQuestionDAO {

	public void save(SecurityQuestion q) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO security_questions VALUES (security_q_seq.NEXTVAL, ?, ?, ?)");

			ps.setString(1, q.getQuestion());
			ps.setString(2, q.getAnswer());
			ps.setInt(3, q.getUserId());

			ps.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SecurityQuestion> findByUser(int userId) {
		List<SecurityQuestion> list = new ArrayList<SecurityQuestion>();

		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM security_questions WHERE user_id=?");

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new SecurityQuestion(rs.getInt("question_id"), rs
						.getString("question"), rs.getString("answer"), rs
						.getInt("user_id")));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean updateAnswer(int questionId, int userId, String newAnswer) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("UPDATE security_questions SET answer=? WHERE question_id=? AND user_id=?");

			ps.setString(1, newAnswer);
			ps.setInt(2, questionId);
			ps.setInt(3, userId);

			int rows = ps.executeUpdate();
			con.close();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int questionId, int userId) {
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con
					.prepareStatement("DELETE FROM security_questions WHERE question_id=? AND user_id=?");

			ps.setInt(1, questionId);
			ps.setInt(2, userId);

			int rows = ps.executeUpdate();
			con.close();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
