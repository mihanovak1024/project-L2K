package concurrency.locks;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvercookedChef extends OvercookedPlayer {
    private Logger log = LoggerFactory.getLogger(OvercookedChef.class);

    private final List<OvercookedPlayer> playerList;
    private final int chefWakeUpMillis;

    OvercookedChef(int taskDurationMillis, DishOrderHolder dishOrderHolder, List<OvercookedPlayer> playerList, int chefWakeUpMillis) {
        super(taskDurationMillis, dishOrderHolder, 0);
        this.chefWakeUpMillis = chefWakeUpMillis;
        this.playerList = playerList;
    }

    /**
     * A random {@link OvercookedPlayer} is selected to clean plates and is
     * cleared of plate cleaning duty after taskDurationMillis milliseconds.
     *
     * @param overcookedPlayer
     */
    private void bossPlayerAround(OvercookedPlayer overcookedPlayer) {
        if (overcookedPlayer == this) {
            log.debug("Can't boss myself around, should find someone else.");
            return;
        }
        log.debug("Bossing a player to clean plates.");
        overcookedPlayer.cleanPlates();
        try {
            Thread.sleep(taskDurationMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("Bossing a player to stop cleaning plates and resume working.");
        overcookedPlayer.stopCleaningPlatesAndContinueWork();
    }

    /**
     * A new {@link DishOrder} is created and added into the {@link DishOrderHolder}.
     */
    private void addNewOrderToList() {
        log.debug("Put a new order to dishOrderHolder.");
        dishOrderHolder.addDishOrderToQueue(new DishOrder());
    }

    //@formatter:off
    /**
     * Iterates for playerList.size() * 5 cycles and does the following:
     * a) on every even cycle it adds a new {@link DishOrder} to the {@link DishOrderHolder}
     * b) on every odd cycle it picks a random {@link OvercookedPlayer} and assigns him
     *    plate cleaning duty for taskDurationMillis milliseconds.
     * Then waits for chefWakeUpMillis milliseconds before going to a next cycle iteration.
     */
    //@formatter:on
    @Override
    public void run() {
        log.debug("Chef [thread = {}]", Thread.currentThread().getName());
        for (int i = 0; i < playerList.size() * 5; i++) {
            log.debug("New chef iteration {}", i);
            if (i % 2 == 0) {
                addNewOrderToList();
            } else {
                OvercookedPlayer player = playerList.get((int) (Math.random() * playerList.size()));
                bossPlayerAround(player);
            }

            try {
                log.debug("Chef taking a break");
                Thread.sleep(chefWakeUpMillis);
                log.debug("Chef end of break");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
