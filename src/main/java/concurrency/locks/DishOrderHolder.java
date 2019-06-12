package concurrency.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nullable;

/**
 * DishOrderHolder holds the queue of the DishOrders and
 * ensures adding and polling from queue is synchronised with a lock.
 */
public class DishOrderHolder {

    private Queue<DishOrder> dishOrderQueue;
    private Lock dishOrderLock = new ReentrantLock();

    public DishOrderHolder() {
        this.dishOrderQueue = new LinkedList<>();
    }

    /**
     * Adds a DishOrder to the queue
     *
     * @param dishOrder
     */
    public void addDishOrderToQueue(DishOrder dishOrder) {
        dishOrderLock.lock();
        try {
            dishOrderQueue.add(dishOrder);
        } finally {
            dishOrderLock.unlock();
        }
    }

    /**
     * Returns a DishOrder from a queue or null
     * if the queue is empty.
     *
     * @return DishOrder or null (if queue empty)
     */
    @Nullable
    public DishOrder getDishOrderFromQueue() {
        dishOrderLock.lock();
        try {
            return dishOrderQueue.poll();
        } finally {
            dishOrderLock.unlock();
        }
    }
}
