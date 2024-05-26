package com.baseball.bullsandcows.dto;

import java.time.LocalDateTime;

public record RankingListViewResponse(String username, int score, LocalDateTime playDate) { }
