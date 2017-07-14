package main.concurency.synchronised;

import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class ProducerThread extends Thread {

    private final static String TAG = ProducerThread.class.getName();
    private static Log4JLogger logger = new Log4JLogger(TAG);

    private ThreadCallbacks callbacks;

    public ProducerThread(ThreadCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void run() {
        super.run();
        synchronized (this){
            List<Integer> numberList = callbacks.getNumberList();
            if(numberList.size() < 10){
                logger.debug("Size pre-adding = " + numberList.size());
                int randomNum = ((int) Math.random()) % 10 + 1;
                logger.debug("Adding element = " + randomNum);
                numberList.add(randomNum);
            } else{
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
