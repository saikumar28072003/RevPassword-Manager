package com.revpm.service;

import java.util.List;

import com.revpm.dao.PasswordEntryDAO;
import com.revpm.daoimpl.PasswordEntryDAOImpl;
import com.revpm.model.PasswordEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordService {

	private static PasswordEntryDAO dao = new PasswordEntryDAOImpl();

	private static final Logger logger = LoggerFactory
			.getLogger(PasswordService.class);

	public static void setPasswordEntryDAO(PasswordEntryDAO mockDao) {
		dao = mockDao;
	}

	public static void addPassword(String account, String username,
			String password, int userId) {

		logger.info("Adding password entry for userId={} account={}", userId,
				account);
		dao.save(new PasswordEntry(0, account, username, password, userId));
		logger.info(
				"Password entry added successfully for userId={} account={}",
				userId, account);
	}

	public static List<PasswordEntry> getPasswordsByUser(int userId) {
		logger.info("Fetching password entries for userId={}", userId);

		List<PasswordEntry> list = dao.findByUser(userId);

		logger.info("Found {} password entries for userId={}", list.size(),
				userId);
		return list;
	}

	public static PasswordEntry getPasswordById(int entryId, int userId) {
		logger.info("Fetching password entry entryId={} for userId={}",
				entryId, userId);

		PasswordEntry entry = dao.findById(entryId, userId);

		if (entry == null) {
			logger.warn("Password entry NOT found entryId={} for userId={}",
					entryId, userId);
		}

		return entry;
	}

	public static void updatePassword(int entryId, String newPassword) {

		logger.info("Updating password for entryId={}", entryId);

		dao.updatePassword(entryId, newPassword);

		logger.info("Password updated successfully for entryId={}", entryId);
	}

	public static void deletePassword(int entryId, int userId) {
		logger.warn("Deleting password entry entryId={} for userId={}",
				entryId, userId);

		dao.delete(entryId, userId);

		logger.info("Password entry deleted entryId={} for userId={}", entryId,
				userId);
	}

	public static List<PasswordEntry> search(String keyword, int userId) {
		logger.info("Searching password entries for userId={} keyword={}",
				userId, keyword);

		List<PasswordEntry> results = dao.searchByAccount(keyword, userId);

		logger.info("Search completed for userId={} found={}", userId,
				results.size());
		return results;
	}

}
