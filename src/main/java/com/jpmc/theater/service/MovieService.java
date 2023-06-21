package com.jpmc.theater.service;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class MovieService {

    @Value("${movie.code.special}")
    private String MOVIE_CODE_SPECIAL;

    @Value("${movie.discount.special}")
    private String MOVIE_DISCOUNT_SPECIAL;

    @Value("${movie.midday.discount.special}")
    private String MOVIE_MIDDAY_DISCOUNT_SPECIAL;

    @Value("${movie.month.day.discount.special}")
    private String MOVIE_MONTH_DAY_DISCOUNT_SPECIAL;

    @Value("${movie.first.show.special}")
    private String MOVIE_FIRST_SHOW_DISCOUNT_SPECIAL;

    @Value("${movie.second.show.special}")
    private String MOVIE_SECOND_SHOW_DISCOUNT_SPECIAL;

    public double calculateTicketPrice(Showing showing, Movie movie) {
        return movie.getTicketPrice() - getDiscount(showing, movie);
    }

    private double getDiscount(Showing showing, Movie movie) {
        double specialDiscount = 0;
        double sequenceDiscount = 0;
        LocalTime morning = LocalTime.of(11, 0);
        LocalTime afternoon = LocalTime.of(16, 0);

        if (Integer.parseInt(MOVIE_CODE_SPECIAL) == movie.getSpecialCode()) {
            specialDiscount = movie.getTicketPrice() * Double.parseDouble(MOVIE_DISCOUNT_SPECIAL);  // 20% discount for special movie
        }

        if (showing.getShowStartTime().toLocalTime().isAfter(morning) && showing.getShowStartTime().toLocalTime().isBefore(afternoon))  {
            if (Double.parseDouble(MOVIE_MIDDAY_DISCOUNT_SPECIAL) * movie.getTicketPrice() > specialDiscount) {
                specialDiscount = movie.getTicketPrice() * Double.parseDouble(MOVIE_MIDDAY_DISCOUNT_SPECIAL); // 25% discount for seeing movie in the middle of the day
            }
        }

        if (showing.getShowStartTime().getDayOfMonth() == 7) {
            if (Double.parseDouble(MOVIE_MONTH_DAY_DISCOUNT_SPECIAL) * movie.getTicketPrice() > specialDiscount) {
                specialDiscount = Double.parseDouble(MOVIE_MONTH_DAY_DISCOUNT_SPECIAL); // day of month special discount
            }
        }
        System.out.println("special discount is: " + specialDiscount);

        var showSequence = showing.getSequenceOfTheDay();
        switch (showSequence) {
            case 1: showSequence = 1;
                sequenceDiscount = Double.parseDouble(MOVIE_FIRST_SHOW_DISCOUNT_SPECIAL); // $3 discount for 1st show
                break;
            case 2: showSequence = 2;
                sequenceDiscount = Double.parseDouble(MOVIE_SECOND_SHOW_DISCOUNT_SPECIAL); // $2 discount for 2nd show
                break;
            default:
                break;
        }

//        else {
//            throw new IllegalArgumentException("failed exception");
//        }

        // biggest discount wins
        return specialDiscount > sequenceDiscount ? specialDiscount : sequenceDiscount;
    }
}
