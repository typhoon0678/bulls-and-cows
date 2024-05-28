package com.baseball.bullsandcows.domain;

import java.util.List;

public interface NumsMaker {

	List<Integer> make(int minNum, int maxNum, int numOfDigits);
}
