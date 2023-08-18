package com.epam.gymapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.gymapp.model.Email;

public interface NotificationRepository extends MongoRepository<Email, Integer> {

}
