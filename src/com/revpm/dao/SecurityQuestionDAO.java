package com.revpm.dao;

import java.util.List;
import com.revpm.model.SecurityQuestion;

public interface SecurityQuestionDAO {

	void save(SecurityQuestion question);

	List<SecurityQuestion> findByUser(int userId);

	boolean updateAnswer(int questionId, int userId, String newAnswer);

	boolean delete(int questionId, int userId);
}