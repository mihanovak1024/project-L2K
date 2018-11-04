package main.java.concurency.synchronised;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class SynchronisedMain implements ThreadCallbacks {

    private final static String TAG = SynchronisedMain.class.getName();

    private ProducerThread producer;
    private ConsumerThread consumer;

    int listFullMax = 5;

    public static List<Integer> numberList;

    public static void main(String[] args) {
        new SynchronisedMain();
    }

    public SynchronisedMain() {
        numberList = new ArrayList<>();
        System.out.println("Starting threads");
        producer = new ProducerThread(this);
        consumer = new ConsumerThread(this);
        producer.run();
        consumer.run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (listFullMax < 0) {
                    throw new Error("MAX LIMIT REACHED");
                }
            }
        }).run();
    }

    @Override
    public void listFull() {
        System.out.println("List is full CALLBACK");
        synchronized (this) {
            listFullMax--;
        }
    }

    @Override
    public void listEmpty() {
        System.out.println("List is empty CALLBACK");
    }

    @Override
    public synchronized List<Integer> getNumberList() {
        return numberList;
    }
}
