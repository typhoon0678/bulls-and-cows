package com.baseball.bullsandcows.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baseball.bullsandcows.domain.Game;
import com.baseball.bullsandcows.dto.RankingListViewResponse;
import com.baseball.bullsandcows.repository.GameRepository;
import com.baseball.bullsandcows.repository.UserRepository;
import com.baseball.bullsandcows.util.SortGame;

import lombok.RequiredArgsConstructor;

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
			String email = userRepository.findById(game.getUser().getId()).orElseThrow(() ->
				new IllegalArgumentException("user not found, id : " + game.getUser().getId())).getEmail();

			sortedRankingList.add(new RankingListViewResponse(
				email,
				game.getScore(),
				game.getPlayDate()
			));
		}

		return sortedRankingList;
	}
}
