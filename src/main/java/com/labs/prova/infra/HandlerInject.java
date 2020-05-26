package com.labs.prova.infra;

import com.google.inject.Inject;
import com.labs.prova.interfaces.Handler;

public class HandlerInject {
    private Handler handler;
    @Inject
    public HandlerInject(Handler handler) {
        this.handler = handler;
    }
    public Handler getHandler() {
        return this.handler;
    }
}