package concurrency.locks;

public class DishOrder {

    private DishState dishState = DishState.ORDERED;

    /**
     * Dish state is set to {@link DishState#VEGETABLES_CUT}
     * if the previous state was {@link DishState#ORDERED}.
     */
    public void vegetablesCut() {
        if (dishState == DishState.ORDERED) {
            dishState = DishState.VEGETABLES_CUT;
        }
    }

    /**
     * Dish state is set to {@link DishState#HEATED}
     * if the previous state was {@link DishState#VEGETABLES_CUT}.
     */
    public void dishHeated() {
        if (dishState == DishState.VEGETABLES_CUT) {
            dishState = DishState.HEATED;
        }
    }

    /**
     * Dish state is set to {@link DishState#SERVED}
     * if the previous state was {@link DishState#HEATED}.
     */
    public void dishServed() {
        if (dishState == DishState.HEATED) {
            dishState = DishState.SERVED;
        }
    }

    public boolean isDishOutOfKitchen() {
        return dishState == DishState.SERVED;
    }

    public boolean isDishCut() {
        return dishState == DishState.VEGETABLES_CUT;
    }

    public boolean isDishHeated() {
        return dishState == DishState.HEATED;
    }

    public DishState getDishState() {
        return dishState;
    }
}
