package com.revpm.service;

import java.util.List;

import com.revpm.dao.SecurityQuestionDAO;
import com.revpm.daoimpl.SecurityQuestionDAOImpl;
import com.revpm.model.SecurityQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityQuestionService {

	private static SecurityQuestionDAO dao = new SecurityQuestionDAOImpl();

	private static final Logger logger = LoggerFactory
			.getLogger(SecurityQuestionService.class);

	public static void setSecurityQuestionDAO(SecurityQuestionDAO mockDao) {
		dao = mockDao;
	}

	public static void addQuestion(String question, String answer, int userId) {

		logger.info("Adding security question for userId={}", userId);
		SecurityQuestion q = new SecurityQuestion(0, question, answer, userId);

		dao.save(q);
		logger.info("Security question added successfully for userId={}",
				userId);
	}

	public static List<SecurityQuestion> getQuestionsByUser(int userId) {
		logger.info("Fetching security questions for userId={}", userId);
		List<SecurityQuestion> list = dao.findByUser(userId);

		logger.info("Fetched {} security questions for userId={}", list.size(),
				userId);
		return list;
	}

	public static boolean updateAnswer(int questionId, int userId,
			String newAnswer) {

		logger.info(
				"Updating security question answer questionId={} userId={}",
				questionId, userId);

		boolean updated = dao.updateAnswer(questionId, userId, newAnswer);

		if (updated) {
			logger.info("Security question updated successfully questionId={}",
					questionId);
		} else {
			logger.warn(
					"Security question update failed questionId={} userId={}",
					questionId, userId);
		}

		return updated;
	}

	public static boolean deleteQuestion(int questionId, int userId) {
		logger.warn("Deleting security question questionId={} userId={}",
				questionId, userId);

		boolean deleted = dao.delete(questionId, userId);

		if (deleted) {
			logger.info("Security question deleted successfully questionId={}",
					questionId);
		} else {
			logger.warn(
					"Security question delete failed questionId={} userId={}",
					questionId, userId);
		}

		return deleted;
	}
}
