package com.baseball.bullsandcows.repository;

import com.baseball.bullsandcows.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
