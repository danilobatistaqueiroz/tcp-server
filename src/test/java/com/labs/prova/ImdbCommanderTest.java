package com.labs.prova;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImdbCommanderTest {

    @Test
    @DisplayName("search movies by title using imdb")
    void searchMoviesByTitle() throws IOException {
        ImdbCommander imdbCommander = new ImdbCommander();
        String movies = imdbCommander.searchMoviesByTitle("mib");
        int diviser = movies.indexOf(":");
        assertTrue(diviser>0);
        int payloadLength = Integer.valueOf(movies.substring(0,diviser));
        assertTrue(payloadLength>0);
        assertTrue(movies.indexOf("Homens de Preto III")>0);
    }

}
