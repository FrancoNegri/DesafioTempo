package com.example.demo.model.user;

public class SecretToken {

	private final long id;
	private final String token;

	public SecretToken(long id, String token) {
		this.id = id;
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

}
