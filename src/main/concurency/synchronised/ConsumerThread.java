package main.concurency.synchronised;

import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class ConsumerThread extends Thread {

    private final static String TAG = ConsumerThread.class.getName();
    private static Log4JLogger logger = new Log4JLogger(TAG);

    private ThreadCallbacks callbacks;

    public ConsumerThread(ThreadCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void run() {
        super.run();
        synchronized (this){
            List<Integer> numberList = callbacks.getNumberList();
            if(numberList.size() > 0){
                logger.debug("Size pre-removing = " + numberList.size());
                logger.debug("Removing element = " + numberList.get(0));
                numberList.remove(0);
            } else{
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
