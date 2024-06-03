package com.baseball.bullsandcows.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baseball.bullsandcows.domain.Game;
import com.baseball.bullsandcows.dto.BestRankingResponse;
import com.baseball.bullsandcows.dto.RankingListViewResponse;
import com.baseball.bullsandcows.repository.GameRepository;
import com.baseball.bullsandcows.repository.UserRepository;
import com.baseball.bullsandcows.util.SortGame;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GameService {

	private static final Logger log = LoggerFactory.getLogger(GameService.class);
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

	public BestRankingResponse getBestRanking(Long userID) {

		List<Game> sortedGameList = SortGame.getRanking(gameRepository.findAll());

		int bestRecordIndex = IntStream.range(0, sortedGameList.size())
			.filter(i -> userID.equals(sortedGameList.get(i).getUser().getId()))
			.findFirst().orElse(-1);

		int bestScore = (bestRecordIndex != -1) ? sortedGameList.get(bestRecordIndex).getScore() : 0;
		return new BestRankingResponse(userID, bestRecordIndex + 1, bestScore);

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
