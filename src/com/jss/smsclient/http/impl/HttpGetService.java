package com.jss.smsclient.http.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.jss.smsclient.http.HttpServiceException;
import com.jss.smsclient.http.beans.HttpServiceResponse;

public class HttpGetService extends HttpBaseService {
	
	public HttpGetService(String host, String query) {
		super(host, query);
	}

	public HttpGetService(String host) {
		super(host);
	}

	@Override
	public HttpServiceResponse execute() throws HttpServiceException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = createHttpGet();

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpServiceResponse response = new HttpServiceResponse(httpResponse);

			return response;
		} catch (IOException e) {
			throw new HttpServiceException(e);
		} finally {
			httpGet.releaseConnection();
		}
	}

	private HttpGet createHttpGet() {
		String url = getUrlBuilder().getUrl();

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");

		return httpGet;
	}
}
