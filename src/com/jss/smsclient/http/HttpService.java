package com.jss.smsclient.http;

import com.jss.smsclient.http.beans.HttpServiceResponse;

public interface HttpService {

	public void addParameter(String paramName, String paramValue);

	public void addParameter(String paramName, Integer paramValue);

	public void addParameters(String paramName, String ... paramValue);

	public HttpServiceResponse execute() throws HttpServiceException;
}
