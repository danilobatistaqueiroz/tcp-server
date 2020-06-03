package com.labs.prova.interfaces;

import java.io.IOException;

import com.labs.prova.exceptions.ClientInputException;
import com.labs.prova.validations.InputQueryResultEnum;

public interface Protocol {
    InputQueryResultEnum readClientInput() throws IOException, ClientInputException;
    void readClientInputs() throws IOException, ClientInputException;
}
