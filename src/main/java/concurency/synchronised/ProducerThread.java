package concurency.synchronised;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by miha.novak on 13/07/2017.
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
        synchronized (this) {
            List<Integer> numberList = callbacks.getNumberList();
            if (numberList.size() < 10) {
                log.debug("Size pre-adding = " + numberList.size());
                int randomNum = ((int) Math.random()) % 10 + 1;
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
