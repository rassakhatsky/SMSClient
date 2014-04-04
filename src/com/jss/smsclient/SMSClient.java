package com.jss.smsclient;

import java.util.Date;

public interface SMSClient {

	public String getSessionID(String login, String password);

	public double getUserBalance(String sessionId);

	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data);

	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate);

	public String sendSmsWithStrSendDate(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate);

	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate, Integer validity);

	public String sendSms(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate, Integer validity);

	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate);

	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate);

	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, Date sendDate, Integer validity);

	public String sendSmsByTimeZone(String sessionId, String sourceAddress, String destinationAddress, String data, String sendDate, Integer validity);

	public String sendSmsBulk(String sessionId, String sourceAddress, String data, String ... destinationAddresses);

	public String sendSmsBulk(String sessionId, String sourceAddress, String data, Date sendDate, String ... destinationAddresses);

	public String sendSmsBulk(String sessionId, String sourceAddress, String data, String sendDate, String ... destinationAddresses);	

	public String sendSmsBulk(String sessionId, String sourceAddress, String data, Date sendDate, Integer validity, String ... destinationAddresses);

	public String sendSmsBulk(String sessionId, String sourceAddress, String data, String sendDate, Integer validity, String ... destinationAddresses);	

	public String getSmsState(String sessionId, String messageId);

	public String getInboxSms(String sessionId, String minDateUTC, String maxDateUTC);

	public String getInboxSms(String sessionId, Date minDateUTC, Date maxDateUTC);

	public String getSmsStatistics(String sessionId, String startDateTime, String endDateTime);

	public String getSmsStatistics(String sessionId, Date startDateTime, Date endDateTime);
}
