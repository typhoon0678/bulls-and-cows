package com.baseball.bullsandcows.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baseball.bullsandcows.domain.Game;

public class SortGame {

	public static List<Game> getRanking(List<Game> games) {

		Map<String, Game> highScoreMap = new HashMap<>();

		for (Game game : games) {
			String email = game.getUser().getEmail();
			Game highestGame = highScoreMap.get(email);

			if (highestGame == null || highestGame.getScore() < game.getScore()) {
				highScoreMap.put(email, game);
			}
		}

		List<Game> filteredScores = new ArrayList<>(highScoreMap.values());
		filteredScores.sort(Collections.reverseOrder(
			Comparator.comparing(Game::getScore)));

		return filteredScores;
	}
}
