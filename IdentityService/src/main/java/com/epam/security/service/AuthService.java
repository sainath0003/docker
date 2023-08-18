package com.epam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.security.entity.UserCredential;
import com.epam.security.exception.UserException;
import com.epam.security.repository.UserCredentialRepository;

@Service
public class AuthService {

	@Autowired
	private UserCredentialRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	public String saveUser(UserCredential credential) {
		try {
			credential.setPassword(passwordEncoder.encode(credential.getPassword()));
			repository.save(credential);
			return "user added to the system";
		} catch (Exception e) {
			throw new UserException("User cannot be created");
		}
	}

	public String generateToken(String username) {
		return jwtService.generateToken(username);
	}

	public void validateToken(String token) {
		jwtService.validateToken(token);
	}

}
