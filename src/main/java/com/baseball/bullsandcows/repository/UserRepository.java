package com.baseball.bullsandcows.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.baseball.bullsandcows.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
