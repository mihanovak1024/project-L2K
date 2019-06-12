package concurrency.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvercookedPlayer implements Runnable {
    private Logger log = LoggerFactory.getLogger(OvercookedPlayer.class);

    private Lock playerLock = new ReentrantLock();
    private Lock cleaningDishesLock = new ReentrantLock();
    private Condition playerFlowCondition = playerLock.newCondition();
    private Condition cleaningDishesCondition = cleaningDishesLock.newCondition();

    private int taskDuration;
    private final DishOrderHolder dishOrderHolder;
    private DishOrder currentDish;

    private volatile boolean isCleaningPlates = false;

    public OvercookedPlayer(int taskDuration, DishOrderHolder dishOrderHolder) {
        this.taskDuration = taskDuration;
        this.dishOrderHolder = dishOrderHolder;
    }

    public void cutVegetables(DishOrder dishOrder) {
        if (dishOrder.isDishCut()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Cutting vegetables.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDuration, TimeUnit.MILLISECONDS)) {
                log.debug("Player interrupted");
            } else {
                dishOrder.vegetablesCut();
                log.debug("Done cutting vegetables.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            playerLock.unlock();
        }
    }

    public void heatDish(DishOrder dishOrder) {
        if (dishOrder.isDishHeated()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Heating dish.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDuration, TimeUnit.MILLISECONDS)) {
                log.debug("Player interrupted");
            } else {
                dishOrder.dishHeated();
                log.debug("Done heating dish.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            playerLock.unlock();
        }
    }

    public void serveDish(DishOrder dishOrder) {
        if (dishOrder.isDishOutOfKitchen()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Serving dish.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDuration, TimeUnit.MILLISECONDS)) {
                log.debug("Player interrupted");
            } else {
                dishOrder.dishServerd();
                log.debug("Done serving dish.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            playerLock.unlock();
        }
    }

    public void cleanPlates() {
        log.debug("Cleaning plates.");
        playerLock.lock();
        try {
            playerFlowCondition.signal();
            dishOrderHolder.addDishOrderToQueue(currentDish);
            isCleaningPlates = true;
        } finally {
            playerLock.unlock();
        }
    }

    public void stopCleaningPlatesAndContinueWork() {
        cleaningDishesLock.lock();
        try {
            isCleaningPlates = false;
            cleaningDishesCondition.signal();
        } finally {
            cleaningDishesLock.unlock();
        }
        log.debug("Done cleaning plates.");
    }

    private void cleaningPlatesMode() {
        cleaningDishesLock.lock();
        try {
            cleaningDishesCondition.await(taskDuration * 5, TimeUnit.MILLISECONDS);
            log.debug("Waking up");
            run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleaningDishesLock.unlock();
        }
    }

    @Override
    public void run() {
        log.debug("New dish");
        currentDish = dishOrderHolder.getDishOrderFromQueue();

        if (!isCleaningPlates) {
            cutVegetables(currentDish);
        } else {
            cleaningPlatesMode();
            return;
        }

        if (!isCleaningPlates) {
            heatDish(currentDish);
        } else {
            cleaningPlatesMode();
            return;
        }

        if (!isCleaningPlates) {
            serveDish(currentDish);
        } else {
            cleaningPlatesMode();
            return;
        }
        run();
    }
}
