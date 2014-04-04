package com.jss.smsclient;

import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.jss.smsclient.exceptions.SMSClientException;
import com.jss.smsclient.http.HttpService;
import com.jss.smsclient.http.beans.HttpServiceResponse;
import com.jss.smsclient.http.impl.HttpGetService;
import com.jss.smsclient.http.impl.HttpPostService;
import com.jss.smsclient.utils.DateUtils;
import com.jss.smsclient.utils.StringUtils;

public class DefaultSmsClient implements SMSClient {

	private static final String BASE_URL = "https://integrationapi.net/rest";

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	private static final String STATISTICS_DATE_FORMAT = "yyyy-MM-dd' 'HH:mm:ss";

	private enum ServiceQueries {
		SessionId("/User/SessionId"),
		UserBalance("/User/Balance"),
		SendSms("/Sms/Send"),
		SendSmsByTimeZone("/Sms/SendByTimeZone"),
		SendSmsBulk("/Sms/SendBulk"),
		SmsState("/Sms/State"),
		SmsInbox("/Sms/In"),
		SmsStatistics("/Sms/Statistics");

		private String queryName;

		private ServiceQueries(String queryName) {
			this.queryName = queryName;
		}

		public String getQueryName() {
			return queryName;
		}
	}

	private enum ServiceParams {
		Login("login"),
		Password("password"),
		SessionId("sessionId"),
		SourceAddress("sourceAddress"),
		DestinationAddress("destinationAddress"),
		DestinationAddresses("destinationAddresses"),
		Data("data"),
		SendDate("sendDate"),
		Validity("validity"), 
		MessageId("messageId"),
		MinDateUTC("minDateUTC"),
		MaxDateUTC("maxDateUTC"),
		StartDateTime("startDateTime"),
		EndDateTime("endDateTime");

		private String paramName;

		private ServiceParams(String paramName) {
			this.paramName = paramName;
		}

		public String getParamName() {
			return paramName;
		}
	}

	@Override
	public String getSessionID(String login, String password) {
		HttpService httpService = new HttpGetService(BASE_URL, ServiceQueries.SessionId.getQueryName());
		httpService.addParameter(ServiceParams.Login.getParamName(), login);
		httpService.addParameter(ServiceParams.Password.getParamName(), password);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		String sessionId = response.getContent();
		sessionId = StringUtils.removeDoubleQuotes(sessionId);

		return sessionId;
	}

	@Override
	public double getUserBalance(String sessionId) {
		HttpService httpService = new HttpGetService(BASE_URL, ServiceQueries.UserBalance.getQueryName());
		httpService.addParameter(ServiceParams.SessionId.getParamName(), sessionId);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		try {
			String balanceStr = response.getContent();
			return Double.parseDouble(balanceStr);
		} catch (Exception e) {
			throw new SMSClientException(e);
		}
	}

	@Override
	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data) {
		return sendSms(sessionId, sourceAddress, destinationAddress, data, null);
	}

	@Override
	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate) {
		String sendDateStr = DateUtils.format(sendDate, DATE_FORMAT);
		return sendSmsWithStrSendDate(sessionId, sourceAddress, destinationAddress, data, sendDateStr);
	}

	@Override
	public String sendSmsWithStrSendDate(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate) {
		return sendSms(sessionId, sourceAddress, destinationAddress, data, sendDate, null);
	}

	@Override
	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate, Integer validity) {
		String sendDateStr = DateUtils.format(sendDate, DATE_FORMAT);
		return sendSms(sessionId, sourceAddress, destinationAddress, data, sendDateStr, validity);
	}

	@Override
	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate, Integer validity) {
		HttpService httpService = new HttpPostService(BASE_URL, ServiceQueries.SendSms.getQueryName());
		httpService = addparamsForSendSms(httpService, sessionId, sourceAddress, destinationAddress, data, sendDate, validity);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		return response.getContent();
	}

	@Override
	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate) {
		String sendDateStr = DateUtils.format(sendDate, DATE_FORMAT);
		return sendSmsByTimeZone(sessionId, sourceAddress, destinationAddress, data, sendDateStr);
	}

	@Override
	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate) {
		return sendSmsByTimeZone(sessionId, sourceAddress, destinationAddress, data, sendDate, null);
	}

	@Override
	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate, Integer validity) {
		String sendDateStr = DateUtils.format(sendDate, DATE_FORMAT);
		return sendSmsByTimeZone(sessionId, sourceAddress, destinationAddress, data, sendDateStr, validity);
	}

	@Override
	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate, Integer validity) {
		HttpService httpService = new HttpPostService(BASE_URL, ServiceQueries.SendSmsByTimeZone.getQueryName());
		httpService = addparamsForSendSms(httpService, sessionId, sourceAddress, destinationAddress, data, sendDate, validity);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		return response.getContent();
	}

	@Override
	public String sendSmsBulk(String sessionId, String sourceAddress, String data, String... destinationAddresses) {
		Date sendDate = null;
		return sendSmsBulk(sessionId, sourceAddress, data, sendDate, destinationAddresses);
	}

	@Override
	public String sendSmsBulk(String sessionId, String sourceAddress, String data, Date sendDate, String... destinationAddresses) {
		String sendDateStr = DateUtils.format(sendDate, DATE_FORMAT);
		return sendSmsBulk(sessionId, sourceAddress, data, sendDateStr, destinationAddresses);
	}

	@Override
	public String sendSmsBulk(String sessionId, String sourceAddress, String data, String sendDate, String... destinationAddresses) {
		return sendSmsBulk(sessionId, sourceAddress, data, sendDate, null, destinationAddresses);
	}

	@Override
	public String sendSmsBulk(String sessionId, String sourceAddress, String data, Date sendDate, Integer validity, String... destinationAddresses) {
		String sendDateStr = DateUtils.format(sendDate, DATE_FORMAT);
		return sendSmsBulk(sessionId, sourceAddress, data, sendDateStr, validity, destinationAddresses);
	}

	@Override
	public String sendSmsBulk(String sessionId, String sourceAddress, String data, String sendDate, Integer validity, String... destinationAddresses) {
		HttpService httpService = new HttpPostService(BASE_URL, ServiceQueries.SendSmsBulk.getQueryName());
		httpService = addparamsForSendSmsBulk(httpService, sessionId, sourceAddress, data, sendDate, validity, destinationAddresses);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		return response.getContent();
	}

	@Override
	public String getSmsState(String sessionId, String messageId) {
		HttpService httpService = new HttpGetService(BASE_URL, ServiceQueries.SmsState.getQueryName());

		httpService.addParameter(ServiceParams.SessionId.getParamName(), sessionId);
		httpService.addParameter(ServiceParams.MessageId.getParamName(), messageId);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		return response.getContent();
	}

	@Override
	public String getInboxSms(String sessionId, Date minDateUTC, Date maxDateUTC) {
		String minDateUTCStr = DateUtils.format(minDateUTC, DATE_FORMAT);
		String maxDateUTCStr = DateUtils.format(maxDateUTC, DATE_FORMAT);

		return getInboxSms(sessionId, minDateUTCStr, maxDateUTCStr);
	}

	@Override
	public String getInboxSms(String sessionId, String minDateUTC, String maxDateUTC) {
		HttpService httpService = new HttpGetService(BASE_URL, ServiceQueries.SmsInbox.getQueryName());

		httpService.addParameter(ServiceParams.SessionId.getParamName(), sessionId);
		httpService.addParameter(ServiceParams.MinDateUTC.getParamName(), minDateUTC);
		httpService.addParameter(ServiceParams.MaxDateUTC.getParamName(), maxDateUTC);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		return response.getContent();
	}

	@Override
	public String getSmsStatistics(String sessionId, Date startDateTime, Date endDateTime) {
		String startDateTimeStr = DateUtils.format(startDateTime, STATISTICS_DATE_FORMAT);
		String endDateTimeStr = DateUtils.format(endDateTime, STATISTICS_DATE_FORMAT);

		return getSmsStatistics(sessionId, startDateTimeStr, endDateTimeStr);
	}

	@Override
	public String getSmsStatistics(String sessionId, String startDateTime, String endDateTime) {
		HttpService httpService = new HttpGetService(BASE_URL, ServiceQueries.SmsStatistics.getQueryName());

		httpService.addParameter(ServiceParams.SessionId.getParamName(), sessionId);
		httpService.addParameter(ServiceParams.StartDateTime.getParamName(), startDateTime);
		httpService.addParameter(ServiceParams.EndDateTime.getParamName(), endDateTime);

		HttpServiceResponse response = httpService.execute();

		if (response.isNotOkResponse()) {
			throwSmsClientException(response);
		}

		return response.getContent();
	}

	private HttpService addparamsForSendSms(HttpService httpService, String sessionId, String sourceAddress, 
                                            String destinationAddress, String data, String sendDate, Integer validity) {

		httpService.addParameter(ServiceParams.SessionId.getParamName(), sessionId);
		httpService.addParameter(ServiceParams.SourceAddress.getParamName(), sourceAddress);
		httpService.addParameter(ServiceParams.DestinationAddress.getParamName(), destinationAddress);
		httpService.addParameter(ServiceParams.Data.getParamName(), data);

		if (StringUtils.isNotEmpty(sendDate)) {
			httpService.addParameter(ServiceParams.SendDate.getParamName(), sendDate);
		}

		if (validity != null) {
			httpService.addParameter(ServiceParams.Validity.getParamName(), validity);
		}

		return httpService;
	}

	private HttpService addparamsForSendSmsBulk(HttpService httpService, String sessionId, String sourceAddress, 
			                                String data, String sendDate, Integer validity, String... destinationAddresses) {
		
		httpService.addParameter(ServiceParams.SessionId.getParamName(), sessionId);
		httpService.addParameter(ServiceParams.SourceAddress.getParamName(), sourceAddress);
		httpService.addParameters(ServiceParams.DestinationAddresses.getParamName(), destinationAddresses);
		httpService.addParameter(ServiceParams.Data.getParamName(), data);

		if (StringUtils.isNotEmpty(sendDate)) {
			httpService.addParameter(ServiceParams.SendDate.getParamName(), sendDate);
		}

		if (validity != null) {
			httpService.addParameter(ServiceParams.Validity.getParamName(), validity);
		}

		return httpService;
	}

	private void throwSmsClientException(HttpServiceResponse response) {
		String content = response.getContent();
		JSONObject jsonObject = (JSONObject)JSONValue.parse(content);

		Object errorCode = null;
		Object errorInfo = null;

		if (jsonObject != null) {
			errorCode = jsonObject.get("Code");
			errorInfo = jsonObject.get("Desc");		
		}
		
		int httpCode = response.getStatusCode();
		
		throw new SMSClientException(errorCode, errorInfo, httpCode);
	}
}
