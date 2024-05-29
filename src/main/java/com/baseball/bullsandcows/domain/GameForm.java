package com.baseball.bullsandcows.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameForm {

	String gameOption;
	Long gameID;
	Long userID;
	String ballNum;
}
