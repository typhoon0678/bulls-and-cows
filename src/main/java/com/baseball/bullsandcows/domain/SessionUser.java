package com.baseball.bullsandcows.domain;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

	private String email;

	public SessionUser(User user) {
		this.email = user.getEmail();
	}

	// Getter, Setter 등 필요한 메서드 구현
}