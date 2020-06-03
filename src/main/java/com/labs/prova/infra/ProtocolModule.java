package com.labs.prova.infra;

import java.io.BufferedReader;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.labs.prova.PayloadCharsProtocol;
import com.labs.prova.interfaces.Protocol;

public class ProtocolModule extends AbstractModule {
    
    private static Logger logger = LoggerFactory.getLogger(ProtocolModule.class);

    private BufferedReader inServer;
    private PrintWriter outClient;
    
    public ProtocolModule(BufferedReader inServer, PrintWriter outClient) {
        this.inServer = inServer;
        this.outClient = outClient;
    }
    
    @Override
    protected void configure() {
        try {
           bind(Protocol.class)
              .toConstructor(
                      PayloadCharsProtocol.class.getConstructor(BufferedReader.class, PrintWriter.class)
               );
        } catch (NoSuchMethodException | SecurityException e) {
           logger.error("Required constructor missing");
        } 
        bind(BufferedReader.class).annotatedWith(Names.named("inServer")).toInstance(inServer);
        bind(PrintWriter.class).annotatedWith(Names.named("outClient")).toInstance(outClient);
     }
}