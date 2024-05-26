package com.baseball.bullsandcows.controller;

import com.baseball.bullsandcows.domain.Game;
import com.baseball.bullsandcows.domain.User;
import com.baseball.bullsandcows.dto.MyPageViewResponse;
import com.baseball.bullsandcows.dto.RankingListViewResponse;
import com.baseball.bullsandcows.service.GameService;
import com.baseball.bullsandcows.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GameViewController {

    private final GameService gameService;
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        Long id = 1L;

        User user = userService.findById(id);
        model.addAttribute("user", user);

        List<Game> gameList = gameService.getGameByUserId(id);
        model.addAttribute("gameList", gameList);

        MyPageViewResponse stat = new MyPageViewResponse(
                gameList.stream().mapToInt(Game::getScore).average().orElse(0),
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