package com.jpmc.theater.service;

import com.jpmc.theater.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ShowingService service;

    public ReservationService(ShowingService service) {
        this.service = service;
    }

    public double makeReservation(Reservation reservation) {
        var audience = reservation.getShowing().getAudienceCount();
        // checking if there's room in the theater before confirming reservation
        if (audience.get() + reservation.getNumOfTickets() <= 50) {
            //System.out.println("Audience count is: " + reservation.getShowing().getAudienceCount().get());
            audience.getAndAdd(reservation.getNumOfTickets());
            // return movie fee
            return service.checkTicketPrice(reservation.getShowing()) * reservation.getNumOfTickets();
        } else {
            throw new IllegalArgumentException("Not enough seats in this theater at this time");
        }
    }
}
