package com.epam.gymapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapp.model.Trainee;

public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
	

	Optional<Trainee> findByUserName(String userName);



	void deleteByUserName(String userName);
}
