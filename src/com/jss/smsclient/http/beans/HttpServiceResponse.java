package com.jss.smsclient.http.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

public class HttpServiceResponse {

	public static final int HTTP_CODE_200 = 200;

	public static final int HTTP_CODE_400 = 400;

	public static final int HTTP_CODE_401 = 401;

	public static final int HTTP_CODE_403 = 403;

	public static final int HTTP_CODE_500 = 500;

	private int statusCode;

	private String statusMessage;

	private String content;

	public HttpServiceResponse(HttpResponse httpResponse) throws IOException {
		StatusLine statusLine = httpResponse.getStatusLine();
		HttpEntity entity = httpResponse.getEntity();

		this.statusCode = statusLine.getStatusCode();
		this.statusMessage = statusLine.getReasonPhrase();
		this.content = getResponseContent(entity);

		EntityUtils.consume(entity);
	}

	public boolean isNotOkResponse() {
		return !isOkResponse();
	}

	public boolean isOkResponse() {
		return HTTP_CODE_200 == statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public String getContent() {
		return content;
	}

	private String getResponseContent(HttpEntity entity) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

		try {
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}			
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// Do nothing, if exception is occured during stream closing.
				}
			}
		}

		return builder.toString();
	}
}
