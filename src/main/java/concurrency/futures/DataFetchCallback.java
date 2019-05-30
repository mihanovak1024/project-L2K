package concurrency.futures;

import javax.annotation.Nonnull;

import common.httputils.Response;

public interface DataFetchCallback {

    void onDataFetchSuccess(@Nonnull Response response);

    void onDataFetchError();

    void onDataFetchTimeout();
}
