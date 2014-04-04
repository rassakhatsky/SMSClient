package com.jss.smsclient.http.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.jss.smsclient.http.HttpServiceException;
import com.jss.smsclient.http.beans.HttpServiceResponse;

public class HttpPostService extends HttpBaseService {

	public HttpPostService(String host, String query) {
		super(host, query);
	}

	public HttpPostService(String host) {
		super(host);
	}

	@Override
	public HttpServiceResponse execute() throws HttpServiceException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = createHttpPost();

		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpServiceResponse response = new HttpServiceResponse(httpResponse);

			return response;
		} catch (IOException e) {
			throw new HttpServiceException(e);
		} finally {
			httpPost.releaseConnection();
		}
	}

	private HttpPost createHttpPost() {
		String url = getUrlBuilder().getUrl();

		HttpPost httpPost = new HttpPost(url);		
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

		return httpPost;
	}	
}
