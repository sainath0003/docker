package com.epam.gymapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	@Bean
	public NewTopic getTopics() {
		return TopicBuilder.name("gymapp").build();
	}
	@Bean
	public NewTopic getReport() {
		return TopicBuilder.name("report").build();
	}
	
	@Bean
	public NewTopic getUserDto() {
		return TopicBuilder.name("userDto1").build();
	}
	@Bean
	public NewTopic getEmail() {
		return TopicBuilder.name("emailOut").build();
	}

}
