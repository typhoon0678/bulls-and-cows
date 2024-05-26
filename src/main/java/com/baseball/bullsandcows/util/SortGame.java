package com.baseball.bullsandcows.util;

import com.baseball.bullsandcows.domain.Game;

import java.util.*;

public class SortGame {

    public static List<Game> getRanking(List<Game> games) {

        Map<String, Game> highScoreMap = new HashMap<>();

        for (Game game : games) {
            String username = game.getUser().getUsername();
            Game highestGame = highScoreMap.get(username);

            if (highestGame == null || highestGame.getScore() < game.getScore()) {
                highScoreMap.put(username, game);
            }
        }

        List<Game> filteredScores = new ArrayList<>(highScoreMap.values());
        filteredScores.sort(Collections.reverseOrder(
                Comparator.comparing(Game::getScore)));

        return filteredScores;
    }
}
