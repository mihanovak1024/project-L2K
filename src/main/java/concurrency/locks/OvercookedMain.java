package concurrency.locks;

import java.util.ArrayList;
import java.util.List;

public class OvercookedMain {

    public static void main(String[] args) {
        new OvercookedMain();
    }

    private List<DishOrder> dishList;

    public OvercookedMain() {
        dishList = new ArrayList<>();
    }
}


