package com.revpm.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revpm.dao.PasswordEntryDAO;
import com.revpm.model.PasswordEntry;
import com.revpm.service.PasswordService;

public class PasswordServiceTest {

	private PasswordEntryDAO mockDao;

	@Before
	public void setup() {
		mockDao = mock(PasswordEntryDAO.class);
		PasswordService.setPasswordEntryDAO(mockDao);
	}

	@Test
	public void testAddPassword() {
		PasswordService.addPassword("Gmail", "user@gmail.com", "pass123", 1);

		verify(mockDao).save(any(PasswordEntry.class));
	}

	@Test
	public void testGetPasswordsByUser() {
		List<PasswordEntry> list = new ArrayList<PasswordEntry>();
		list.add(new PasswordEntry(1, "Gmail", "user@gmail.com", "pass", 1));

		when(mockDao.findByUser(1)).thenReturn(list);

		List<PasswordEntry> result = PasswordService.getPasswordsByUser(1);

		assertEquals(1, result.size());
	}

	@Test
	public void testDeletePassword() {
		PasswordService.deletePassword(1, 1);

		verify(mockDao).delete(1, 1);
	}
}
