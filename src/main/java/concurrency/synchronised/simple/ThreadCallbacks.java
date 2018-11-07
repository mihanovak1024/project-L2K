package concurrency.synchronised.simple;

import java.util.List;

/**
 * Callback to notify the user that the list is:
 * 1) empty -> Consumer deleted all the elements
 * 2) full  -> Producer inserted until the list was full
 */
public interface ThreadCallbacks {

    void listFull();

    void listEmpty();

    List<Integer> getNumberList();
}
