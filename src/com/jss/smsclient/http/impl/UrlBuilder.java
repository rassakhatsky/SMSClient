package com.jss.smsclient.http.impl;

import java.util.ArrayList;
import java.util.List;

import com.jss.smsclient.utils.StringUtils;

public class UrlBuilder {

	private static final String DOMEN_DELIMITER = "?";

	private static final String PARAM_DELIMITER = "=";

	private static final String PARAM_PAIR_DELIMITER = "&";

	private String host;

	private String query;

	private List<HttpParameter> parameters;

	public UrlBuilder(String host) {
		this(host, null);
	}

	public UrlBuilder(String host, String query) {
		this.host = host;
		this.query = query;

		this.parameters = new ArrayList<HttpParameter>();		
	}

	public void addParameter(String paramName, Integer paramValue) {
		this.addParameter(paramName, paramValue.toString());
	}

	public void addparameters(String paramName, String ... paramValues) {
		for (String paramValue : paramValues) {
			addParameter(paramName, paramValue);
		}
	}

	public void addParameter(String paramName, String paramValue) {
		if (StringUtils.isEmpty(paramName)) {
			throw new IllegalArgumentException("parameter's name can not be empty or null.");
		}

		HttpParameter parameter = new HttpParameter(paramName, paramValue);
		parameters.add(parameter);
	}

	public String getUrl() {
		StringBuilder builder = new StringBuilder();

		builder.append(host);
		if (query != null) {
			builder.append(query);
		}

		builder.append(DOMEN_DELIMITER);

		boolean firstParam = true;
		for (HttpParameter parameter : parameters) {
			if (!firstParam) {
				builder.append(PARAM_PAIR_DELIMITER);
			}

			builder.append(parameter.getName());
			builder.append(PARAM_DELIMITER);
			builder.append(parameter.getValue());
			
			firstParam = false;			
		}

		String url = builder.toString();
		url = encodeURL(url);

		return url;
	}

	private String encodeURL(String url) {
		String result = url;

		result = result.replaceAll(" ", "%20");

		return result;
	}
}
