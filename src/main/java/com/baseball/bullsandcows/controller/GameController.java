package com.baseball.bullsandcows.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baseball.bullsandcows.domain.GameDTO;
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
	public String conductRound(@RequestBody GameForm gameForm, Model model) {

		Optional<User> one = userService.findOne(gameForm.getUserID());

		User user = one.orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 유저입니다."));

		switch (gameForm.getGameOption()) {
			case "new" -> {
				GameDTO gameDTO = gameService.newGame(user);
				model.addAttribute("gameDTO", gameDTO);
				return "/index";
			}
			case "next" -> {
				GameDTO gameDTO = gameService.moveNextStage(user, gameForm.getGameID());
				model.addAttribute("gameDTO", gameDTO);
				return "/index";
			}
			case "continue" -> {

				GameDTO gameDTO = gameService.judgeGame(gameForm.getNum(), gameForm.getGameID(), user);
				model.addAttribute("gameDTO", gameDTO);
				return "/index";
			}
		}

		return "redirect:/404error";
	}
}
