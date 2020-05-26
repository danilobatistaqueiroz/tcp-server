package com.labs.prova.infra;

import java.net.Socket;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class HandlerModule extends AbstractModule {
    private Socket socket;
    public HandlerModule(Socket socket){
        this.socket= socket;
    }
    @Override
    protected void configure() {
        bind(Socket.class)
           .annotatedWith(Names.named("socket"))
           .toInstance(socket);
    }
}