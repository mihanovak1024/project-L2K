package main.java.designpatterns.chainofresponsibility;

/**
 * Created by miha.novak on 25/09/2017.
 */
public class Tyre implements Handler {

    private Handler nextHandler;

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void doSomethingOnCarPart(String mechanicAction, String carPart) {
        if (carPart.equals("Tyre")) {
            switch (mechanicAction) {
                case "CheckPressure":
                    System.out.println("Pressure in the " + carPart + " is okay.");
                    break;
                case "AddPressure":
                    System.out.println("Pressure added to the " + carPart + ".");
                    break;
                default:
                    System.out.println("Our " + carPart + " mechanic can't do the following job: " + mechanicAction);
                    break;
            }
        } else {
            nextHandler.doSomethingOnCarPart(mechanicAction, carPart);
        }
    }
}
