package com.jss.smsclient.http.impl;

import com.jss.smsclient.http.HttpService;
import com.jss.smsclient.http.HttpServiceException;
import com.jss.smsclient.http.beans.HttpServiceResponse;

public abstract class HttpBaseService implements HttpService {

	private UrlBuilder urlBuilder;

	public abstract HttpServiceResponse execute() throws HttpServiceException;

	public HttpBaseService(String host) {
		this(host, null);
	}

	public HttpBaseService(String host, String query) {
		this.urlBuilder = new UrlBuilder(host, query);
	}

	@Override
	public void addParameter(String paramName, String paramValue) {
		urlBuilder.addParameter(paramName, paramValue);
	}

	@Override
	public void addParameters(String paramName, String... paramValues) {
		urlBuilder.addparameters(paramName, paramValues);
	}

	@Override
	public void addParameter(String paramName, Integer paramValue) {
		urlBuilder.addParameter(paramName, paramValue);
	}

	protected UrlBuilder getUrlBuilder() {
		return urlBuilder;
	}
}
