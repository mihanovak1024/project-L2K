package concurrency.synchronised.awaitsignal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

/**
 * StopwatchTimer starts with a specified execution time, during which
 * it can either complete successfully or it can be interrupted (and continued later on).
 */
public class StopwatchTimer {
    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private volatile long startTimeMillis;
    private volatile long millisToFinish = -1;
    private boolean isRunning;

    private StopwatchNotifier stopwatchNotifier;
    private ExecutorService stopwatchExecutor;

    private Lock stopwatchLock = new ReentrantLock();
    private Condition stopwatchCondition = stopwatchLock.newCondition();

    public StopwatchTimer(StopwatchNotifier stopwatchNotifier, ExecutorService stopwatchExecutor) {
        this.stopwatchNotifier = stopwatchNotifier;
        this.stopwatchExecutor = stopwatchExecutor;
    }

    /**
     * Starts the StopwatchTimer with some execution time.
     * <p>
     * If it's already running, it won't run again and if
     * the millisToFinish is higher than zero, it resumes
     * else it starts from the beginning.
     *
     * @param stopwatchTimeMillis
     */
    public void start(long stopwatchTimeMillis) {
        if (isRunning) {
            log.debug("Stopwatch is already running. Returning...");
            return;
        }
        isRunning = true;
        startTimeMillis = System.currentTimeMillis();

        if (millisToFinish < 0) {
            log.debug("Starting stopwatch for " + stopwatchTimeMillis / 1000 + " seconds.");
            // No need for locking, as no-one can alter it (stopwatch is stopped at this moment).
            millisToFinish = stopwatchTimeMillis;
        } else {
            updateAndGetTimeToFinish();
            log.debug("Continuing stopwatch for " + millisToFinish + " seconds.");
        }
        startStopwatchExecutor();
    }

    /**
     * Stops the StopwatchTimer and updates the time to finish,
     * so it can be resumed from current point onwards.
     */
    public void stop() {
        if (!isRunning) {
            log.debug("Stopwatch was already stopped. Returning...");
            return;
        }
        isRunning = false;
        stopStopwatchExecutor();
        updateAndGetTimeToFinish();
    }

    @VisibleForTesting
    protected void clear() {
        startTimeMillis = 0;
        millisToFinish = -1;
        isRunning = false;
    }

    public long updateAndGetTimeToFinish() {
        stopwatchLock.lock();
        try {
            millisToFinish -= System.currentTimeMillis() - startTimeMillis;
            return millisToFinish;
        } finally {
            stopwatchLock.unlock();
        }
    }

    /**
     * Starts the StopwatchTimer executor and waits for millisToFinish
     * hoping for a successful completion, else it was interrupted with
     * the {@link StopwatchTimer#stop()} method.
     */
    private void startStopwatchExecutor() {
        stopwatchExecutor.submit(new Runnable() {
            @Override
            public void run() {
                stopwatchLock.lock();
                try {
                    if (!stopwatchCondition.await(millisToFinish, TimeUnit.MILLISECONDS)) {
                        notifyStopwatchFinished();
                    } else {
                        log.debug("Stopwatch is stopped...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    stopwatchLock.unlock();
                }
            }
        });
    }

    /**
     * Interrupts the StopwatchTimer executor.
     */
    private void stopStopwatchExecutor() {
        stopwatchLock.lock();
        try {
            stopwatchCondition.signal();
        } finally {
            stopwatchLock.unlock();
        }
    }

    /**
     * Notifies the user that the StopwatchTimer completed successfully.
     */
    private void notifyStopwatchFinished() {
        clear();
        stopwatchNotifier.finished();
    }
}
