package concurrency.synchronised.awaitsignal;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StopwatchTimerTest {

    @Mock
    private StopwatchNotifier mockStopwatchNotifier;

    @Mock
    private ExecutorService stopwatchExecutor;

    private long waitTimeMillis = 50;

    @Before
    public void setup() {
        stopwatchExecutor = Executors.newSingleThreadExecutor();
    }

    @After
    public void teardown() {
    }

    @Test
    public void timerStart_calledOnce_finishedCalled() throws InterruptedException {
        // preconditions

        // test
        StopwatchTimer stopWatchTimer = spy(new StopwatchTimer(mockStopwatchNotifier, stopwatchExecutor));
        stopWatchTimer.start(waitTimeMillis);

        stopwatchExecutor.awaitTermination(60, TimeUnit.MILLISECONDS);

        // assert
        verify(mockStopwatchNotifier, times(1)).finished();
        verify(stopWatchTimer, times(1)).clear();
    }

    @Test
    public void timerStart_calledTwice_finishedCalledOnce() throws InterruptedException {
        // preconditions

        // test
        StopwatchTimer stopWatchTimer = spy(new StopwatchTimer(mockStopwatchNotifier, stopwatchExecutor));
        stopWatchTimer.start(waitTimeMillis);
        stopWatchTimer.start(waitTimeMillis);

        stopwatchExecutor.awaitTermination(100, TimeUnit.MILLISECONDS);

        // assert
        verify(mockStopwatchNotifier, times(1)).finished();
        verify(stopWatchTimer, times(1)).clear();
    }

    @Test
    public void timerStart_calledAndStoppedAndContinued_finishedCalled() throws InterruptedException {
        // preconditions
        waitTimeMillis = 400;

        // test
        final StopwatchTimer stopWatchTimer = spy(new StopwatchTimer(mockStopwatchNotifier, stopwatchExecutor));
        new Thread(new Runnable() {
            @Override
            public void run() {

                stopWatchTimer.start(waitTimeMillis);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopWatchTimer.stop();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopWatchTimer.start(waitTimeMillis);
            }
        }).run();

        stopwatchExecutor.awaitTermination(600, TimeUnit.MILLISECONDS);

        // assert
        verify(mockStopwatchNotifier, times(1)).finished();
        verify(stopWatchTimer, times(1)).clear();
    }

    @Test
    public void timerStart_calledAndStoppedAndContinued_finishedNotCalled() throws InterruptedException {
        // preconditions
        waitTimeMillis = 200;

        // test
        final StopwatchTimer stopWatchTimer = spy(new StopwatchTimer(mockStopwatchNotifier, stopwatchExecutor));
        new Thread(new Runnable() {
            @Override
            public void run() {

                stopWatchTimer.start(waitTimeMillis);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopWatchTimer.stop();
            }
        }).run();

        stopwatchExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);

        // assert
        verify(mockStopwatchNotifier, times(0)).finished();
        verify(stopWatchTimer, times(0)).clear();
    }
}
