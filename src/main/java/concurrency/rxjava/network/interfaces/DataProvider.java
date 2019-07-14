package concurrency.rxjava.network.interfaces;

import java.util.List;

public interface DataProvider<T> {

    T getSingleData(String url);

    List<T> getDataList(String url);
}
