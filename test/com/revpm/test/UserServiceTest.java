package com.revpm.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import com.revpm.dao.UserDAO;
import com.revpm.model.User;
import com.revpm.service.UserService;

public class UserServiceTest {

	private UserDAO mockUserDAO;

	@Before
	public void setUp() {
		mockUserDAO = mock(UserDAO.class);//we can use @mock annotation also
		UserService.setUserDAO(mockUserDAO);
	}

	@Test
	public void testRegisterUserSuccess() {
		when(mockUserDAO.findByEmail("test@gmail.com")).thenReturn(null);

		User user = UserService.register("Test User", "test@gmail.com","password123");
		assertNotNull(user);
		verify(mockUserDAO).save(any(User.class));
	}

	@Test
	public void testRegisterDuplicateEmail() {
		User existing = new User(1, "Existing", "test@gmail.com", "pass");
		when(mockUserDAO.findByEmail("test@gmail.com")).thenReturn(existing);

		User user = UserService.register("Test User", "test@gmail.com","password123");
		assertNull(user);
		verify(mockUserDAO, never()).save(any(User.class));

	}

	@Test
	public void testLoginSuccess() {

		User dbUser = new User(1, "sai", "sai@gmail.com", "pass123");
		when(mockUserDAO.findByEmail("sai@gmail.com")).thenReturn(dbUser);
		User user = UserService.login("sai@gmail.com", "pass123");
		assertNotNull(user);
		assertEquals("sai", user.getName());
	}

	@Test
	public void testLoginFailure() {
		when(mockUserDAO.findByEmail("wrong@gmail.com")).thenReturn(null);
		User user = UserService.login("wrong@gmail.com", "wrongpass");
		assertNull(user);
	}

	@Test
	public void testGetUserByEmail() {
		User mockUser = new User(1, "Test", "test@gmail.com", "pass");

		when(mockUserDAO.findByEmail("test@gmail.com")).thenReturn(mockUser);

		User result = UserService.getUserByEmail("test@gmail.com");

		assertNotNull(result);
		assertEquals("test@gmail.com", result.getEmail());
	}

	@Test
	public void testUpdateMasterPassword() {
		UserService.updateMasterPassword(1, "newpass123");

		verify(mockUserDAO).updatePassword(1, "newpass123");
	}

	@Test
	public void testUpdateProfile() {
		UserService.updateProfile(1, "New Name", "new@gmail.com");

		verify(mockUserDAO).updateProfile(1, "New Name", "new@gmail.com");
	}

}
