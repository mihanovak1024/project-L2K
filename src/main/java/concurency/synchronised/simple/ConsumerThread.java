package concurency.synchronised.simple;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConsumerThread is responsible for consuming the
 * elements for the numberList.
 */
public class ConsumerThread extends Thread {
    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private ThreadCallbacks callbacks;

    public ConsumerThread(ThreadCallbacks callbacks) {
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
                if (numberList.size() > 0) {
                    log.debug("Size pre-removing = " + numberList.size());
                    log.debug("Removing element = " + numberList.get(0));
                    numberList.remove(0);
                } else {
                    callbacks.listEmpty();
                }
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
