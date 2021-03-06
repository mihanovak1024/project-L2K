package concurrency.locks;

import java.util.ArrayList;
import java.util.List;

public class OvercookedMain {

    public static void main(String[] args) {
        new OvercookedMain(
                3,
                100,
                70,
                50
        );
    }

    /**
     * Creates a list of {@link OvercookedPlayer} and one {@link OvercookedChef}.
     * Starts the chef and players in their own threads.
     *
     * @param numberOfPlayers
     * @param taskDurationMillis
     * @param chefWakeUpMillis
     * @param playerShiftCheckMillis
     */
    OvercookedMain(int numberOfPlayers, int taskDurationMillis, int chefWakeUpMillis, int playerShiftCheckMillis) {
        DishOrderHolder dishOrderHolder = new DishOrderHolder();
        List<OvercookedPlayer> playerList = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new OvercookedPlayer(taskDurationMillis, dishOrderHolder, playerShiftCheckMillis));
        }
        OvercookedChef chef = new OvercookedChef(taskDurationMillis, dishOrderHolder, playerList, chefWakeUpMillis);

        new Thread(chef).start();

        for (OvercookedPlayer player : playerList) {
            new Thread(player).start();
        }
    }
}


