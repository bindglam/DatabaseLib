package com.bindglam.database;

@FunctionalInterface
public interface ResourceConsumer<C, E extends Exception> {
    void accept(C connection) throws E;
}
