package concurrency.locks;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvercookedChef extends OvercookedPlayer {
    private Logger log = LoggerFactory.getLogger(OvercookedChef.class);

    private final List<OvercookedPlayer> playerList;

    OvercookedChef(int taskDurationMillis, DishOrderHolder dishOrderHolder, List<OvercookedPlayer> playerList) {
        super(taskDurationMillis, dishOrderHolder);
        this.playerList = playerList;
    }

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

    private void addNewOrderToList() {
        dishOrderHolder.addDishOrderToQueue(new DishOrder());
    }

    @Override
    public void run() {
        log.debug("Chef [thread = {}]", Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            log.debug("New chef iteration {}", i);
            if (i % 2 == 0) {
                addNewOrderToList();
            } else {
                OvercookedPlayer player = playerList.get((int) (Math.random() * playerList.size()));
                bossPlayerAround(player);
            }

            try {
                log.debug("Chef taking a break");
                Thread.sleep(200);
                log.debug("Chef end of break");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
