package com.jss.smsclient.exceptions;

public class SMSClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static final String UNAVAILABLE_ERROR = "Unavailable";

	private String errorCode;

	private String errorInfo;
	
	private Integer httpCode;

	public SMSClientException(Object errorCode, Object errorInfo, Integer httpCode) {
		setErrorCode(errorCode);
		setErrorInfo(errorInfo);

		this.httpCode = httpCode;
	}

	public SMSClientException(Throwable cause) {
		super(cause);
	}

	public SMSClientException(Throwable cause, Integer errorCode, String errorInfo, Integer httpCode) {
		super(cause);

		setErrorCode(errorCode);
		setErrorInfo(errorInfo);

		this.httpCode = httpCode;
	}

	public String getServiceErrorMessage() {
		StringBuilder builder = new StringBuilder();

		builder.append("SMS Service Error: ").append("\n");
		builder.append("Service Error Code: ").append(errorCode).append("\n");
		builder.append("Service Error Info: ").append(errorInfo).append("\n");
		builder.append("HTTP Code: ").append(httpCode).append("\n");

		return builder.toString();
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public Integer getHttpCode() {
		return httpCode;
	}

	private void setErrorCode(Object errorCode) {
		if (errorCode == null) {
			this.errorCode = UNAVAILABLE_ERROR;
		} else {
			this.errorCode = errorCode.toString();
		}
	}

	private void setErrorInfo(Object errorInfo) {
		if (errorInfo == null) {
			this.errorInfo = UNAVAILABLE_ERROR;
		} else {
			this.errorInfo = errorInfo.toString();
		}
	}
}
