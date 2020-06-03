package com.labs.prova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.labs.prova.infra.ProtocolModule;
import com.labs.prova.interfaces.Handler;
import com.labs.prova.interfaces.Protocol;

public class ClientHandler implements Handler {
    Socket clientSocket;
    PrintWriter outClient;
    
    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    
    @Inject
    public void setClientSocket(@Named("socket") Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    public void run() {
        try (BufferedReader inServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            outClient = new PrintWriter(clientSocket.getOutputStream());
            injectProtocol(inServer, outClient).readClientInputs();
        } catch (Exception ex) {
            logger.error(String.format("Client handler exception: %s", ex.getMessage()), ex);
        } finally {
            outClient.close();
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
    
    Protocol injectProtocol(BufferedReader inServer, PrintWriter outClient) {
        Injector injector = Guice.createInjector(new ProtocolModule(inServer, outClient));
        return injector.getInstance(Protocol.class);
    }
   
}

