package com.labs.prova.interfaces;

import java.net.Socket;

import com.google.inject.ImplementedBy;
import com.labs.prova.ClientHandler;

@ImplementedBy(ClientHandler.class)
public interface Handler extends Runnable {
    public void setClientSocket(Socket clientSocket);
}
