package com.baseball.bullsandcows.service;

import org.springframework.stereotype.Service;

import com.baseball.bullsandcows.repository.GameRepository;
import com.baseball.bullsandcows.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final UserRepository userRepository;

}
