package concurrency.locks;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvercookedChef extends OvercookedPlayer {
    private Logger log = LoggerFactory.getLogger(OvercookedChef.class);

    public void bossPlayerAround(OvercookedPlayer overcookedPlayer) {
        if (overcookedPlayer == this) {
            log.debug("Can't boss myself around, should find someone else.");
            return;
        }
        // TODO: 10/06/2019 random action for random player
    }

    public void addNewOrderToList(List<DishOrder> dishArray) {
        // TODO: 10/06/2019 thread safety
        dishArray.add(new DishOrder());
    }
}
