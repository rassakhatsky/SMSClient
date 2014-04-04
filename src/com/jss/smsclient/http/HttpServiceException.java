package com.jss.smsclient.http;

public class HttpServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpServiceException() {
		super();
	}

	public HttpServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpServiceException(String message) {
		super(message);
	}

	public HttpServiceException(Throwable cause) {
		super(cause);
	}
}
