package io.github.bindglam.database;

public interface Database<R, E extends Exception> {
    void start();

    void stop();

    void getResource(ResourceConsumer<R, E> consumer);
}