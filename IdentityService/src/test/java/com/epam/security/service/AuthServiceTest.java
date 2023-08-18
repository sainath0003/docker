package com.epam.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epam.security.entity.UserCredential;
import com.epam.security.exception.UserException;
import com.epam.security.repository.UserCredentialRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
	@Mock
	private UserCredentialRepository repository;
	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@InjectMocks
	private AuthService authService;

	@Test
	void testSaveUser() {
		UserCredential credential = new UserCredential(1, "sai", "123456");
		when(repository.save(any())).thenReturn(credential);

		String result = authService.saveUser(credential);
		assertEquals("user added to the system", result);
	}

	@Test
	void testSaveNullUser() {
		UserCredential credential = new UserCredential(1, "sai", "123456");
		when(repository.save(any())).thenThrow(NullPointerException.class);

		assertThrows(UserException.class, () -> authService.saveUser(credential));
	}

	@Test
	void testGenerateToken() {
		when(jwtService.generateToken(anyString())).thenReturn("asdfr");
		assertNotNull(authService.generateToken("sai"));
	}

	@Test
	void testValidateToken() {
		authService.validateToken("sai");
		Mockito.verify(jwtService).validateToken(anyString());
	}

}
