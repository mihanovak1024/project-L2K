package main.concurency.synchronised;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class SynchronisedMain implements ThreadCallbacks {

    private final static String TAG = SynchronisedMain.class.getName();
    private static Log4JLogger logger = new Log4JLogger(TAG);

    private ProducerThread producer;
    private ConsumerThread consumer;

    int listFullMax = 5;

    public static List<Integer> numberList;

    public SynchronisedMain() {
        numberList = new ArrayList<>();
        logger.debug("Starting threads");
        producer = new ProducerThread(this);
        consumer = new ConsumerThread(this);
        producer.run();
        consumer.run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(listFullMax < 0){
                    throw new Error("MAX LIMIT REACHED");
                }
            }
        }).run();
    }

    @Override
    public void listFull() {
        logger.info("List is full CALLBACK");
        synchronized (this) {
            listFullMax--;
        }
    }

    @Override
    public void listEmpty() {
        logger.info("List is empty CALLBACK");
    }

    @Override
    public synchronized List<Integer> getNumberList() {
        return numberList;
    }
}
