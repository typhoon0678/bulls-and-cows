package com.baseball.bullsandcows.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baseball.bullsandcows.domain.User;
import com.baseball.bullsandcows.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public Optional<User> findOne(Long id) {
		return userRepository.findById(id);
	}

}
