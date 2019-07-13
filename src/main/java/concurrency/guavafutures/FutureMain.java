package concurrency.guavafutures;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.httputils.Response;

public class FutureMain implements DataFetchCallback {
    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
    private static final int FUTURE_TIMEOUT_MILLIS = 15 * 1000; // 15 seconds
    private long dataFetchStart;

    public static void main(String[] arg) {
        new FutureMain();
    }

    public FutureMain() {
        DataFetchingService dataFetchingService = new GuavaFetcher();

        String url = "https://jobs.github.com/positions.json?description=android&location=sf";
        String source = "GitHub";

        //        String url2 = "https://api.coindesk.com/v1/bpi/currentprice.json";
        //        String source2 = "CoinDesk";

        dataFetchStart = System.currentTimeMillis();
        log.debug("[FutureMain] [currentThread: " + Thread.currentThread().getName() + "]");
        dataFetchingService.fetchData(url, source, this, FUTURE_TIMEOUT_MILLIS);
        //        dataFetchingService.fetchData(url2, source2, this, FUTURE_TIMEOUT_MILLIS);
    }

    private void dataFetchStop(@Nonnull String fromMethod) {
        long dataFetchLength = System.currentTimeMillis() - dataFetchStart;
        log.debug("[" + fromMethod + "] [currentThread: " + Thread.currentThread().getName() + "]  dataFetchLengthMillis = " +
                  dataFetchLength);
    }

    @Override
    public void onDataFetchSuccess(@Nonnull Response response) {
        dataFetchStop("onDataFetch_Success");
        String data = response.getData();
        String source = response.getSource();
        String outcome = data != null && data.length() > 0 ? "Nonempty" : "Empty/Null";
        log.debug("[onDataFetchSuccess] source = " + source + ", data = " + outcome);
    }

    @Override
    public void onDataFetchError() {
        dataFetchStop("onDataFetch_Error");
    }

    @Override
    public void onDataFetchTimeout() {
        dataFetchStop("onDataFetch_Timeout");
    }
}
