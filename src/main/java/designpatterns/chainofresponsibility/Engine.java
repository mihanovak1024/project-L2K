package main.java.designpatterns.chainofresponsibility;

/**
 * Created by miha.novak on 25/09/2017.
 */
public class Engine implements Handler {

    private Handler nextHandler;

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void doSomethingOnCarPart(String mechanicAction, String carPart) {
        if (carPart.equals("Engine")) {
            switch (mechanicAction) {
                case "RunDiagnostics":
                    System.out.println("There doesn't seem to be anything wrong with the " + carPart + ".");
                    break;
                case "ImprovePower":
                    System.out.println("The power of the " + carPart + " has been improved by 11 hP");
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
