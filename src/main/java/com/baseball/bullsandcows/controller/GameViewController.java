package com.baseball.bullsandcows.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.baseball.bullsandcows.domain.Game;
import com.baseball.bullsandcows.domain.User;
import com.baseball.bullsandcows.dto.BestRankingResponse;
import com.baseball.bullsandcows.dto.MyPageViewResponse;
import com.baseball.bullsandcows.dto.RankingListViewResponse;
import com.baseball.bullsandcows.repository.UserRepository;
import com.baseball.bullsandcows.service.GameService;
import com.baseball.bullsandcows.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class GameViewController {

	private final GameService gameService;
	private final UserService userService;
	private final UserRepository userRepository;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/mypage")
	public String mypage(Model model) {
		Long id = 6L;

		User user = userService.findById(id);
		model.addAttribute("user", user);

		List<Game> gameList = gameService.getGameByUserId(id);
		model.addAttribute("gameList", gameList);

		BestRankingResponse bestRankingResponse = gameService.getBestRanking(user.getId());
		MyPageViewResponse stat = new MyPageViewResponse(
			bestRankingResponse.ranking(),
			bestRankingResponse.bestScore(),
			Math.round(gameList.stream().mapToInt(Game::getScore).average().orElse(0)),
			gameList.size());
		model.addAttribute("stat", stat);

		return "mypage";
	}

	@GetMapping("/ranking")
	public String ranking(Model model) {

		List<RankingListViewResponse> rankingList = gameService.getRanking();
		model.addAttribute("rankingList", rankingList);

		return "ranking";
	}
}
