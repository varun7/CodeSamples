package edu.code.samples.concurrent;

public interface Broker<V> {

    void put(V data);

    V take();

}
