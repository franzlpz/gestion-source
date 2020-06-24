package com.gestion.source.app.controllers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.source.app.common.Constants;
import com.gestion.source.app.common.ServiceResponse;
import com.gestion.source.app.models.Article;
import com.gestion.source.app.services.IArticleService;

@RestController
@RequestMapping("/api/")
public class ArticleController {

	@Autowired
	private IArticleService service;

	@Autowired
	private KafkaTemplate<String, Article> kafkaTemplate;

	private static final String TOPIC = "Kafka_Message";

	@GetMapping(value = "/articulos")
	public ResponseEntity<ServiceResponse> list() {
		List<Article> list;
		ServiceResponse response = new ServiceResponse();
		try {
			list = service.findAll();
		} catch (DataAccessException e) {
			return new ResponseEntity<>(
					response.internalError(Constants.messageInternalError, Objects.requireNonNull(e.getMessage())
							.concat(": ").concat(e.getMostSpecificCause().getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (list.isEmpty()) {
			return new ResponseEntity<>(response.notFound(Constants.messageNotFound, null), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response.ok(Constants.messageOk, list), HttpStatus.OK);
	}

	@GetMapping(value = "/articulo/{id}")
	public ResponseEntity<ServiceResponse> getById(@PathVariable Integer id) {
		Article answer;
		ServiceResponse response = new ServiceResponse();
		try {
			answer = service.findById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<>(
					response.internalError(Constants.messageInternalError, Objects.requireNonNull(e.getMessage())
							.concat(": ").concat(e.getMostSpecificCause().getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (answer == null) {
			return new ResponseEntity<>(response.notFound(Constants.messageNotFound, null), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response.ok(Constants.messageOk, answer), HttpStatus.OK);
	}

	@PostMapping(value = "/articulo")
	public ResponseEntity<ServiceResponse> create(@RequestBody Article answer, BindingResult result) {
		Article answerResponse;
		ServiceResponse response = new ServiceResponse();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			return new ResponseEntity<>(response.badRequest(Constants.messageBadRequest, errors),
					HttpStatus.BAD_REQUEST);
		}
		try {
			answerResponse = service.add(answer);
		} catch (DataAccessException e) {
			return new ResponseEntity<>(
					response.internalError(Constants.messageInternalError, Objects.requireNonNull(e.getMessage())
							.concat(": ").concat(e.getMostSpecificCause().getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		kafkaTemplate.send(TOPIC, answer);

		return new ResponseEntity<>(response.ok(Constants.messageOk, answerResponse), HttpStatus.CREATED);
	}

}
