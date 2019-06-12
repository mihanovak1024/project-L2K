package concurrency.locks;

public class DishOrder {

    private DishState dishState = DishState.ORDERED;

    public void vegetablesCut() {
        if (dishState == DishState.ORDERED) {
            dishState = DishState.VEGETABLES_CUT;
        }
    }

    public void dishHeated() {
        if (dishState == DishState.VEGETABLES_CUT) {
            dishState = DishState.HEATED;
        }
    }

    public void dishServerd() {
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
