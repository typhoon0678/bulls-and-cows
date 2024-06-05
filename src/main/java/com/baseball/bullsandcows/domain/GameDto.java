package com.baseball.bullsandcows.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameDto {

	Long gameID;
	Long userID;
	int addScore;
	int totalScore;
	int tryCount;
	String gameResult;
	List<Integer> judgement;

}
