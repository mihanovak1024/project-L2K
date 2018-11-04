package main.java.concurency.synchronised;

import java.util.List;

import main.java.concurency.synchronised.ThreadCallbacks;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class ProducerThread extends Thread {

    private final static String TAG = ProducerThread.class.getName();

    private ThreadCallbacks callbacks;

    public ProducerThread(ThreadCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void run() {
        super.run();
        synchronized (this) {
            List<Integer> numberList = callbacks.getNumberList();
            if (numberList.size() < 10) {
                System.out.println("Size pre-adding = " + numberList.size());
                int randomNum = ((int) Math.random()) % 10 + 1;
                System.out.println("Adding element = " + randomNum);
                numberList.add(randomNum);
            } else {
                callbacks.listFull();
            }
        }
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
