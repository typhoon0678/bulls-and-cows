package com.baseball.bullsandcows.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baseball.bullsandcows.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
