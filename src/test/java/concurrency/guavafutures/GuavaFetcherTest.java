package concurrency.guavafutures;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GuavaFetcherTest {

    @Mock
    private DataFetchCallback mockDataFetchCallback;

    @Mock
    private Callable mockDataFetchTask;

    private GuavaFetcher guavaFetcher;

    @Before
    public void setup() {
        guavaFetcher = spy(new GuavaFetcher());
    }

    @After
    public void teardown() {
    }

    /**
     * Wait for the max task limit operation time is reached (timeout)
     *
     * @param timeout
     * @throws InterruptedException
     */
    private void waitForTestsToExecute(int timeout) throws InterruptedException {
        // Wait for the max task limit operation time is reached (futureTimeoutMillis)
        guavaFetcher.getListeningExecutorService().shutdown();
        guavaFetcher.getListeningExecutorService().awaitTermination(timeout, TimeUnit.MILLISECONDS);
        guavaFetcher.getListeningScheduledExecutorService().shutdown();
        guavaFetcher.getListeningScheduledExecutorService().awaitTermination(timeout, TimeUnit.MILLISECONDS);
    }

    @Test
    public void fetchData_dataFetchCallbacksCalled_onDataFetchSuccessCalled() throws InterruptedException {
        // given
        String url = "testUrl";
        String source = "test";
        int futureTimeoutMillis = 200;

        doReturn(mockDataFetchTask).when(guavaFetcher).createDataFetchTask(any(), any());

        // when
        guavaFetcher.fetchData(url, source, mockDataFetchCallback, futureTimeoutMillis);

        waitForTestsToExecute(futureTimeoutMillis);

        // then
        verify(mockDataFetchCallback, times(1)).onDataFetchSuccess(any());
        verify(mockDataFetchCallback, times(0)).onDataFetchError();
        verify(mockDataFetchCallback, times(0)).onDataFetchTimeout();
    }

    @Test
    public void fetchData_dataFetchCallbacksCalled_onDataFetchErrorCalled() throws Exception {
        // given
        String url = "testUrl";
        String source = "test";
        int futureTimeoutMillis = 200;

        doReturn(mockDataFetchTask).when(guavaFetcher).createDataFetchTask(any(), any());
        doAnswer(invocationOnMock -> {
            throw new NullPointerException();
        }).when(mockDataFetchTask).call();

        // when
        guavaFetcher.fetchData(url, source, mockDataFetchCallback, futureTimeoutMillis);

        waitForTestsToExecute(futureTimeoutMillis);

        // then
        verify(mockDataFetchCallback, times(0)).onDataFetchSuccess(any());
        verify(mockDataFetchCallback, times(1)).onDataFetchError();
        verify(mockDataFetchCallback, times(0)).onDataFetchTimeout();
    }

    @Test
    public void fetchData_dataFetchCallbacksCalled_onDataFetchTimeoutCalled() throws Exception {
        // given
        String url = "testUrl";
        String source = "test";
        int futureTimeoutMillis = 200;

        doReturn(mockDataFetchTask).when(guavaFetcher).createDataFetchTask(any(), any());
        doAnswer(invocationOnMock -> {
            Thread.sleep(400);
            return null;
        }).when(mockDataFetchTask).call();

        // when
        guavaFetcher.fetchData(url, source, mockDataFetchCallback, futureTimeoutMillis);

        waitForTestsToExecute(futureTimeoutMillis);

        // then
        verify(mockDataFetchCallback, times(0)).onDataFetchSuccess(any());
        verify(mockDataFetchCallback, times(0)).onDataFetchError();
        verify(mockDataFetchCallback, times(1)).onDataFetchTimeout();
    }
}
