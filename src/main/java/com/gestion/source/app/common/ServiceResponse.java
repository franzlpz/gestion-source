package com.gestion.source.app.common;

public class ServiceResponse {
	private int code;
	private String status;
	private String message;
	private Object body;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public ServiceResponse ok(String message, Object body) {
		ServiceResponse response = new ServiceResponse();
		response.setCode(200);
		response.setStatus("OK");
		response.setMessage(message);
		response.setBody(body);
		return response;
	}

	public ServiceResponse notFound(String message, Object body) {
		ServiceResponse response = new ServiceResponse();
		response.setCode(404);
		response.setStatus("NOT_FOUND");
		response.setMessage(message);
		response.setBody(body);
		return response;
	}

	public ServiceResponse badRequest(String message, Object body) {
		ServiceResponse response = new ServiceResponse();
		response.setCode(400);
		response.setStatus("BAD_REQUEST");
		response.setMessage(message);
		response.setBody(body);
		return response;
	}

	public ServiceResponse internalError(String message, Object body) {
		ServiceResponse response = new ServiceResponse();
		response.setCode(500);
		response.setStatus("INTERNAL_SERVER_ERROR");
		response.setMessage(message);
		response.setBody(body);
		return response;
	}
}
