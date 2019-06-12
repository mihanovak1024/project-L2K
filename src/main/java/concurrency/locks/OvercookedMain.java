package concurrency.locks;

import java.util.ArrayList;
import java.util.List;

public class OvercookedMain {

    public static void main(String[] args) {
        new OvercookedMain(1, 100);
    }

    OvercookedMain(int numberOfPlayers, int taskDurationMillis) {
        DishOrderHolder dishOrderHolder = new DishOrderHolder();
        List<OvercookedPlayer> playerList = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new OvercookedPlayer(taskDurationMillis, dishOrderHolder));
        }
        OvercookedChef chef = new OvercookedChef(taskDurationMillis, dishOrderHolder, playerList);

        new Thread(chef).start();

        for (OvercookedPlayer player : playerList) {
            new Thread(player).start();
        }
    }
}


