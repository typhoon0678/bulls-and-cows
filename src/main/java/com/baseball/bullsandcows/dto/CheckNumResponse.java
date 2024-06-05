package com.baseball.bullsandcows.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckNumResponse {

	private int gameID;
	private int userID;
	private int addScore;
	private int totalScore;
	private int tryCount;
	private String gameResult;
	private int[] judgement; // strike, ball, (out)

}
