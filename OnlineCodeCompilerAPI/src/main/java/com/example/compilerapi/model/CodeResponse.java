package com.example.compilerapi.model;

public class CodeResponse {

	private String output;
	private String error;
	private boolean success;
	private String language;
	private String message;
	
	public CodeResponse() {
		
	}

	public CodeResponse(String output, String error, boolean success, String language, String message) {
		super();
		this.output = output;
		this.error = error;
		this.success = success;
		this.language = language;
		this.message = message;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
