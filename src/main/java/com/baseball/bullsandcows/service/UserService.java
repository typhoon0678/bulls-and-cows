package com.baseball.bullsandcows.service;

import org.springframework.stereotype.Service;

import com.baseball.bullsandcows.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
}
