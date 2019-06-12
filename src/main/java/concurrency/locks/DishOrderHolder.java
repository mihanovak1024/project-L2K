package concurrency.locks;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DishOrderListHolder {

    private Queue<DishOrder> dishOrderList;
    private Lock dishOrderLock = new ReentrantLock();

    public DishOrderListHolder() {
        this.dishOrderList = new Block<>();
    }

    public void addDishOrderToList(DishOrder dishOrder) {
        dishOrderLock.lock();
        try {
            dishOrderList.add(dishOrder);
        } finally {
            dishOrderLock.unlock();
        }
    }

    public DishOrder getDishOrderFromList() {
        dishOrderLock.lock();
        try {
            return dishOrderList.get();
        } finally {
            dishOrderLock.unlock();
        }
    }
}
