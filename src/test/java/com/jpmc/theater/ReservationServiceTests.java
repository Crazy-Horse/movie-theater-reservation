package com.jpmc.theater;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.service.MovieService;
import com.jpmc.theater.service.ReservationService;
import com.jpmc.theater.service.ShowingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationServiceTests {

    @Autowired
    ReservationService reservationService;

    @Autowired
    MovieService movieService;

    @Autowired
    ShowingService showingService;

    @DisplayName("Test movie reservation with no discounts")
    @Test
    void totalFeeNoDiscount() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0),
                3,
                // should be after 4 pm and not on the 7th of month
                LocalDateTime.of(2023, 6, 12, 17, 0)
        );
        var reservation = new Reservation(customer, showing, 3);
        assertTrue(reservationService.makeReservation(reservation) == 37.5);
        assertEquals(3, showing.getAudienceCount().get());
    }

    @DisplayName("Test movie reservation with special movie code")
    @Test
    void totalFeeSpecialMovieDiscount() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                3,
                // should be after 4 pm and not on the 7th of month
                LocalDateTime.of(2023, 6, 12, 17, 0)
        );
        var reservation = new Reservation(customer, showing, 4);
        assertTrue(reservationService.makeReservation(reservation) == 40);
        assertEquals(4, showing.getAudienceCount().get());
    }

    @DisplayName("Test movie reservation with movie midday special")
    @Test
    void totalFeeMiddayMovieDiscount() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                3,
                // should be after 4 pm and not on the 7th of month
                LocalDateTime.of(2023, 6, 12, 12, 0)
        );
        var reservation = new Reservation(customer, showing, 4);
        assertTrue(reservationService.makeReservation(reservation) == 37.5);
        assertEquals(4, showing.getAudienceCount().get());
    }

    @DisplayName("Test movie reservation with movie day of month special")
    @Test
    void totalFeeDayofMonthMovieDiscount() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0),
                3,
                // should be after 4 pm and not on the 7th of month
                LocalDateTime.of(2023, 6, 7, 17, 0)
        );
        var reservation = new Reservation(customer, showing, 4);
        assertTrue(reservationService.makeReservation(reservation) == 46);
        assertEquals(4, showing.getAudienceCount().get());
    }
}
