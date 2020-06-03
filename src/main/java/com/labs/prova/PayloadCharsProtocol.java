package com.labs.prova;

import static com.labs.prova.validations.InputQueryResultEnum.END_OF_QUERY;
import static com.labs.prova.validations.InputQueryResultEnum.ERROR;
import static com.labs.prova.validations.InputQueryResultEnum.VALID;
import static com.labs.prova.validations.InputQueryResultEnum.WARNING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.labs.prova.exceptions.ClientInputException;
import com.labs.prova.infra.CommanderModule;
import com.labs.prova.interfaces.Commander;
import com.labs.prova.interfaces.Protocol;
import com.labs.prova.validations.InputQueryResultEnum;
import com.labs.prova.validations.QueryBuilder;

public class PayloadCharsProtocol implements Protocol {

    private BufferedReader inServer;
    private PrintWriter outClient;
    
    private boolean activeConnection = true;
    
    @Inject
    public PayloadCharsProtocol(@Named("inServer") BufferedReader inServer, @Named("outClient") PrintWriter outClient) {
        this.inServer = inServer;
        this.outClient = outClient;
    }
    
    public void readClientInputs() throws IOException, ClientInputException {
        while (activeConnection) {
            if(readClientInput()==WARNING) {
                activeConnection = false;
            }
        }
    }
    
    public InputQueryResultEnum readClientInput() throws IOException, ClientInputException {
        QueryBuilder builder = new QueryBuilder();
        boolean activeInput = true;
        while (activeInput) {
            int character = inServer.read();
            if(builder.validateInput(character)==VALID) {
                if(builder.isQueryLength()) {
                    builder.queryLengthAppend((char)character);
                } else {
                    builder.queryAppend((char)character);
                    builder.validateEndOfQuery();
                }
            }
            activeInput = getBuildResult(builder);
        }
        return builder.getResult();
    }
    
    boolean getBuildResult(QueryBuilder builder) throws IOException, ClientInputException {
        boolean nextInput = true;
        if(builder.getResult()==END_OF_QUERY) {
            String output = injectCommander().searchMoviesByTitle(builder.getQueryString());
            printOutputLines(output);
            nextInput = false;
        } else if(builder.getResult()==WARNING) {
            nextInput = false;
        } else if(builder.getResult()==ERROR) {
            throw new ClientInputException(builder.getMessage());
        }
        return nextInput;
    }
    
    String[] printOutputLines(String output) {
        String[] results = output.split("\n");
        for(String item : results) {
            outClient.println(item);
        }
        return results;
    }
    
    Commander injectCommander() {
        Injector injector = Guice.createInjector(new CommanderModule());
        return injector.getInstance(Commander.class);
    }
}
