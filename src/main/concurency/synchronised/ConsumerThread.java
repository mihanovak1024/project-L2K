package main.concurency.synchronised;

import java.util.List;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class ConsumerThread extends Thread {

    private final static String TAG = ConsumerThread.class.getName();

    private ThreadCallbacks callbacks;

    public ConsumerThread(ThreadCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void run() {
        super.run();
        synchronized (this) {
            List<Integer> numberList = callbacks.getNumberList();
            if (numberList.size() > 0) {
                System.out.println("Size pre-removing = " + numberList.size());
                System.out.println("Removing element = " + numberList.get(0));
                numberList.remove(0);
            } else {
                callbacks.listEmpty();
            }
        }
        try {
            sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
