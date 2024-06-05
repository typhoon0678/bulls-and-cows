package com.baseball.bullsandcows.controller;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baseball.bullsandcows.domain.GameDto;
import com.baseball.bullsandcows.domain.GameForm;
import com.baseball.bullsandcows.domain.User;
import com.baseball.bullsandcows.service.GameService;
import com.baseball.bullsandcows.service.UserService;

@Controller
public class GameController {

	GameService gameService;
	UserService userService;

	@Autowired
	public GameController(GameService gameService, UserService userService) {
		this.gameService = gameService;
		this.userService = userService;
	}

	@GetMapping("/api/checkNum")
	public String findRound() {

		return "/index";
	}

	@PostMapping("/api/checkNum")
	@ResponseBody
	public ResponseEntity<GameDto> conductRound(@RequestBody GameForm gameForm) {

		try {

			Optional<User> one = userService.findOne(gameForm.getUserID());
			User user = one.orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 유저입니다."));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

			switch (gameForm.getGameOption()) {
				case "new" -> {
					return new ResponseEntity<>(gameService.newGame(user), headers, HttpStatus.OK);
				}
				case "next" -> {
					return new ResponseEntity<>(gameService.moveNextStage(user, gameForm.getGameID()), headers,
						HttpStatus.OK);
				}
				case "continue" -> {
					return new ResponseEntity<>(gameService.judgeGame(gameForm.getNum(), gameForm.getGameID(), user),
						headers, HttpStatus.OK);
				}
			}

		} catch (IllegalStateException | IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
