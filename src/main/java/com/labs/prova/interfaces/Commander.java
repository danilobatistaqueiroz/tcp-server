package com.labs.prova.interfaces;

import java.io.IOException;
import java.io.PrintWriter;

public interface Commander {
    public void printMovies(PrintWriter out, String criteria) throws IOException;
}
