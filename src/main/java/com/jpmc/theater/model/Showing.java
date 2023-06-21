package com.jpmc.theater.model;

import com.jpmc.theater.model.Movie;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@RequiredArgsConstructor
public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private AtomicInteger audienceCount;
    private LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
        this.audienceCount = new AtomicInteger(0);
    }
    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }
}
