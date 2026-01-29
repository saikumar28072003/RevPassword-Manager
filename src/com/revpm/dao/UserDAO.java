package com.revpm.dao;

import com.revpm.model.User;

public interface UserDAO {
	void save(User user);

	User findByEmail(String email);

	void updatePassword(int userId, String password);

	void updateProfile(int userId, String name, String email);

}
