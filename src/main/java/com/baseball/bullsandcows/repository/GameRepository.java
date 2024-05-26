package com.baseball.bullsandcows.repository;

import com.baseball.bullsandcows.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
