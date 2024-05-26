package com.baseball.bullsandcows.service;

import com.baseball.bullsandcows.domain.User;
import com.baseball.bullsandcows.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}