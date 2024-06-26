package com.baseball.bullsandcows.domain;

import java.time.LocalDateTime;
import java.util.regex.Pattern;


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

import lombok.ToString;


@Entity
@Getter
@NoArgsConstructor

@ToString

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

		validateNums(nums);

		this.user = user;
		this.score = score;
		this.tryCount = tryCount;
		this.nums = nums;
		this.isFinished = isFinished;
		this.playDate = LocalDateTime.now();
	}

	private void validateNums(String nums) {
		validateNumsSize(nums);
		validateNumsRange(nums);
	}

	private void validateNumsSize(String nums) {

		if (nums.length() != 3) {
			throw new IllegalStateException("[ERROR] 3자리 숫자만 가능합니다.");
		}
	}

	private void validateNumsRange(String nums) {

		Pattern pattern = Pattern.compile("^[1-9]");

		String[] split = nums.split("");

		for (String eachNum : split) {

			if (!pattern.matcher(eachNum).matches()) {
				throw new IllegalStateException("[ERROR] 1~9 범위의 숫자만 가능합니다.");
			}
		}

	}

}
