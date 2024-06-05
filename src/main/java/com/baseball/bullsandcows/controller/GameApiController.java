package com.baseball.bullsandcows.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baseball.bullsandcows.dto.CheckNumRequest;
import com.baseball.bullsandcows.dto.CheckNumResponse;
import com.baseball.bullsandcows.service.GameService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class GameApiController {

	private final GameService gameService;

	@PostMapping("/checkNum")
	public CheckNumResponse checkNum(@RequestBody CheckNumRequest checkNumRequest) {

		return CheckNumResponse.builder()
			.gameID(checkNumRequest.getGameID())
			.userID(checkNumRequest.getUserID())
			.addScore(100)
			.totalScore(1000)
			.tryCount(4)
			.gameResult("next")  // "continue", "next", "gameOver"
			.judgement(new int[] {1, 1})  // "strike", "ball", ("out")
			.build();
	}
}
