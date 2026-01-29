package com.revpm.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revpm.dao.SecurityQuestionDAO;
import com.revpm.model.SecurityQuestion;
import com.revpm.service.SecurityQuestionService;

public class SecurityQuestionServiceTest {

	private SecurityQuestionDAO mockDao;

	@Before
	public void setup() {
		mockDao = mock(SecurityQuestionDAO.class);
		SecurityQuestionService.setSecurityQuestionDAO(mockDao);
	}

	@Test
	public void testAddSecurityQuestion() {
		SecurityQuestionService.addQuestion("What is your pet name?",
				"jacksparrow", 1);

		verify(mockDao).save(any(SecurityQuestion.class));
	}

	@Test
	public void testGetQuestionsByUser() {
		ArrayList<SecurityQuestion> list = new ArrayList<SecurityQuestion>();
		list.add(new SecurityQuestion(1, "What is your birth city?","Hyderabad", 1));

		when(mockDao.findByUser(1)).thenReturn(list);

		List<SecurityQuestion> result = SecurityQuestionService.getQuestionsByUser(1);

		assertEquals(1, result.size());
	}

	@Test
	public void testUpdateSecurityAnswer() {
		when(mockDao.updateAnswer(1, 1, "NewAnswer")).thenReturn(true);

		boolean updated = SecurityQuestionService.updateAnswer(1, 1,"NewAnswer");

		assertTrue(updated);
	}

	@Test
	public void testDeleteSecurityQuestion() {
		when(mockDao.delete(1, 1)).thenReturn(true);

		boolean deleted = SecurityQuestionService.deleteQuestion(1, 1);

		assertTrue(deleted);
	}
}
