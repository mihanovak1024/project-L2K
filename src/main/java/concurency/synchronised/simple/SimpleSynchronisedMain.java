package concurency.synchronised.simple;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class SimpleSynchronisedMain implements ThreadCallbacks {

    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
    protected static int LIST_SIZE_LIMIT = 5;

    public static List<Integer> numberList;

    public static void main(String[] args) throws InterruptedException {
        new SimpleSynchronisedMain();
    }

    public SimpleSynchronisedMain() throws InterruptedException {
        numberList = new ArrayList<>();

        ProducerThread producer = new ProducerThread(this);
        ConsumerThread consumer = new ConsumerThread(this);
        log.debug("Starting threads");
        producer.start();
        sleep(100);
        consumer.start();
    }

    @Override
    public void listFull() {
        log.debug("List is full CALLBACK [" + Thread.currentThread() + "]");
    }

    @Override
    public void listEmpty() {
        log.debug("List is empty CALLBACK [" + Thread.currentThread() + "]");
    }

    @Override
    public synchronized List<Integer> getNumberList() {
        return numberList;
    }
}
