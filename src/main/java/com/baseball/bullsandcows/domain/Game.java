package com.baseball.bullsandcows.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private int score;

	@Column(nullable = false)
	@ColumnDefault("5")
	private int tryCount;

	@Column(nullable = false)
	private String nums;

	@Column(nullable = false)
	private boolean isFinished;

	@Column(nullable = false)
	private LocalDateTime playDate;

	@Builder
	public Game(User user, int score, int tryCount, String nums, boolean isFinished) {
		this.user = user;
		this.score = score;
		this.tryCount = tryCount;
		this.nums = nums;
		this.isFinished = isFinished;
		this.playDate = LocalDateTime.now();
	}
}
