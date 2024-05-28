package com.baseball.bullsandcows.dto;

import java.time.LocalDateTime;

public record RankingListViewResponse(String email, int score, LocalDateTime playDate) {
}
