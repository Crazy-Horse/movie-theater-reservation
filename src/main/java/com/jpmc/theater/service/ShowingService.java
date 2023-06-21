package com.jpmc.theater.service;

import com.jpmc.theater.model.Showing;
import org.springframework.stereotype.Service;

@Service
public class ShowingService {

    private final MovieService movieService;

    public ShowingService(MovieService movieService) {
        this.movieService = movieService;
    }

    public double checkTicketPrice(Showing showing) {
        return movieService.calculateTicketPrice(showing, showing.getMovie());
    }
}
