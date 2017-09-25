package main.designpatterns.chainofresponsibility;

/**
 * Created by miha.novak on 25/09/2017.
 */
public class Interior implements Handler {

    private Handler nextHandler;

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void doSomethingOnCarPart(String mechanicAction, String carPart) {
        if (carPart.equals("Interior")) {
            switch (mechanicAction) {
                case "CheckTemperature":
                    System.out.println("Temperature in the " + carPart + " is ok.");
                    break;
                case "CleanInterior":
                    System.out.println(carPart + " has been cleaned.");
                default:
                    System.out.println("Our " + carPart + " mechanic can't do the following job: " + mechanicAction);
                    break;
            }
        } else {
            System.out.println("Our mechanic doesn't know the car part: " + carPart);
        }
    }
}
