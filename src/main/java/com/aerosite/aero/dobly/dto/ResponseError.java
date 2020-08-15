package com.aerosite.aero.dobly.dto;

import java.security.Timestamp;

public class ResponseError {

	private Timestamp timestamp;
	private int Status;
	private String error;
	private String message;
	private String path;
	
	public ResponseError() {
		super();
	}

	public ResponseError(Timestamp timestamp, int status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		Status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ResponseError [timestamp=" + timestamp + ", Status=" + Status + ", error=" + error + ", message="
				+ message + ", path=" + path + "]";
	}
	
}
