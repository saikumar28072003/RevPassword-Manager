package com.revpm.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.revpm.dao.OTPDAO;
import com.revpm.service.OTPService;

public class OTPServiceTest {

	private OTPDAO mockDao;

	@Before
	public void setup() {
		mockDao = mock(OTPDAO.class);
		OTPService.setOTPDAO(mockDao);
	}

	@Test
	public void testGenerateOTP() {

		String otp = OTPService.generateOTP(1);

		assertNotNull(otp);
		assertEquals(4, otp.length()); // generating 4-digit OTP

		verify(mockDao).saveOTP(eq(1), eq(otp), anyLong());
	}

	@Test
	public void testValidateOTP_Success() {

		when(mockDao.validateOTP(1, "1234")).thenReturn(true);

		boolean result = OTPService.validateOTP(1, "1234");

		assertTrue(result);
	}

	@Test
	public void testValidateOTP_Failure() {

		when(mockDao.validateOTP(1, "9999")).thenReturn(false);

		boolean result = OTPService.validateOTP(1, "9999");

		assertFalse(result);
	}
}
