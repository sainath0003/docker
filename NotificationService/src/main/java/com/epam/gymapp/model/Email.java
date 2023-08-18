package com.epam.gymapp.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "email")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
	private String recipient;
	private String subject;
	private String body;
	private boolean status = true;

	public Email(String recipient, String subject, String body) {
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
	}



}
