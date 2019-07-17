package concurrency.rxjava.network.interfaces;

import java.util.List;

public interface DataProvider<T> {

    T getSingleData(String url, Class<T> clazz);

    List<T> getDataList(String url, Class<T> clazz);
}
