package com.epam.gymapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.gymapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String userName);

	List<User> findByUserId(int userId);

	void deleteByUserName(String userName);
	
}
