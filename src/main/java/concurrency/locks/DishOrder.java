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

    private boolean isDishOutOfKitchen() {
        return dishState == DishState.SERVED;
    }
}
