package com.baseball.bullsandcows.service;

import com.baseball.bullsandcows.domain.Game;
import com.baseball.bullsandcows.dto.RankingListViewResponse;
import com.baseball.bullsandcows.repository.GameRepository;
import com.baseball.bullsandcows.repository.UserRepository;
import com.baseball.bullsandcows.util.SortGame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.maxBy;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(Long id) {
        return gameRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found")
        );
    }

    public List<Game> getGameByUserId(Long userId) {
        return gameRepository.findAll().stream()
                .filter(game -> Objects.equals(game.getUser().getId(), userId))
                .collect(Collectors.toList());
    }

    public List<RankingListViewResponse> getRanking() {

        List<Game> sortedGameList = SortGame.getRanking(gameRepository.findAll());

        List<RankingListViewResponse> sortedRankingList = new ArrayList<>();

        for (Game game : sortedGameList) {
            String username = userRepository.findById(game.getUser().getId()).orElseThrow(() ->
                    new IllegalArgumentException("user not found, id : " + game.getUser().getId())).getUsername();

            sortedRankingList.add(new RankingListViewResponse(
                    username,
                    game.getScore(),
                    game.getPlayDate()
            ));
        }

        return sortedRankingList;
    }
}
