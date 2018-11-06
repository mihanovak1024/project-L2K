package concurency.synchronised.simple;

import java.util.List;

/**
 * Created by miha.novak on 13/07/2017.
 */
public interface ThreadCallbacks {

    void listFull();

    void listEmpty();

    List<Integer> getNumberList();
}
