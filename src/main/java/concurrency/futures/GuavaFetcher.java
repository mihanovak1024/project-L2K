package concurrency.futures;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import common.Util;
import common.httputils.HttpRequestHelper;
import common.httputils.Response;

public class GuavaFetcher implements DataFetchingService {
    private final static int FIXED_NUM_POOL_THREADS = 3;

    private ListeningScheduledExecutorService listeningScheduledExecutorService;
    private ListeningExecutorService listeningExecutorService;

    public GuavaFetcher() {
        listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(FIXED_NUM_POOL_THREADS));
        listeningScheduledExecutorService = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(FIXED_NUM_POOL_THREADS));
    }

    @Override
    public void fetchData(@Nonnull String url,
                          @Nonnull String source,
                          @Nonnull DataFetchCallback dataFetchCallback,
                          @Nonnull int futureTimeoutMillis) {
        Callable dataFetchTask = createDataFetchTask(source, url);
        ListenableFuture<Response> listenableFuture = listeningExecutorService.submit(dataFetchTask);

        ListenableFuture<Response> timeoutHandler =
                Futures.withTimeout(listenableFuture, futureTimeoutMillis, TimeUnit.MILLISECONDS, listeningScheduledExecutorService);

        Futures.addCallback(timeoutHandler, new FutureCallback<Response>() {
            @Override
            public void onSuccess(@Nullable Response response) {
                if (dataFetchCallback != null) {
                    dataFetchCallback.onDataFetchSuccess(response);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (dataFetchCallback != null) {
                    if (throwable instanceof TimeoutException) {
                        dataFetchCallback.onDataFetchTimeout();
                    } else {
                        dataFetchCallback.onDataFetchError();
                    }
                }
            }
        }, MoreExecutors.directExecutor());
    }

    @VisibleForTesting
    protected Callable<Response> createDataFetchTask(String source, String url) {
        Callable dataFetchTask = () -> {
            HttpRequestHelper httpRequestHelper = new HttpRequestHelper();

            // If an error occurs during request,
            // the HttpRequestException is thrown and onFailure(throwable) is called
            InputStream inputStream = httpRequestHelper.makeRequestForUrl(url);

            // If an error occurs during input stream parsing,
            // the InputStreamException is thrown and onFailure(throwable) is called
            String jsonResponse = Util.getStringFromInputStream(inputStream);

            Response response = new Response(source, jsonResponse);
            return response;
        };
        return dataFetchTask;
    }

    @VisibleForTesting
    protected ListeningExecutorService getListeningExecutorService() {
        return listeningExecutorService;
    }

    @VisibleForTesting
    protected ListeningScheduledExecutorService getListeningScheduledExecutorService() {
        return listeningScheduledExecutorService;
    }
}
