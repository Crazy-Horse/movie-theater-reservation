package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.service.MovieService;
import com.jpmc.theater.service.ReservationService;
import com.jpmc.theater.service.ShowingService;
import com.jpmc.theater.service.TheaterService;
import com.jpmc.theater.util.LocalDateProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TheaterTests {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    MovieService service;

    @Autowired
    ShowingService showingService;

    @Autowired
    TheaterService theaterService;

    @Autowired
    LocalDateProvider localDateProvider;

    @Test
    void totalFeeForCustomer() {
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theaterService.reserve(john, 2, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        assertEquals(reservationService.makeReservation(reservation), 50);
    }

    @Test
    void printMovieSchedule() {
        theaterService.printSchedule();
        try {
            theaterService.printJsonSchedule();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
