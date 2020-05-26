package com.labs.prova;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.labs.prova.exceptions.InvalidPortException;
import com.labs.prova.infra.HandlerInject;
import com.labs.prova.infra.HandlerModule;

public class Application {

    boolean active = true;
    private static Logger logger = LoggerFactory.getLogger(Application.class);  
    
    public static void main(String argv[]) throws Exception {
        Application application = new Application();
        int port = application.getPort(argv);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server is listening on port {}", port);
            while(application.active) {
                application.injectHandler(serverSocket);
            }
        } catch (IOException ex) {
            logger.error(String.format("Server exception: %s", ex.getMessage()), ex);
        }
    }
    
    int getPort(String argv[]) {
        if (argv.length < 1) throw new InvalidPortException("Port not informed!");
        int port = Integer.parseInt(argv[0]);
        if(port < 1024 || port > 49151) {
            throw new InvalidPortException("Invalid port range, choose between 1024 and 49151!");
        }
        return port;
    }

    void injectHandler(ServerSocket serverSocket) throws IOException {
        Injector injector = Guice.createInjector(new HandlerModule(serverSocket.accept()));
        HandlerInject handlerInject = injector.getInstance(HandlerInject.class);
        new Thread(handlerInject.getHandler()).start();
    }
    
}

