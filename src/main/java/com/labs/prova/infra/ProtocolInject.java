package com.labs.prova.infra;

import com.google.inject.Inject;
import com.labs.prova.interfaces.Protocol;

public class ProtocolInject {
    private Protocol protocol;
    @Inject
    public ProtocolInject(Protocol protocol) {
        this.protocol = protocol;
    }
    public Protocol getProtocol() {
        return this.protocol;
    }
}