package concurrency.locks;

public enum DishState {
    ORDERED(0),
    VEGETABLES_CUT(1),
    HEATED(2),
    SERVED(3);

    int priority;

    DishState(int priority) {
        this.priority = priority;
    }
}
