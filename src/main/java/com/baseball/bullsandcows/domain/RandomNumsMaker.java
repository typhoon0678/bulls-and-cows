package com.baseball.bullsandcows.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumsMaker implements NumsMaker {

	Random random = new Random();

	@Override
	public List<Integer> make(int minNum, int maxNum, int numOfDigits) {

		List<Integer> randomNums = new ArrayList<>();

		do {
			int randomNum = makeEachNum(minNum, maxNum);

			if (randomNums.contains(randomNum)) {
				continue;
			}

			randomNums.add(randomNum);

		} while (randomNums.size() < numOfDigits);

		return randomNums;
	}

	private int makeEachNum(int minNum, int maxNum) {
		return random.nextInt(minNum, maxNum);
	}
}
