package concurrency.guavafutures;

import javax.annotation.Nonnull;

public interface DataFetchingService {

    void fetchData(@Nonnull String url,
                   @Nonnull String source,
                   @Nonnull DataFetchCallback dataFetchCallback,
                   @Nonnull int futureTimeoutMillis);
}
