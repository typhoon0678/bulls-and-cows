package com.baseball.bullsandcows.dto;

import java.util.Map;

import com.baseball.bullsandcows.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;

	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		return ofKakao("id", attributes);
	}
	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> account = (Map<String, Object>) attributes.get("profile");

		return OAuthAttributes.builder()
			.name((String) account.get("nickname"))
			.email((String) response.get("email"))
			.attributes(response)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public User toEntity() {
		return User.builder()
			.email(email)
			.build();
	}
}