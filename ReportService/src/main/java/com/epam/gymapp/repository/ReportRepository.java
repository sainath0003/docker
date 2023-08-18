package com.epam.gymapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.gymapp.model.Report;

public interface ReportRepository extends MongoRepository<Report, String> {
	Optional<Report> findByTrainerUserName(String trainerUserName);
}
