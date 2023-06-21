package com.jpmc.theater.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public class Movie {
    private static final int MOVIE_CODE_SPECIAL = 1;

    private final String title;
    private String description;
    private final Duration runningTime;
    private final double ticketPrice;
    private final int specialCode;

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

}