package com.epam.gymapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapp.model.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {



	Optional<Trainer> findByUserName(String userName);

	void deleteByUserName(String userName);
}
