package com.baseball.bullsandcows.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baseball.bullsandcows.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
