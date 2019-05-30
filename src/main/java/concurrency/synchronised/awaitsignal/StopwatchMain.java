package concurrency.synchronised.awaitsignal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple test with locks, conditions and ExecutorService.
 * <p>
 * {@link StopwatchTimer} is started on the mainExecutor ({@link ExecutorService})
 * with {@link StopwatchMain#STOPWATCH_RUN_MILLISECONDS} executing time and
 * the mainExecutor awaits for either {@link StopwatchTimer} interrupting it or
 * mainExecutor interrupting {@link StopwatchTimer} as it took longer than mainExecutor
 * was waiting for the call.
 */
public class StopwatchMain implements StopwatchNotifier {
    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
    private static final int STOPWATCH_RUN_MILLISECONDS = 5 * 1000;
    private static final int MAIN_WAIT_SECONDS = 6; // <- StopwatchTimer interrupts it
    // private static final int MAIN_WAIT_SECONDS = 4; // <- mainExecutor stops the StopwatchTimer

    private Lock mainLock = new ReentrantLock();
    private Condition mainCond = mainLock.newCondition();

    public static void main(String[] args) {
        new StopwatchMain();
    }

    public StopwatchMain() {
        ExecutorService mainExecutor = Executors.newSingleThreadExecutor();
        ExecutorService stopwatchExecutor =  Executors.newSingleThreadExecutor();
        mainExecutor.submit(new Runnable() {
            @Override
            public void run() {
                StopwatchTimer stopwatchTimer = new StopwatchTimer(StopwatchMain.this, stopwatchExecutor);
                stopwatchTimer.start(STOPWATCH_RUN_MILLISECONDS);
                mainLock.lock();
                try {
                    if (mainCond.await(MAIN_WAIT_SECONDS, TimeUnit.SECONDS)) {
                        log.debug("Stopwatch ended, this was signaled...");
                    } else {
                        log.debug("Stopwatch hasn't ended yet, time elapsed. Stopping stopwatch...");
                        stopwatchTimer.stop();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mainLock.unlock();
                }
            }
        });
    }

    @Override
    public void finished() {
        log.debug("Finished!");
        mainLock.lock();
        try {
            mainCond.signal();
        } finally {
            mainLock.unlock();
        }
    }
}
