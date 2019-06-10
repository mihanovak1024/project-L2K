package concurrency.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvercookedPlayer {
    private Logger log = LoggerFactory.getLogger(OvercookedPlayer.class);

    public void cutVegetables(DishOrder dishOrder) {
        log.debug("Cutting vegetables.");
        dishOrder.vegetablesCut();
    }

    public void heatDish(DishOrder dishOrder) {
        log.debug("Heating dish.");
        dishOrder.dishHeated();
    }

    public void serveDish(DishOrder dishOrder) {
        log.debug("Serving dish.");
        dishOrder.dishServerd();
    }

    public void cleanPlates() {
        log.debug("Cleaning plates.");
    }
}
