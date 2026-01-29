package com.revpm.test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.revpm.model.User;

public class MockitoCheckTest {

	@Test
	public void testMockitoWorks() {
		User user = mock(User.class);
		when(user.getName()).thenReturn("sai");
		assertEquals("sai", user.getName());
	}
}
