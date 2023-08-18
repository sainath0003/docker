package com.epam.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.security.entity.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
	Optional<UserCredential> findByUserName(String username);
}
