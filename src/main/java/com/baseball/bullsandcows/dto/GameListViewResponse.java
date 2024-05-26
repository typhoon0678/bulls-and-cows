package com.baseball.bullsandcows.dto;

import java.time.LocalDateTime;

public record GameListViewResponse(Long id, LocalDateTime playDate, int score, Long userID) { }
