package com.gestion.source.app.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.gestion.source.app.models.Article;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "Kafka_Message", groupId = "group_id")
	public String consume(String message) {
		return message;
	}

	@KafkaListener(topics = "Kafka_Message_json", groupId = "group_json", containerFactory = "userKafkaListenerFactory")
	public Article consumeJson(Article article) {
		return article;
	}
}
