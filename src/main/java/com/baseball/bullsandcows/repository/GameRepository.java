package com.baseball.bullsandcows.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baseball.bullsandcows.domain.Game;
import com.baseball.bullsandcows.domain.User;

import jakarta.transaction.Transactional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	Optional<Game> findByIdAndUser(Long id, User user);

	@Transactional
	@Modifying
	@Query("update Game g set g.tryCount = g.tryCount - 1 where g.id = :id and g.user = :user")
	int judgeGameByIdAndUser(@Param("id") Long id, @Param("user") User user);

	@Transactional
	@Modifying
	@Query("update Game g set  g.nums = :nums, g.score = g.score + :score, g.tryCount = 5 "
		+ "where g.id = :id and g.user = :user")
	int updateNextStageGame(
		@Param("nums") String nums,
		@Param("score") int score,
		@Param("id") Long id,
		@Param("user") User user);
}
