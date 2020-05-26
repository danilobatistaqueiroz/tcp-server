package com.labs.prova;

import static com.labs.prova.validations.InputValidEnum.BREAK;
import static com.labs.prova.validations.InputValidEnum.CONTINUE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.labs.prova.infra.CommanderModule;
import com.labs.prova.interfaces.Commander;
import com.labs.prova.interfaces.Handler;
import com.labs.prova.validations.InputValid;

public class ClientHandler implements Handler {
    Socket clientSocket;
    PrintWriter out;
    
    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    
    @Inject
    public void setClientSocket(@Named("socket") Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            readClientCommands(in);
        } catch (Exception ex) {
            logger.error(String.format("Client handler exception: %s", ex.getMessage()), ex);
        } finally {
            out.close();
            closeClientSocket();
        }
    }

    private void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            logger.error(String.format("Client Socket exception: %s", ex.getMessage()), ex);                
        }
    }

    String readClientCommands(BufferedReader in) throws IOException {
        String inputLine;
        InputValid validation = new InputValid();
        while ((inputLine = in.readLine()) != null) {
            int divisor = inputLine.indexOf(':');
            String query = inputLine.substring(divisor+1);
            validation.validateInput(inputLine, query, divisor);
            if(validation.getValid()==CONTINUE) {
                out.println(validation.getMessage());
                continue;
            } else if(validation.getValid()==BREAK) {
                out.println(validation.getMessage());
                return validation.getMessage();
            }
            query = StringEscapeUtils.escapeHtml4(query);
            injectCommander().printMovies(out, query);
        }
        return validation.getMessage();
    }
    
    Commander injectCommander() {
        Injector injector = Guice.createInjector(new CommanderModule());
        return injector.getInstance(Commander.class);
    }
   
}

