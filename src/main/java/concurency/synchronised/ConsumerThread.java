package concurency.synchronised;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by miha.novak on 13/07/2017.
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
        synchronized (this) {
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
            sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
