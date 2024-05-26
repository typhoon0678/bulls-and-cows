package com.baseball.bullsandcows.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
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
    private LocalDateTime playDate;

    @Builder
    public Game(Long id, User user, int score, LocalDateTime playDate) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.playDate = playDate;
    }
}

