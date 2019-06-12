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

    /**
     * Logic for action of cutting vegetables.
     * <p>
     * First checks if this task was already done for the currentDish
     * and if already was, it returns and starts with another task.
     * If not, it awaits for taskDurationMillis milliseconds to finish
     * the task or until it gets signaled (and forced to clean plates).)
     */
    private void cutVegetables() {
        if (currentDish.isDishCut()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Cutting vegetables.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDurationMillis, TimeUnit.MILLISECONDS)) {
                log.debug("Player interrupted");
            } else {
                currentDish.vegetablesCut();
                log.debug("Done cutting vegetables.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            playerLock.unlock();
        }
    }

    /**
     * Logic for action of heating the dish.
     * <p>
     * First checks if this task was already done for the currentDish
     * and if already was, it returns and starts with another task.
     * If not, it awaits for taskDurationMillis milliseconds to finish
     * the task or until it gets signaled (and forced to clean plates).)
     */
    private void heatDish() {
        if (currentDish.isDishHeated()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Heating dish.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDurationMillis, TimeUnit.MILLISECONDS)) {
                log.debug("Player interrupted");
            } else {
                currentDish.dishHeated();
                log.debug("Done heating dish.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            playerLock.unlock();
        }
    }

    /**
     * Logic for action of serving the dish to the customer.
     * <p>
     * First checks if this task was already done for the currentDish
     * and if already was, it returns and starts with another task.
     * If not, it awaits for taskDurationMillis milliseconds to finish
     * the task or until it gets signaled (and forced to clean plates).)
     */
    private void serveDish() {
        if (currentDish.isDishOutOfKitchen()) {
            log.debug("Dish already cut");
            return;
        }
        log.debug("Serving dish.");
        playerLock.lock();
        try {
            if (playerFlowCondition.await(taskDurationMillis, TimeUnit.MILLISECONDS)) {
                log.debug("Player interrupted");
            } else {
                currentDish.dishServed();
                log.debug("Done serving dish.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            playerLock.unlock();
        }
    }

    /**
     * It stops the current task this {@link OvercookedPlayer} is
     * performing and forces him to clean dishes.
     * <p>
     * The current unfinished dish is returned to the {@link DishOrderHolder}.
     * <p>
     * This method can be triggered from {@link OvercookedChef}.
     */
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

    /**
     * It stops the current plate cleaning task and makes the player continue previous work.
     * <p>
     * This method can be triggered from {@link OvercookedChef}.
     */
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

    /**
     * When {@link OvercookedPlayer#cleanPlates()} method is called from {@link OvercookedChef},
     * the current Thread awaits until either
     * a) {@link OvercookedChef} calls {@link OvercookedPlayer#stopCleaningPlatesAndContinueWork()}
     * b) the (taskDurationMillis * 5) milliseconds passes
     */
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

    /**
     * Main logic for {@link OvercookedPlayer}.
     * It checks the {@link DishOrderHolder} for available {@link DishOrder} and
     * starts executing each task, if the plateCleaning task from
     * {@link OvercookedChef}'s side was not initiated.
     *
     * If the {@link DishOrderHolder} does not contain any available
     * {@link DishOrder}, it waits 5 iterations for playerShiftCheckMillis milliseconds
     * and tries to check for an available {@link DishOrder} every iteration.
     */
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
            cutVegetables();
        } else {
            cleaningPlatesMode();
            return;
        }

        if (!isCleaningPlates) {
            heatDish();
        } else {
            cleaningPlatesMode();
            return;
        }

        if (!isCleaningPlates) {
            serveDish();
        } else {
            cleaningPlatesMode();
            return;
        }
        run();
    }
}
