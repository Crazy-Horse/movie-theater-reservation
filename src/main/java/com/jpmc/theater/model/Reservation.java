package com.jpmc.theater.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reservation {
    private final Customer customer;
    private final Showing showing;
    private final int numOfTickets;
}