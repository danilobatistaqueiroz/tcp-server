package com.labs.prova.infra;

import com.google.inject.AbstractModule;
import com.labs.prova.interfaces.Commander;
import com.labs.prova.ImdbCommander;

public class CommanderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Commander.class).to(ImdbCommander.class);
    }
}