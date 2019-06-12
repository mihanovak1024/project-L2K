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

    final int taskDurationMillis;
    final int playerShiftCheckMillis;
    final DishOrderHolder dishOrderHolder;
    private DishOrder currentDish;

    private volatile boolean isCleaningPlates = false;

    OvercookedPlayer(int taskDurationMillis, DishOrderHolder dishOrderHolder, int playerShiftCheckMillis) {
        this.taskDurationMillis = taskDurationMillis;
        this.dishOrderHolder = dishOrderHolder;
        this.playerShiftCheckMillis = playerShiftCheckMillis;
    }

    private void cutVegetables(DishOrder dishOrder) {
        if (dishOrder.isDishCut()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Cutting vegetables.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDurationMillis, TimeUnit.MILLISECONDS)) {
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

    private void heatDish(DishOrder dishOrder) {
        if (dishOrder.isDishHeated()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Heating dish.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDurationMillis, TimeUnit.MILLISECONDS)) {
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

    private void serveDish(DishOrder dishOrder) {
        if (dishOrder.isDishOutOfKitchen()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Serving dish.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDurationMillis, TimeUnit.MILLISECONDS)) {
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

    void cleanPlates() {
        log.debug("Player should clean plates.");
        playerLock.lock();
        try {
            playerFlowCondition.signal();
            if (currentDish != null) {
                log.debug("Current state of dishOrder [{}]", currentDish.getDishState().toString());
                dishOrderHolder.addDishOrderToQueue(currentDish);
            }
            isCleaningPlates = true;
        } finally {
            playerLock.unlock();
        }
    }

    void stopCleaningPlatesAndContinueWork() {
        cleaningDishesLock.lock();
        try {
            isCleaningPlates = false;
            cleaningDishesCondition.signal();
        } finally {
            cleaningDishesLock.unlock();
        }
        log.debug("Player is done cleaning plates.");
    }

    private void cleaningPlatesMode() {
        cleaningDishesLock.lock();
        try {
            log.debug("Cleaning plates mode");
            cleaningDishesCondition.await(taskDurationMillis * 5, TimeUnit.MILLISECONDS);
            log.debug("Resuming other work");
            run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleaningDishesLock.unlock();
        }
    }

    @Override
    public void run() {
        log.debug("New dish [thread = {}]", Thread.currentThread().getName());

        for (int i = 0; i < 5; i++) {
            currentDish = dishOrderHolder.getDishOrderFromQueue();
            if (currentDish == null) {
                try {
                    log.debug("No dishes at the moment, taking a break. [{}/5]", i);
                    Thread.sleep(playerShiftCheckMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
        if (currentDish == null) {
            log.debug("No more dishes, ending my shift.");
            return;
        }

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
