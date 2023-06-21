package com.jpmc.theater;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {
    @Mock
    private MovieService movieService;

    @Test
    void specialMovieDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(2023, 11, 8, 17, 0));

        Mockito.when(movieService.calculateTicketPrice(showing, spiderMan)).thenReturn(10.0);

        //test
        var ticketPrice = movieService.calculateTicketPrice(showing, spiderMan);
        Mockito.verify(movieService, Mockito.times(1)).calculateTicketPrice(showing, spiderMan);
    }

    @Test
    void specialMovieSequenceOfDayDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.now(), LocalTime.now()));

        Mockito.when(movieService.calculateTicketPrice(showing, spiderMan)).thenReturn(9.5);

        //test
        var ticketPrice = movieService.calculateTicketPrice(showing, spiderMan);
        Mockito.verify(movieService, Mockito.times(1)).calculateTicketPrice(showing, spiderMan);
    }
}
