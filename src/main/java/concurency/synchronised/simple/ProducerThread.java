package concurency.synchronised.simple;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by miha.novak on 13/07/2017.
 */

/**
 * ProducerThread is responsible for producing the
 * elements into the numberList.
 */
public class ProducerThread extends Thread {
    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private ThreadCallbacks callbacks;

    public ProducerThread(ThreadCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void run() {
        super.run();
        // Number 15 doesn't really matter here,
        // it's just a random non-infinite number.
        for (int i = 0; i < 15; i++) {
            // ThreadCallbacks is implemented in SimpleSynchronisedMain
            // thus it can be a shared monitor for the consumer and the producer.
            synchronized (callbacks) {
                List<Integer> numberList = callbacks.getNumberList();
                if (numberList.size() < SimpleSynchronisedMain.LIST_SIZE_LIMIT) {
                    log.debug("Size pre-adding = " + numberList.size());
                    int randomNum = (int) (Math.random() * 10 + 1);
                    log.debug("Adding element = " + randomNum);
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
}
