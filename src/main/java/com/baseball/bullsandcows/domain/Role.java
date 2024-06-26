package com.baseball.bullsandcows.domain;

public enum Role {
	USER("USER_KEY"),
	ADMIN("ADMIN_KEY");

	private final String key;

	Role(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}


	int b =3;
}