package com.baseball.bullsandcows.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckNumRequest {

	private String gameOption;
	private int gameID;
	private int userID;
	private String num;
}
