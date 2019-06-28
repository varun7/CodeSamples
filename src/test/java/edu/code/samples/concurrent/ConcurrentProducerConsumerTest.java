package edu.code.samples.concurrent;


import org.junit.Test;

import static edu.code.samples.concurrent.ThreadBasedProducerConsumer.UniDataBroker;
import static edu.code.samples.concurrent.ThreadBasedProducerConsumer.ThreadProducer;
import static edu.code.samples.concurrent.ThreadBasedProducerConsumer.ThreadConsumer;

class ConcurrentProducerConsumerTest {

    @Test
    public void testThreadedProducerConsumer() {
        UniDataBroker broker = new UniDataBroker();
        new Thread(new ThreadProducer(broker)).start();
        new Thread(new ThreadConsumer(broker)).start();
    }
}