package com.jss.smsclient;

public class CacheSmsClient extends DefaultSmsClient {

	private String login;

	private String password;

	private String sessionId;

	public CacheSmsClient() {
		this.login = null;
		this.password = null;		
		this.sessionId = null;
	}

	@Override
	public String getSessionID(String login, String password) {
		if (login == null || password == null) {
			throw new IllegalArgumentException();
		}

		if (!login.equals(this.login) || !password.equals(this.password) || this.sessionId == null) {
			this.login = login;
			this.password = password;
			this.sessionId = super.getSessionID(login, password);
		}

		return this.sessionId;
	}
}
